package ch.uzh.ifi.attempto.gfservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * Structured representation of GF trees. Current features:
 *   - constructs GfTree from a strings, e.g. "a (b (c d)) e", some syntax errors are tolerated
 *   - getter for all function names and leaf names in the tree
 */
public class GfTree {

	public static final char SPACE = ' ';
	public static final char PLEFT = '(';
	public static final char PRIGHT = ')';

	// [ \t\n\x0B\f\r]
	public static final ImmutableSet<Character> LAYOUT_CHARS = ImmutableSet.of(SPACE, '\t', '\n', '\f', '\r');

	private final GfFun mRoot;
	private final Set<String> mFunctionNames;
	private final Set<String> mLeafNames;
	private final String mString;


	// TODO: make immutable if exposed to the client
	public static class GfFun {
		private final String mName;
		private List<GfFun> mArgs;
		public GfFun(String name) {
			mName = name;
		}

		public void addArg(GfFun fun) {
			if (mArgs == null) {
				mArgs = new ArrayList<GfFun>();
			}
			mArgs.add(fun);
		}

		public String getName() {
			return mName;
		}

		public List<GfFun> getArgs() {
			if (mArgs == null) {
				return ImmutableList.of();
			}
			return mArgs;
		}

		public boolean hasArgs() {
			return mArgs != null;
		}
	}


	public GfTree(String str) throws GfTreeParseException {
		StringBuilder name = new StringBuilder();
		int end = consumeName(str, 0, name);
		mRoot = new GfFun(name.toString());
		end = consumeArgs(str, mRoot, end);
		if (end != str.length()) {
			throw new GfTreeParseException(end);
		}

		StringBuilder sb = new StringBuilder();
		Set<String> funs = Sets.newHashSet();
		Set<String> leaves = Sets.newHashSet();
		initData(mRoot, sb, funs, leaves);
		mFunctionNames = ImmutableSet.copyOf(funs);
		mLeafNames = ImmutableSet.copyOf(leaves);
		mString = sb.toString();
	}


	public Set<String> getFunctionNames() {
		return mFunctionNames;
	}

	/**
	 * Leaves are functions that have no arguments
	 */
	public Set<String> getLeafNames() {
		return mLeafNames;
	}

	public String toString() {
		return mString;
	}

	public boolean hasFunctionNames(String... names) {
		return hasNames(mFunctionNames, names);
	}


	public boolean hasLeafNames(String... names) {
		return hasNames(mLeafNames, names);
	}


	public boolean equals(GfTree tree) {
		return toString().equals(tree.toString());
	}


	private static boolean hasNames(Set<String> set, String... names) {
		for (String name : names) {
			if (! set.contains(name)) {
				return false;
			}
		}
		return true;
	}


	private static void initData(GfFun fun, StringBuilder sb, Set<String> funs, Set<String> leaves) {
		String name = fun.getName();
		sb.append(name);
		funs.add(name);
		if (! fun.hasArgs()) {
			leaves.add(name);
		}
		for (GfFun arg : fun.getArgs()) {
			sb.append(SPACE);
			if (arg.hasArgs()) {
				sb.append(PLEFT);
				initData(arg, sb, funs, leaves);
				sb.append(PRIGHT);
			} else {
				initData(arg, sb, funs, leaves);
			}
		}
	}


	private static int consumeArgs(String str, GfFun fun, int begin) throws GfTreeParseException {
		int i = begin;
		while (i < str.length()) {
			char ch = str.charAt(i);
			if (ch == PLEFT) {
				i++;
				StringBuilder sb = new StringBuilder();
				int end = consumeName(str, i, sb);
				GfFun funWithArgs = new GfFun(sb.toString());
				fun.addArg(funWithArgs);
				i = consumeArgs(str, funWithArgs, end);
			} else if (LAYOUT_CHARS.contains(ch)) {
				// Skip whitespace
				i++;
			} else if (ch == PRIGHT) {
				i++;
				break;
			} else {
				StringBuilder sb = new StringBuilder();
				i = consumeName(str, i, sb);
				fun.addArg(new GfFun(sb.toString()));
			}
		}
		return i;
	}


	private static int consumeName(String str, int begin, StringBuilder sb) throws GfTreeParseException {
		int i = begin;
		int start = -1;
		while (i < str.length()) {
			char ch = str.charAt(i);
			if (LAYOUT_CHARS.contains(ch)) {
				if (start != -1) {
					break;
				}
				// ignore preceding whitespace
			} else if (ch == PLEFT || ch == PRIGHT) {
				break;
			} else if (start == -1) {
				start = i;
			}
			i++;
		}
		if (start == -1) {
			throw new GfTreeParseException(i);
		}
		sb.append(str.substring(start, i));
		return i;
	}

}

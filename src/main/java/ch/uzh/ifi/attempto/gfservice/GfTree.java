package ch.uzh.ifi.attempto.gfservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * Structured representation of GF trees. Current features:
 *   - constructs GfTree from a strings, e.g. "a ((b c) d)", some syntax errors are tolerated
 *   - getter for all function names in the tree
 */
public class GfTree {

	public static char SPACE = ' ';
	public static char PLEFT = '(';
	public static char PRIGHT = ')';

	// [ \t\n\x0B\f\r]
	public static ImmutableSet<Character> LAYOUT_CHARS = ImmutableSet.of(SPACE, '\t', '\n', '\f', '\r');

	private final Set<String> mFuns = Sets.newHashSet();
	private final String mTreeAsStr;


	/**
	 * There are two kinds of nodes. Ones that have children and
	 * the others that contain just the function name.
	 */
	public static class GfNode {
		private final List<GfNode> mChildren;
		private final String mFun;

		public GfNode() {
			mChildren = new ArrayList<GfNode>();
			mFun = null;
		}

		public GfNode(String fun) {
			mChildren = null;
			mFun = fun;
		}

		public List<GfNode> getChildren() {
			if (mChildren == null) {
				return new ArrayList<GfNode>();
			}
			return mChildren;
		}

		public String getFun() {
			return mFun;
		}

		public boolean hasChildren() {
			return mChildren != null;
		}

		private void addChild(GfNode tree) {
			mChildren.add(tree);
		}
	}


	public GfTree(String str) throws GfTreeParseException {
		GfNode root = new GfNode();
		int len = str.length();
		int pos = consumeTree(str, 0, len, root);
		if (pos != len) {
			throw new GfTreeParseException(pos - 1);
		}

		StringBuilder sb = new StringBuilder();
		initData(root, sb, mFuns);
		mTreeAsStr = sb.toString();
	}


	public Set<String> getFuns() {
		return ImmutableSet.copyOf(mFuns);
	}

	public boolean hasFuns(String... funs) {
		for (String fun : funs) {
			if (! mFuns.contains(fun)) {
				return false;
			}
		}
		return true;
	}

	public String toString() {
		return mTreeAsStr;
	}

	public boolean equals(GfTree tree) {
		return toString().equals(tree.toString());
	}



	private void initData(GfNode node, StringBuilder sb, Set<String> funs) {
		if (node.hasChildren()) {
			boolean firstChild = true;
			for (GfNode child : node.getChildren()) {
				if (! firstChild) {
					sb.append(SPACE);
				}
				if (child.hasChildren()) {
					sb.append(PLEFT);
					initData(child, sb, funs);
					sb.append(PRIGHT);
				} else {
					String fun = child.getFun();
					sb.append(fun);
					funs.add(fun);
				}
				firstChild = false;
			}
		} else {
			String fun = node.getFun();
			sb.append(fun);
			funs.add(fun);
		}
	}


	// returns the number of consumed characters
	private static int consumeTree(String str, int begin, int end, GfNode tree) {
		int i = begin;
		while (i < end) {
			char ch = str.charAt(i);
			if (ch == PRIGHT) {
				return i + 1;
			}
			if (ch == PLEFT) {
				GfNode subtree = new GfNode();
				tree.addChild(subtree);
				i = consumeTree(str, i + 1, end, subtree);
				continue;
			}
			if (LAYOUT_CHARS.contains(ch)) {
				i++;
				continue;
			}
			int j = i;
			while (j < end) {
				char sep = str.charAt(j);
				if (sep == PLEFT || sep == PRIGHT || LAYOUT_CHARS.contains(sep)) {
					GfNode leaf = new GfNode(str.substring(i, j));
					tree.addChild(leaf);
					break;
				}
				j++;
			}
			if (j == end) {
				GfNode leaf = new GfNode(str.substring(i, j));
				tree.addChild(leaf);
			}
			i = j;
		}
		return end;
	}
}
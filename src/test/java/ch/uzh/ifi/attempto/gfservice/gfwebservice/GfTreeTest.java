package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import ch.uzh.ifi.attempto.gfservice.GfTree;
import ch.uzh.ifi.attempto.gfservice.GfTreeParseException;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import static org.junit.Assert.*;


public class GfTreeTest {

	// Parse errors
	private static final String E1_TREE = "";
	private static final int E1_POS = 0;
	private static final String E2_TREE = "(";
	private static final int E2_POS = 1;
	private static final String E3_TREE = "a b ) c";
	private static final int E3_POS = 5;
	private static final String E4_TREE = "  ";
	private static final int E4_POS = 2;

	// Correct trees
	private static final String T1_TREE = "a 1 (b 2) (c (d 3 4) 5) 6";
	private static final String T2_TREE = "a";
	private static final String T3_TREE = "a b";
	private static final String T4_TREE = "a b c";
	private static final String T5_TREE = "a (bb c)";
	private static final String T6_TREE = "a (b (c))";
	private static final String T6_PRETTY = "a (b c)";
	private static final String T7_TREE = "UQuestion (QAction light (switchOff light ?3) ?5 (DKindOne light))";
	private static final String T10_TREE = "\ta  \n (b\tc\nd)\n ";
	private static final String T10_PRETTY = "a (b c d)";
	private static final String T11_TREE = "a(b(c";
	private static final String T11_PRETTY = "a (b c)";

	@Test
	public void testError1() {
		testError(E1_TREE, E1_POS);
	}

	//@Test TODO: disabled for the time being
	public void testError2() {
		testError(E2_TREE, E2_POS);
	}

	@Test
	public void testError3() {
		testError(E3_TREE, E3_POS);
	}

	@Test
	public void testError4() {
		testError(E4_TREE, E4_POS);
	}


	@Test
	public void testGfTree1() {
		GfTree tree = testGfTree(T1_TREE);
		assertEquals(ImmutableSet.of("a", "b", "c", "d", "1", "2", "3", "4", "5", "6"), tree.getFunctionNames());
		assertEquals(ImmutableSet.of("1", "2", "3", "4", "5", "6"), tree.getLeafNames());
	}

	@Test
	public void testGfTree2() {
		testGfTree(T2_TREE);
	}

	@Test
	public void testGfTree3() {
		testGfTree(T3_TREE);
	}

	//a (b cc) (d (e ff) gg)
	@Test
	public void testGfTree4() {
		testGfTree(T4_TREE);
	}

	@Test
	public void testGfTree5() {
		testGfTree(T5_TREE);
	}

	@Test
	public void testGfTree6() {
		testGfTree(T6_TREE, T6_PRETTY);
	}

	@Test
	public void testGfTree7() {
		testGfTree(T7_TREE);
	}

	@Test
	public void testGfTree10() {
		testGfTree(T10_TREE, T10_PRETTY);
	}

	@Test
	public void testGfTree11() {
		testGfTree(T11_TREE, T11_PRETTY);
	}


	private GfTree testGfTree(String treeAsStr) {
		GfTree tree = null;
		try {
			tree = new GfTree(treeAsStr);
			assertEquals(treeAsStr, tree.toString());
		} catch (GfTreeParseException e) {
			fail("Should NOT throw GfTreeParseException: " + e.getMessage());
		}
		return tree;
	}

	private GfTree testGfTree(String in, String out) {
		GfTree tree = null;
		try {
			tree = new GfTree(in);
			assertEquals(out, tree.toString());
		} catch (GfTreeParseException e) {
			fail("Should NOT throw GfTreeParseException: " + e.getMessage());
		}
		return tree;
	}


	private void testError(String treeAsStr, int pos) {
		try {
			new GfTree(treeAsStr);
			fail("Should throw GfTreeParseException");
		} catch (GfTreeParseException e) {
			assertEquals(pos, e.getPosition());
		}
	}

}
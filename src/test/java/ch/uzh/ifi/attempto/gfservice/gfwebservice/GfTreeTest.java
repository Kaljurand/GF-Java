package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import ch.uzh.ifi.attempto.gfservice.GfTree;
import ch.uzh.ifi.attempto.gfservice.GfTreeParseException;
import org.junit.Test;

import static org.junit.Assert.*;


public class GfTreeTest {

	// Parse errors
	private static final String E1_TREE = "(";
	private static final int E1_POS = 1;
	private static final String E2_TREE = "(a b (c)) d) e";
	private static final int E2_POS = 11;
	private static final String E3_TREE = "a b ) c";
	private static final int E3_POS = 4;

	// Correct trees
	private static final String T1_TREE = "";
	private static final String T2_TREE = "a";
	private static final String T3_TREE = "aa";
	private static final String T4_TREE = "a bb c";
	private static final String T5_TREE = "a (bb c)";
	private static final String T6_TREE = "((a))";
	private static final String T7_TREE = "UQuestion (QAction light (switchOff light ?3) ?5 (DKindOne light))";
	private static final String T8_TREE = "(() (())) (a) ()";
	private static final String T9_TREE = "))";
	private static final String T10_TREE = " a  ((b)\tb\nc) ";
	private static final String T11_TREE = "(a b) c"; // TODO: should we support trees of this form?

	@Test
	public void testError1() {
		testError(T1_TREE, E1_POS);
	}

	@Test
	public void testError2() {
		testError(E2_TREE, E2_POS);
	}

	@Test
	public void testError3() {
		testError(E3_TREE, E3_POS);
	}


	@Test
	public void testGfTree1() {
		testGfTree(T1_TREE);
	}

	@Test
	public void testGfTree2() {
		testGfTree(T2_TREE);
	}

	@Test
	public void testGfTree3() {
		testGfTree(T3_TREE);
	}

	@Test
	public void testGfTree4() {
		GfTree tree = testGfTree(T4_TREE);
		assertTrue(tree.hasFuns("c", "a", "bb"));
	}

	@Test
	public void testGfTree5() {
		testGfTree(T5_TREE);
	}

	@Test
	public void testGfTree6() {
		testGfTree(T6_TREE);
	}

	@Test
	public void testGfTree7() {
		testGfTree(T7_TREE);
	}

	@Test
	public void testGfTree8() {
		testGfTree(T8_TREE);
	}

	@Test
	public void testGfTree9() {
		testGfTree(T9_TREE);
	}

	@Test
	public void testGfTree10() {
		testGfTree(T10_TREE, "a ((b) b c)");
	}

	@Test
	public void testGfTree11() {
		testGfTree(T11_TREE);
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
package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import ch.uzh.ifi.attempto.gfservice.GfServiceException;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultAbstrtree;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultAlignment;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultBrowse;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultComplete;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultGrammar;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultLinearize;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultParse;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultParsetree;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultRandom;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultTranslate;
import ch.uzh.ifi.attempto.gfservice.gfwebservice.GfWebService;


public class GfWebServiceTest {

	private static final GfWebService GF_SERVICE;
	private static final String GRAMMAR_PGF = "Go.pgf";
	private static final String NAME = "Go";
	private static final String STARTCAT = NAME;
	private static final String FROM = "GoEng";
	private static final String TO = "GoApp";
	private static final Set<String> CATEGORIES = new HashSet<String>(Arrays.asList(new String[] {
			"Float", "String", "Number", "Go", "Int", "Unit", "Direction" }));
	private static final Set<String> FUNCTIONS = new HashSet<String>(Arrays.asList(new String[] {
			"n1", "n2", "n3", "n4", "n5", "go", "meter", "forward", "back" }));
	private static final Set<String> LANGUAGES = new HashSet<String>(Arrays.asList(new String[] { "GoEst", "GoApp", "GoEng" }));

	private static final Set<String> T_BROWSE_0_PRODUCERS = new HashSet<String>(Arrays.asList(new String[] { "go" }));
	private static final Set<String> T_BROWSE_0_CONSUMERS = new HashSet<String>(Arrays.asList(new String[] { }));

	private static final String UNPARSABLE_STRING = "adkasdasdasda;slqwe0otkfef0sfs d ada-sdasd";

	private static final String T_PARSE_0_CAT = "Number";
	private static final String T_PARSE_0_INPUT = "three";
	private static final Set<String> T_PARSE_0_OUT_TREES = new HashSet<String>(Arrays.asList(new String[] { "n3" }));

	private static final String T_LINEARIZE_1_TREE = "n3";
	private static final Set<String> T_LINEARIZE_1_OUT_TEXTS = new HashSet<String>(Arrays.asList(new String[] { "3" }));

	private static final Map<String, Set<String>> T_LINEARIZE_2_OUT = new HashMap<String, Set<String>>();

	private static final String T_COMPLETE_0_INPUT = "go t";
	private static final Set<String> T_COMPLETE_0_OUT_COMPLETIONS = new HashSet<String>(Arrays.asList(new String[] { "three", "two" }));

	private static final String T_COMPLETE_1_OUT = "go five meters back";

	private static final String T_COMPLETE_2_1_INPUT = "g";
	private static final Set<String> T_COMPLETE_2_1_OUT_COMPLETIONS = mkSetString(new String[] { "go one", "go two", "go three", "go four", "go five" });
	private static final String T_COMPLETE_2_2_INPUT = "go ";
	private static final Set<String> T_COMPLETE_2_2_OUT_COMPLETIONS = mkSetString(new String[] { "one", "two", "three", "four", "five" });
	private static final String T_COMPLETE_2_3_INPUT = "go o";
	private static final Set<String> T_COMPLETE_2_3_OUT_COMPLETIONS = mkSetString(new String[] { "one meters forward", "one meters back" });
	private static final String T_COMPLETE_2_4_INPUT = "go one meters ";
	private static final Set<String> T_COMPLETE_2_4_OUT_COMPLETIONS = mkSetString(new String[] { "forward", "back" });
	private static final String T_COMPLETE_2_5_INPUT = "go one meters b";
	private static final Set<String> T_COMPLETE_2_5_OUT_COMPLETIONS = mkSetString(new String[] { "back" });
	private static final String T_COMPLETE_2_6_INPUT = "go one meters back ";
	private static final Set<String> T_COMPLETE_2_6_OUT_COMPLETIONS = Collections.EMPTY_SET;

	private static final String T_TRANSLATE_1_CAT = T_PARSE_0_CAT;
	private static final String T_TRANSLATE_1_INPUT = T_PARSE_0_INPUT;
	private static final Set<String> T_TRANSLATE_1_OUT_TRANSLATIONS = new HashSet<String>(Arrays.asList(new String[] { "3" }));

	private static final String T_ALIGNMENT_0_OUT_DATAURI_PREFIX = "data:image/png;base64,";

	// The input should currently end with a complete word
	private static final String T_GENERATE_0_INPUT = "g";
	// go X meters Y (where X is in {1..5} and Y is in {back,forward})
	// i.e. 10 possible parseable strings, but 21 prefixes
	private static final int T_GENERATE_0_SIZE = 21;


	static {
		GF_SERVICE = new GfWebService(Constants.WS_URI, Constants.GRAMMAR_DIR_LOCALHOST + GRAMMAR_PGF);

		T_LINEARIZE_2_OUT.put("GoEst", new HashSet<String>(Arrays.asList(new String[] { "kolm" })));
		T_LINEARIZE_2_OUT.put("GoEng", new HashSet<String>(Arrays.asList(new String[] { "three" })));
		T_LINEARIZE_2_OUT.put("GoApp", new HashSet<String>(Arrays.asList(new String[] { "3" })));
	}


	@Test
	public void testGrammar() {
		try {
			GfServiceResultGrammar result = GF_SERVICE.grammar();
			assertEquals(NAME, result.getName());
			assertEquals(CATEGORIES, result.getCategories());
			assertEquals(FUNCTIONS, result.getFunctions());
			assertEquals(STARTCAT, result.getStartcat());
			assertEquals(LANGUAGES, result.getLanguages().keySet());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testParse() {
		try {
			GfServiceResultParse result = GF_SERVICE.parse(T_PARSE_0_CAT, T_PARSE_0_INPUT, FROM);
			assertEquals(T_PARSE_0_OUT_TREES, result.getTrees(FROM));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testParse1() {
		try {
			GfServiceResultParse result = GF_SERVICE.parse(T_PARSE_0_CAT, UNPARSABLE_STRING, FROM);
			assertEquals(Collections.EMPTY_SET, result.getTrees(FROM));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testParse2() {
		try {
			GfServiceResultParse result = GF_SERVICE.parse(T_PARSE_0_CAT, T_PARSE_0_INPUT, FROM, 0);
			assertEquals(Collections.EMPTY_SET, result.getTrees(FROM));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testLinearize() {
		try {
			GfServiceResultLinearize result = GF_SERVICE.linearize(T_LINEARIZE_1_TREE, TO);
			assertEquals(T_LINEARIZE_1_OUT_TEXTS, result.getTexts(TO));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testLinearize1() {
		try {
			GF_SERVICE.linearize(null, TO);
			fail(Constants.MSGY_ILLEGAL_ARGUMENT_EXCEPTION);
		} catch (IllegalArgumentException e) {
			assertEquals("Tree MUST be given", e.getMessage());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testLinearize2() {
		try {
			GfServiceResultLinearize result = GF_SERVICE.linearize(T_LINEARIZE_1_TREE, null);
			assertEquals(T_LINEARIZE_2_OUT, result.getTexts());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testLinearize3() {
		try {
			GF_SERVICE.linearize(UNPARSABLE_STRING, null);
			fail(Constants.MSGY_GF_SERVICE_EXCEPTION);
		} catch (GfServiceException e) {
			assertEquals("-", e.getMessage());
		}
	}


	@Test
	public void testTranslate() {
		try {
			GfServiceResultTranslate result = GF_SERVICE.translate(T_TRANSLATE_1_CAT, T_TRANSLATE_1_INPUT, FROM, TO);
			assertEquals(T_TRANSLATE_1_OUT_TRANSLATIONS, result.getTranslations(FROM));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	/**
	 * <p>Translation by parse+linearize.</p>
	 */
	@Test
	public void testTranslate1() {
		try {
			GfServiceResultParse p = GF_SERVICE.parse(T_TRANSLATE_1_CAT, T_TRANSLATE_1_INPUT, FROM);
			assertEquals(1, p.getTrees(FROM).size());
			GfServiceResultLinearize l = GF_SERVICE.linearize(p.getTrees(FROM).iterator().next(), TO);
			assertEquals(T_TRANSLATE_1_OUT_TRANSLATIONS, l.getTexts(TO));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testRandom() {
		try {
			GfServiceResultRandom result = GF_SERVICE.random(null, null);
			// We just check if the number of returned trees matches the number
			// of requested trees. We request the default numbers of trees (i.e. 1).
			assertEquals(1, result.getTrees().size());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testComplete() {
		try {
			GfServiceResultComplete result = GF_SERVICE.complete(null, T_COMPLETE_0_INPUT, FROM, 3);
			assertEquals(T_COMPLETE_0_OUT_COMPLETIONS, result.getCompletions(FROM));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	/**
	 * <p>Building a sentence by completion.</p>
	 */
	@Test
	public void testComplete1() {
		String input = "";
		while (true) {
			GfServiceResultComplete result = null;
			try {
				result = GF_SERVICE.complete(null, input, FROM, 1);
			} catch (GfServiceException e) {
				fail(Constants.MSG_GF_SERVICE_EXCEPTION);
			}
			Set<String> completions = result.getCompletions(FROM);
			if (completions.isEmpty()) {
				break;
			} else {
				input += completions.iterator().next() + " ";
			}
		}
		assertEquals(T_COMPLETE_1_OUT + " ", input);
	}


	@Test
	public void testComplete2_1() {
		try {
			GfServiceResultComplete result = GF_SERVICE.complete(null, T_COMPLETE_2_1_INPUT, FROM, null, null);
			assertEquals(T_COMPLETE_2_1_OUT_COMPLETIONS, result.getCompletions(FROM));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testComplete2_2() {
		try {
			GfServiceResultComplete result = GF_SERVICE.complete(null, T_COMPLETE_2_2_INPUT, FROM, null, null);
			assertEquals(T_COMPLETE_2_2_OUT_COMPLETIONS, result.getCompletions(FROM));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testComplete2_3() {
		try {
			GfServiceResultComplete result = GF_SERVICE.complete(null, T_COMPLETE_2_3_INPUT, FROM, null, null);
			assertEquals(T_COMPLETE_2_3_OUT_COMPLETIONS, result.getCompletions(FROM));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testComplete2_4() {
		try {
			GfServiceResultComplete result = GF_SERVICE.complete(null, T_COMPLETE_2_4_INPUT, FROM, null, null);
			assertEquals(T_COMPLETE_2_4_OUT_COMPLETIONS, result.getCompletions(FROM));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testComplete2_5() {
		try {
			GfServiceResultComplete result = GF_SERVICE.complete(null, T_COMPLETE_2_5_INPUT, FROM, null, null);
			assertEquals(T_COMPLETE_2_5_OUT_COMPLETIONS, result.getCompletions(FROM));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testComplete2_6() {
		try {
			GfServiceResultComplete result = GF_SERVICE.complete(null, T_COMPLETE_2_6_INPUT, FROM, null, null);
			assertEquals(T_COMPLETE_2_6_OUT_COMPLETIONS, result.getCompletions(FROM));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testAbstrtree() {
		try {
			GfServiceResultAbstrtree result = GF_SERVICE.abstrtree(T_LINEARIZE_1_TREE);
			//System.out.println("<img src=\"" + result.getDataUri() + "\">");
			assertEquals(T_ALIGNMENT_0_OUT_DATAURI_PREFIX,
					result.getDataUri().substring(0, T_ALIGNMENT_0_OUT_DATAURI_PREFIX.length()));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testAbstrtree1() {
		try {
			GfServiceResultAbstrtree result = GF_SERVICE.abstrtree(UNPARSABLE_STRING);
			assertEquals(T_ALIGNMENT_0_OUT_DATAURI_PREFIX,
					result.getDataUri().substring(0, T_ALIGNMENT_0_OUT_DATAURI_PREFIX.length()));
			fail(Constants.MSGY_GF_SERVICE_EXCEPTION);
		} catch (GfServiceException e) {
			assertEquals("-", e.getMessage());
		}
	}


	@Test
	public void testParsetree() {
		try {
			GfServiceResultParsetree result = GF_SERVICE.parsetree(T_LINEARIZE_1_TREE, FROM);
			//System.out.println("<img src=\"" + result.getDataUri() + "\">");
			assertEquals(T_ALIGNMENT_0_OUT_DATAURI_PREFIX,
					result.getDataUri().substring(0, T_ALIGNMENT_0_OUT_DATAURI_PREFIX.length()));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testAlignment() {
		try {
			GfServiceResultAlignment result = GF_SERVICE.alignment(T_LINEARIZE_1_TREE);
			//System.out.println("<img src=\"" + result.getDataUri() + "\">");
			assertEquals(T_ALIGNMENT_0_OUT_DATAURI_PREFIX,
					result.getDataUri().substring(0, T_ALIGNMENT_0_OUT_DATAURI_PREFIX.length()));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testBrowse() {
		try {
			GfServiceResultBrowse result = GF_SERVICE.browse(STARTCAT);
			assertEquals(T_BROWSE_0_PRODUCERS, result.getProducers());
			assertEquals(T_BROWSE_0_CONSUMERS, result.getConsumers());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testGenerateIt0() {
		try {
			List<String> prefixes = new ArrayList<String>();
			for (String prefix : GF_SERVICE.generatePrefix(FROM)) {
				prefixes.add(prefix);
			}
			assertEquals(T_GENERATE_0_SIZE, prefixes.size());
		} catch (RuntimeException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testGenerateIt1() {
		try {
			List<String> prefixes = new ArrayList<String>();
			for (String prefix : GF_SERVICE.generatePrefix(null, T_GENERATE_0_INPUT, FROM, null)) {
				prefixes.add(prefix);
			}
			assertEquals(T_GENERATE_0_SIZE, prefixes.size());
		} catch (RuntimeException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testGenerateIt2() {
		try {
			List<String> prefixes = new ArrayList<String>();
			// Parseable string gives at least 1 completion
			for (String prefix : GF_SERVICE.generatePrefix(null, T_COMPLETE_1_OUT, FROM, null)) {
				prefixes.add(prefix);
			}
			assertEquals(1, prefixes.size());
		} catch (RuntimeException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testGenerateIt3() {
		try {
			assertEquals(false, GF_SERVICE.generatePrefix(null, "blah", FROM, null).iterator().hasNext());
		} catch (RuntimeException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	private static Set<String> mkSetString(String[] array) {
		return new HashSet<String>(Arrays.asList(array));
	}
}
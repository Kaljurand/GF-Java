package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import ch.uzh.ifi.attempto.gfservice.GfService;
import ch.uzh.ifi.attempto.gfservice.GfServiceException;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultComplete;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultGrammar;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultLinearize;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultParse;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultRandom;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultTranslate;
import ch.uzh.ifi.attempto.gfservice.gfwebservice.GfWebService;


public class GfWebServiceTest {

	private static final String MSGY_ILLEGAL_ARGUMENT_EXCEPTION = "should throw IllegalArgumentException";
	private static final String MSG_URI_SYNTAX_EXCEPTION = "should NOT throw URISyntaxException";
	private static final String MSG_GF_SERVICE_EXCEPTION = "should NOT throw GfServiceException";

	private static final GfService GF_SERVICE;
	private static final String GRAMMAR = "grammars/Go.pgf";
	private static final String URL_AS_STRING = "http://localhost:41296/";
	private static final String NAME = "Go";
	private static final String STARTCAT = NAME;
	private static final String FROM = "GoEng";
	private static final String TO = "GoApp";
	private static final Set<String> LANGUAGES = new HashSet<String>(Arrays.asList(new String[] { "GoEst", "GoApp", "GoEng" }));
	private static URI URI = null;

	private static final String T_PARSE_1_CAT = "Number";
	private static final String T_PARSE_1_INPUT = "three";
	private static final Set<String> T_PARSE_1_OUT_TREES = new HashSet<String>(Arrays.asList(new String[] { "n3" }));

	private static final String T_LINEARIZE_1_TREE = "n3";
	private static final Set<String> T_LINEARIZE_1_OUT_TEXTS = new HashSet<String>(Arrays.asList(new String[] { "3" }));

	private static final List<String> T_RANDOM_1_OUT = Arrays.asList(new String[] { "it does not", "matter" });

	private static final String T_COMPLETE_0_INPUT = "go t";
	private static final Set<String> T_COMPLETE_0_OUT_COMPLETIONS = new HashSet<String>(Arrays.asList(new String[] { "three", "two" }));

	private static final String T_COMPLETE_1_OUT = "go five meters back";

	private static final String T_TRANSLATE_1_CAT = T_PARSE_1_CAT;
	private static final String T_TRANSLATE_1_INPUT = T_PARSE_1_INPUT;
	private static final Set<String> T_TRANSLATE_1_OUT_TRANSLATIONS = new HashSet<String>(Arrays.asList(new String[] { "3" }));

	static {
		try {
			URI = new URI(URL_AS_STRING);
		} catch (URISyntaxException e) {
			fail(MSG_URI_SYNTAX_EXCEPTION);
		}

		GF_SERVICE = new GfWebService(URI, GRAMMAR);
	}


	@Test
	public void testGrammar() {
		try {
			GfServiceResultGrammar result = GF_SERVICE.grammar();
			assertEquals(NAME, result.getName());
			assertEquals(STARTCAT, result.getStartcat());
			assertEquals(LANGUAGES, result.getLanguages().keySet());
		} catch (GfServiceException e) {
			fail(MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testParse() {
		try {
			GfServiceResultParse result = GF_SERVICE.parse(T_PARSE_1_CAT, T_PARSE_1_INPUT, FROM);
			assertEquals(T_PARSE_1_OUT_TREES, result.getTrees(FROM));
		} catch (GfServiceException e) {
			fail(MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testLinearize() {
		try {
			GfServiceResultLinearize result = GF_SERVICE.linearize(T_LINEARIZE_1_TREE, TO);
			assertEquals(T_LINEARIZE_1_OUT_TEXTS, result.getTexts(TO));
		} catch (GfServiceException e) {
			fail(MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testLinearize1() {
		try {
			GF_SERVICE.linearize(null, TO);
			fail(MSGY_ILLEGAL_ARGUMENT_EXCEPTION);
		} catch (IllegalArgumentException e) {
			assertEquals("Tree MUST be given", e.getMessage());
		} catch (GfServiceException e) {
			fail(MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testTranslate() {
		try {
			GfServiceResultTranslate result = GF_SERVICE.translate(T_TRANSLATE_1_CAT, T_TRANSLATE_1_INPUT, FROM, TO);
			assertEquals(T_TRANSLATE_1_OUT_TRANSLATIONS, result.getTranslations(FROM));
		} catch (GfServiceException e) {
			fail(MSG_GF_SERVICE_EXCEPTION);
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
			fail(MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testRandom() {
		try {
			GfServiceResultRandom result = GF_SERVICE.random(null, T_RANDOM_1_OUT.size());
			// We just check if the number of returned trees matches the number
			// of requested trees.
			assertEquals(T_RANDOM_1_OUT.size(), result.getTrees().size());
		} catch (GfServiceException e) {
			fail(MSG_GF_SERVICE_EXCEPTION);
		}
	}


	@Test
	public void testComplete() {
		try {
			GfServiceResultComplete result = GF_SERVICE.complete(null, T_COMPLETE_0_INPUT, FROM, 3);
			assertEquals(T_COMPLETE_0_OUT_COMPLETIONS, result.getCompletions(FROM));
		} catch (GfServiceException e) {
			fail(MSG_GF_SERVICE_EXCEPTION);
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
				fail(MSG_GF_SERVICE_EXCEPTION);
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

}
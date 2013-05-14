package ch.uzh.ifi.attempto.gfservice;

import java.util.List;
import java.util.Map;

/**
 * <p>The result of LinearizeAll for a given language is a list
 * of strings with possible repetition. For example, if the tree is
 * a lexical function then LinearizeAll returns all the forms of this function.</p>
 */
public interface GfServiceResultLinearizeAll extends GfResult {

	/**
	 * <p>SHOULD never return {@code null}.</p>
	 *
	 * @param to concrete language
	 * @return list of linearizations in the given language
	 */
	List<String> getTexts(String to);


	/**
	 * <p>SHOULD never return {@code null}.</p>
	 *
	 * @return set of language-linearizations pairs
	 */
	Map<String, List<String>> getTexts();

}
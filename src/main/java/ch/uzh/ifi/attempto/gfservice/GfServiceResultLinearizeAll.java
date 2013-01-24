package ch.uzh.ifi.attempto.gfservice;

import java.util.Map;
import java.util.Set;

public interface GfServiceResultLinearizeAll extends GfResult {

	/**
	 * <p>SHOULD never return {@code null}.</p>
	 *
	 * @param to concrete language
	 * @return set of linearizations in the given language
	 */
	Set<String> getTexts(String to);


	/**
	 * <p>SHOULD never return {@code null}.</p>
	 *
	 * @return set of language-linearizations pairs
	 */
	Map<String, Set<String>> getTexts();

}
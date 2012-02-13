package ch.uzh.ifi.attempto.gfservice;

import java.util.Map;
import java.util.Set;

public interface GfServiceResultLinearize extends GfResult {


	/**
	 * <p>SHOULD never return <code>null</code>.</p>
	 *
	 * @param to concrete language
	 * @return set of linearizations in the given language
	 */
	Set<String> getTexts(String to);


	/**
	 * <p>SHOULD never return <code>null</code>.</p>
	 *
	 * @return set of language-linearizations pairs
	 */
	Map<String, Set<String>> getTexts();

}

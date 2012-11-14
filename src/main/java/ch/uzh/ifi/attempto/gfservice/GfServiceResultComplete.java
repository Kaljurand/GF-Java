package ch.uzh.ifi.attempto.gfservice;

import java.util.Set;

public interface GfServiceResultComplete extends GfResult {

	/**
	 * <p>Returns a set of all possible tokens that can be used
	 * to complete the sentence in the given language.</p>
	 *
	 * <p>SHOULD never return <code>null</code>.</p>
	 *
	 * @param from concrete language
	 * @return set of tokens
	 */
	Set<String> getCompletions(String from);

}

package ch.uzh.ifi.attempto.gfservice;

import java.util.Set;

public interface GfServiceResultParse extends GfResult {

	/**
	 * <p>SHOULD never return <code>null</code>.</p>
	 *
	 * @param from concrete language
	 * @return set of abstract syntax trees for the given language
	 */
	Set<String> getTrees(String from);

}

package ch.uzh.ifi.attempto.gfservice;

import java.util.List;

public interface GfServiceResultRandom extends GfResult {


	/**
	 * <p>Returns a list of randomly generated abstract syntax trees.
	 * The order does not matter, but there can be duplicates.</p>
	 *
	 * <p>SHOULD never return <code>null</code>.</p>
	 *
	 * @return list of abstract syntax trees
	 */
	List<String> getTrees();

}

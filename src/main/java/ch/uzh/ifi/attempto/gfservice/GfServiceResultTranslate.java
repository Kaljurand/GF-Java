package ch.uzh.ifi.attempto.gfservice;

import java.util.Set;

public interface GfServiceResultTranslate extends GfResult {


	/**
	 * <p>Returns all the translations from the given language
	 * into all the requested languages. The results does not
	 * indicate which translation belongs to which language.</p>
	 *
	 * <p>SHOULD never return <code>null</code>.</p>
	 *
	 * @param from concrete language
	 * @return set of all possible translations into all requested languages
	 */
	Set<String> getTranslations(String from);

}
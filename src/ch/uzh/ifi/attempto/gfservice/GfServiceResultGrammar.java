package ch.uzh.ifi.attempto.gfservice;

import java.util.Map;
import java.util.Set;

public interface GfServiceResultGrammar extends GfResult {

	/**
	 * @return set of function names
	 */
	Set<String> getFunctions();


	/**
	 * @return map from concrete languages to ISO language codes
	 */
	Map<String, Set<String>> getLanguages();


	/**
	 * @return name of the start category
	 */
	String getStartcat();


	/**
	 * @return name of the abstract syntax in the grammar
	 */
	String getName();


	/**
	 * @return set of category names
	 */
	Set<String> getCategories();

}
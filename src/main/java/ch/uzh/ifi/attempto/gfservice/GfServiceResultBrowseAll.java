package ch.uzh.ifi.attempto.gfservice;

import java.util.Set;

public interface GfServiceResultBrowseAll extends GfResult {

	/**
	 * @return set of functions in the grammar
	 */
	Set<String> getFunctions();

	/**
	 * <p>Returns the name of the function ("printname") in the given
	 * language.</p>
	 *
	 * @param function function identifier
	 * @param language concrete language
	 * @return "printname" of the given function in the given language
	 */
	String getFunctionName(String function, String language);

	/**
	 * @param function function identifier
	 * @return definition of the function, e.g. "fun f : A -> B"
	 */
	String getFunctionDef(String function);

	/**
	 * @return set of categories in the grammar
	 */
	Set<String> getCategories();

	/**
	 * <p>Returns the name of the category ("printname") in the given
	 * language.</p>
	 *
	 * @param category category identifier
	 * @param language concrete language
	 * @return "printname" of the given category in the given language
	 */
	String getCategoryName(String category, String language);

	/**
	 * @param category category identifier
	 * @return definition of the function, e.g. "cat A"
	 */
	String getCategoryDef(String category);

	/**
	 * @param category
	 * @return set of functions that produce the given category
	 */
	Set<String> getProducers(String category);

	/**
	 * @param category
	 * @return set of functions that consume the given category
	 */
	Set<String> getConsumers(String category);

}
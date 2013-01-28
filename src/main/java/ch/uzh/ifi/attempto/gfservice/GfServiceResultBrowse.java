package ch.uzh.ifi.attempto.gfservice;

import java.util.Set;

public interface GfServiceResultBrowse extends GfResult {

	/**
	 * @return set of function names
	 */
	Set<String> getProducers();

	/**
	 * @return set of function names
	 */
	Set<String> getConsumers();

}
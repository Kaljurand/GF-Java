package ch.uzh.ifi.attempto.gfservice;

public interface GfParseResult extends GfResult {

	boolean isSuccess();
	boolean containsFilename(String key);
	String getResultCode();
	String getLocation();

}
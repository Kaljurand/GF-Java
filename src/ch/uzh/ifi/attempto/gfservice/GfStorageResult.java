package ch.uzh.ifi.attempto.gfservice;

public interface GfStorageResult extends GfResult {

	String getResultCode();
	String getMessage();
	String getCommand();

}
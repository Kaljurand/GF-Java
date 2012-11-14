package ch.uzh.ifi.attempto.gfservice;

public interface GfStorageResult extends GfResult {

	String getResultCode();
	boolean isSuccess();
	String getMessage();
	String getCommand();

}
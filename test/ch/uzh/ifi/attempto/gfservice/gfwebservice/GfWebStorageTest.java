package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import ch.uzh.ifi.attempto.gfservice.GfModule;
import ch.uzh.ifi.attempto.gfservice.GfServiceException;

public class GfWebStorageTest {

	private static final GfWebStorage GF_WEB_STORAGE;
	private static final String WS_URL_LOCALHOST = "http://localhost:41296";
	private static String DIR_NAME;
	private static URI URI = null;

	// Change _CLOUD -> _LOCALHOST to test against the GF webservice running on localhost
	static {
		try {
			URI = new URI(WS_URL_LOCALHOST);
		} catch (URISyntaxException e) {
			fail(Messages.MSG_URI_SYNTAX_EXCEPTION);
		}

		GF_WEB_STORAGE = new GfWebStorage(URI);

		try {
			DIR_NAME = GF_WEB_STORAGE.create();
		} catch (GfServiceException e) {
			DIR_NAME = null;
		}
	}


	@Test
	public void testStorageUpload() {
		try {
			String result = GF_WEB_STORAGE.upload(DIR_NAME, new GfModule("name.gf", "content"));
			assertEquals("TODO", result);
		} catch (GfServiceException e) {
			fail(Messages.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageMake() {
		try {
			String result = GF_WEB_STORAGE.make(DIR_NAME, new GfModule("Test.gf", "abstract Test = {}"));
			assertEquals("TODO", result);
		} catch (GfServiceException e) {
			fail(Messages.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}

}
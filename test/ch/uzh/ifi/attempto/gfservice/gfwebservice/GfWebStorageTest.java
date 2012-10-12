package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import ch.uzh.ifi.attempto.gfservice.GfModule;
import ch.uzh.ifi.attempto.gfservice.GfServiceException;

public class GfWebStorageTest {

	private static final GfWebStorage GF_WEB_STORAGE;
	private static String DIR_NAME;

	private static GfModule GF_MODULE_ERROR = null;
	private static GfModule GF_MODULE_GO = null;
	private static GfModule GF_MODULE_GO_ENG = null;
	private static GfModule GF_MODULE_GO_APP = null;

	static {
		try {
			GF_MODULE_ERROR = getGfModule("Error.gf");
			GF_MODULE_GO = getGfModule("Go.gf");
			GF_MODULE_GO_ENG = getGfModule("GoEng.gf");
			GF_MODULE_GO_APP = getGfModule("GoApp.gf");
		} catch (IOException e) {
			fail(Constants.MSG_PROGRAMMER_ERROR + ": " + e);
		}

		GF_WEB_STORAGE = new GfWebStorage(Constants.WS_URI);

		try {
			DIR_NAME = GF_WEB_STORAGE.create();
		} catch (GfServiceException e) {
			DIR_NAME = null;
		}
	}


	@Test
	public void testStorageMakeGo() {
		try {
			GfWebStorageResult result = GF_WEB_STORAGE.make(DIR_NAME, GF_MODULE_GO, GF_MODULE_GO_ENG, GF_MODULE_GO_APP);
			show(result);
			assertEquals(Constants.RESULT_CODE_OK, result.getResultCode());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageMakeError() {
		try {
			GfWebStorageResult result = GF_WEB_STORAGE.make(DIR_NAME, GF_MODULE_ERROR);
			show(result);
			assertEquals(Constants.RESULT_CODE_ERROR, result.getResultCode());
			assertEquals(true, result.getMessage().startsWith(GF_MODULE_ERROR.getName()));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	private static GfModule getGfModule(String name) throws IOException {
		return new GfModule(name, getFileContent(name));
	}


	private static String getFileContent(String name) throws IOException {
		return Resources.toString(Resources.getResource(GfWebStorageTest.class, name), Charsets.UTF_8);
	}


	private static void show(Object o) {
		System.out.println(o.toString());
	}

}
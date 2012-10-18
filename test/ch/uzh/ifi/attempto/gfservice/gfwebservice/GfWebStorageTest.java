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
	private static GfModule GF_MODULE_ERROR_SYNTAX_1 = null;
	private static GfModule GF_MODULE_ERROR_SYNTAX_2 = null;
	private static GfModule GF_MODULE_GO = null;
	private static GfModule GF_MODULE_GO_ENG = null;
	private static GfModule GF_MODULE_GO_APP = null;

	static {
		try {
			GF_MODULE_ERROR = getGfModule("Error");
			GF_MODULE_ERROR_SYNTAX_1 = getGfModule("ErrorSyntax1");
			GF_MODULE_ERROR_SYNTAX_2 = getGfModule("ErrorSyntax2");
			GF_MODULE_GO = getGfModule("Go");
			GF_MODULE_GO_ENG = getGfModule("GoEng");
			GF_MODULE_GO_APP = getGfModule("GoApp");
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
			assertEquals(true, result.isSuccess());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageParseError() {
		try {
			GfWebParseResult result = GF_WEB_STORAGE.parse(GF_MODULE_ERROR);
			show(result);
			assertEquals(true, result.containsFilename(GF_MODULE_ERROR.getFilename()));
			assertEquals(true, result.isSuccess());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageParseError1() {
		try {
			GfWebParseResult result = GF_WEB_STORAGE.parse(GF_MODULE_ERROR_SYNTAX_1);
			show(result);
			assertEquals(true, result.containsFilename(GF_MODULE_ERROR_SYNTAX_1.getFilename()));
			assertEquals(false, result.isSuccess());
			assertEquals(GfWebParseResult.RESULT_CODE_SYNTAX, result.getResultCode());
			assertEquals("1:9", result.getLocation());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageParseError2() {
		try {
			GfWebParseResult result = GF_WEB_STORAGE.parse(GF_MODULE_ERROR_SYNTAX_2);
			show(result);
			assertEquals(true, result.containsFilename(GF_MODULE_ERROR_SYNTAX_2.getFilename()));
			assertEquals(false, result.isSuccess());
			assertEquals(GfWebParseResult.RESULT_CODE_SYNTAX, result.getResultCode());
			assertEquals("2:7", result.getLocation());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageMakeError() {
		try {
			GfWebStorageResult result = GF_WEB_STORAGE.make(DIR_NAME, GF_MODULE_ERROR);
			show(result);
			assertEquals(GfWebStorageResult.RESULT_CODE_ERROR, result.getResultCode());
			assertEquals(true, result.getMessage().startsWith(GF_MODULE_ERROR.getName()));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	private static GfModule getGfModule(String name) throws IOException {
		String filename = name + GfModule.EXT;
		return new GfModule(name, getFileContent(filename));
	}


	private static String getFileContent(String name) throws IOException {
		return Resources.toString(Resources.getResource(GfWebStorageTest.class, name), Charsets.UTF_8);
	}


	private static void show(Object o) {
		System.out.println(o.toString());
	}

}
package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
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
	private static GfModule GF_MODULE_DUMMY_1 = null;
	private static GfModule GF_MODULE_DUMMY_2 = null;
	private static Set<String> FILENAMES;

	static {
		try {
			GF_MODULE_ERROR = getGfModule("Error");
			GF_MODULE_ERROR_SYNTAX_1 = getGfModule("ErrorSyntax1");
			GF_MODULE_ERROR_SYNTAX_2 = getGfModule("ErrorSyntax2");
			GF_MODULE_GO = getGfModule("Go");
			GF_MODULE_GO_ENG = getGfModule("GoEng");
			GF_MODULE_GO_APP = getGfModule("GoApp");
			GF_MODULE_DUMMY_1 = new GfModule("Dummy1", "Dummy Content 1");
			GF_MODULE_DUMMY_2 = new GfModule("Dummy2", "Dummy Content 2");
			FILENAMES = ImmutableSet.of(
					GF_MODULE_GO.getFilename(),
					GF_MODULE_DUMMY_1.getFilename(),
					GF_MODULE_DUMMY_2.getFilename());
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
	public void testStorageUpdateGo() {
		try {
			GfWebStorageResult result = GF_WEB_STORAGE.update(DIR_NAME, GF_MODULE_GO, GF_MODULE_GO_ENG, GF_MODULE_GO_APP);
			TestUtils.show(result);
			assertEquals(true, result.isSuccess());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageUpdateGo1() {
		try {
			GfWebStorageResult result = GF_WEB_STORAGE.update(DIR_NAME, GF_MODULE_GO_ENG,
					Arrays.asList(GF_MODULE_GO_APP.getName()));
			TestUtils.show(result);
			assertEquals(true, result.isSuccess());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageUpdateGo2() {
		try {
			GfWebStorageResult result = GF_WEB_STORAGE.update(
					DIR_NAME,
					"Unit",
					true,
					Arrays.asList(GF_MODULE_GO_APP.getName()),
					GF_MODULE_GO_ENG);
			TestUtils.show(result);
			assertEquals(true, result.isSuccess());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageUpdateGoError() {
		try {
			GfWebStorageResult result = GF_WEB_STORAGE.update(DIR_NAME, GF_MODULE_GO_ENG,
					Arrays.asList(Constants.NON_EXISTENT));
			TestUtils.show(result);
			assertEquals(false, result.isSuccess());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageParseError() {
		try {
			GfWebParseResult result = GF_WEB_STORAGE.parse(GF_MODULE_ERROR);
			TestUtils.show(result);
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
			TestUtils.show(result);
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
			TestUtils.show(result);
			assertEquals(true, result.containsFilename(GF_MODULE_ERROR_SYNTAX_2.getFilename()));
			assertEquals(false, result.isSuccess());
			assertEquals(GfWebParseResult.RESULT_CODE_SYNTAX, result.getResultCode());
			assertEquals("2:7", result.getLocation());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageUpdateError() {
		try {
			GfWebStorageResult result = GF_WEB_STORAGE.update(DIR_NAME, GF_MODULE_ERROR, Constants.EMPTY_STRING_SET);
			TestUtils.show(result);
			assertEquals(GfWebStorageResult.RESULT_CODE_ERROR, result.getResultCode());
			assertEquals(true, result.getMessage().startsWith(GF_MODULE_ERROR.getName()));
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageUploadGo() {
		try {
			GF_WEB_STORAGE.upload(DIR_NAME, GF_MODULE_GO, GF_MODULE_DUMMY_1, GF_MODULE_DUMMY_2);
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageUploadGoIntoNonExistentDir() {
		try {
			GF_WEB_STORAGE.upload(Constants.NON_EXISTENT, GF_MODULE_DUMMY_1);
			fail(Constants.MSGY_GF_SERVICE_EXCEPTION);
		} catch (GfServiceException e) {
			TestUtils.show(e);
		}
	}


	@Test
	public void testStorageRm() {
		try {
			GF_WEB_STORAGE.upload(DIR_NAME, GF_MODULE_GO);
			GF_WEB_STORAGE.rm(DIR_NAME, GF_MODULE_GO.getFilename());
		} catch (GfServiceException e) {
			fail(Constants.MSG_GF_SERVICE_EXCEPTION + ": " + e);
		}
	}


	@Test
	public void testStorageRmError() {
		try {
			GF_WEB_STORAGE.rm(DIR_NAME, Constants.NON_EXISTENT);
			fail(Constants.MSGY_GF_SERVICE_EXCEPTION);
		} catch (GfServiceException e) {
			TestUtils.show(e);
		}
	}


	@Test
	public void testStorageLsRm() {
		try {
			// Get the current directory listing
			GfWebStorageResultLs result = GF_WEB_STORAGE.ls(DIR_NAME, ".gf");
			// Remove all the files
			for (String filename : result.getFilenames()) {
				GF_WEB_STORAGE.rm(DIR_NAME, filename);
			}
			// Upload some files
			GF_WEB_STORAGE.upload(DIR_NAME, GF_MODULE_GO, GF_MODULE_DUMMY_1, GF_MODULE_DUMMY_2);
			// Get a new listing
			result = GF_WEB_STORAGE.ls(DIR_NAME, ".gf");
			// Check if the listing matches the uploaded files
			assertEquals(FILENAMES, result.getFilenames());
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

}
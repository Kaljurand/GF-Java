package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import ch.uzh.ifi.attempto.gfservice.GfServiceException;
import ch.uzh.ifi.attempto.gfservice.GfStorageResultLs;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Set;

public class GfWebStorageResultLs implements GfStorageResultLs {

	private final JSONArray mJa;

	public GfWebStorageResultLs(String jsonAsStr) throws IOException, ParseException, GfServiceException {

		Object obj = JSONValue.parseWithException(jsonAsStr);

		if (! (obj instanceof JSONArray)) {
			throw new GfServiceException("Expected JSON array");
		}

		mJa = (JSONArray) obj;
	}

	public Set<String> getFilenames() {
		return JsonUtils.makeStringSetFromJsonArray(mJa);
	}
}
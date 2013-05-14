package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultLinearizeAll;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultLinearize;

public class GfWebServiceResultLinearizeAll implements GfServiceResultLinearizeAll {

	public static final String TO = "to";
	public static final String TEXT = "texts"; // Note: "texts"

	private final Map<String, List<String>> mMultimap;

	public GfWebServiceResultLinearizeAll(String jsonAsStr) throws IOException, ParseException {
		Object obj = JSONValue.parseWithException(jsonAsStr);
		mMultimap = JsonUtils.makeMultimapListFromJsonArray((JSONArray) obj, TO, TEXT);
	}


	public List<String> getTexts(String to) {
		return mMultimap.get(to);
	}


	public Map<String, List<String>> getTexts() {
		return mMultimap;
	}
}
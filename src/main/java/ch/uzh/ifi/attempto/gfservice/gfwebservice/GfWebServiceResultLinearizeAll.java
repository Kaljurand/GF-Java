package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultLinearizeAll;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultLinearize;

public class GfWebServiceResultLinearizeAll implements GfServiceResultLinearizeAll {

	public static final String TO = "to";
	public static final String TEXT = "texts"; // Note: "texts"

	private final Map<String, Set<String>> mMultimap;

	public GfWebServiceResultLinearizeAll(String jsonAsStr) throws IOException, ParseException {
		Object obj = JSONValue.parseWithException(jsonAsStr);
		mMultimap = JsonUtils.makeMultimapSetFromJsonArray((JSONArray) obj, TO, TEXT);
	}


	public Set<String> getTexts(String to) {
		return mMultimap.get(to);
	}


	public Map<String, Set<String>> getTexts() {
		return mMultimap;
	}
}
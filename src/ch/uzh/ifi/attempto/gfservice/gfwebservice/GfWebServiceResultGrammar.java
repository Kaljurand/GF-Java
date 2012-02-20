package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultGrammar;

/**
 *
 * @author Kaarel Kaljurand
 *
 */
public class GfWebServiceResultGrammar implements GfServiceResultGrammar {

	public static final String FUNCTIONS = "functions";
	public static final String LANGUAGES = "languages";
	public static final String STARTCAT = "startcat";
	public static final String NAME = "name";
	public static final String CATEGORIES = "categories";

	private final Set<String> mFunctions;
	private final Map<String, Set<String>> mLanguages;
	private final String mStartcat;
	private final String mName;
	private final Set<String> mCategories;

	public GfWebServiceResultGrammar(String jsonAsStr) throws IOException, ParseException {

		JSONObject jo = (JSONObject) JSONValue.parseWithException(jsonAsStr);

		mFunctions = JsonUtils.makeStringSetFromJsonArray((JSONArray) jo.get(FUNCTIONS));
		mLanguages = JsonUtils.makeMultimapSetFromJsonArray((JSONArray) jo.get(LANGUAGES), "name", "languageCode");
		mStartcat = jo.get(STARTCAT).toString();
		mName = jo.get(NAME).toString();
		mCategories = JsonUtils.makeStringSetFromJsonArray((JSONArray) jo.get(CATEGORIES));
	}

	public Set<String> getFunctions() {
		return mFunctions;
	}

	public Map<String, Set<String>> getLanguages() {
		return mLanguages;
	}

	public String getStartcat() {
		return mStartcat;
	}

	public String getName() {
		return mName;
	}

	public Set<String> getCategories() {
		return mCategories;
	}

}
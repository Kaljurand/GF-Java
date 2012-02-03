package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultLinearize;

/**
 * @author Kaarel Kaljurand
 *
 */
public class GfWebServiceResultLinearize implements GfServiceResultLinearize {

	public static final String TO = "to";
	public static final String TEXT = "text";

	private final Map<String, Set<String>> mMultimap;

	public GfWebServiceResultLinearize(String jsonAsStr) throws IOException, ParseException {
		Object obj = JSONValue.parseWithException(jsonAsStr);
		mMultimap = JsonUtils.makeMultimapSetFromJsonArray((JSONArray) obj, TO, TEXT);
	}


	public Set<String> getTexts(String to) {
		return mMultimap.get(to);
	}
}
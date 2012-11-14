package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultParse;

/**
 * TODO: catch all cast errors and map them to checked exception ResultParseError
 *
 * TODO: parse from a stream reader
 *
 * TODO: return immutable objects
 * 
 * TODO: this is almost identical to GfWebServiceResultComplete
 *
 * @author Kaarel Kaljurand
 *
 */
public class GfWebServiceResultParse implements GfServiceResultParse {

	public static final String FROM = "from";
	public static final String TREES = "trees";

	private final Map<String, Set<String>> map = new HashMap<String, Set<String>>();

	public GfWebServiceResultParse(String jsonAsStr) throws IOException, ParseException {

		Object obj = JSONValue.parseWithException(jsonAsStr);

		for (Object o : (JSONArray) obj) {
			JSONObject jo = (JSONObject) o;
			map.put(jo.get(FROM).toString(), JsonUtils.makeStringSetFromJsonArray((JSONArray) jo.get(TREES)));
		}

	}

	public Set<String> getTrees(String from) {
		return map.get(from);
	}
}
package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultComplete;

/**
 * TODO: catch all cast errors and map them to checked exception ResultParseError
 * TODO: parse from a stream reader
 * 
 * @author Kaarel Kaljurand
 *
 */
public class GfWebServiceResultComplete implements GfServiceResultComplete {

	public static final String FROM = "from";
	public static final String COMPLETIONS = "completions";

	private final Map<String, Set<String>> map = new HashMap<String, Set<String>>();


	public GfWebServiceResultComplete(String from, Set<String> completions) {
		map.put(from, completions);
	}


	public GfWebServiceResultComplete(String jsonAsStr) throws IOException, ParseException {

		Object obj = JSONValue.parseWithException(jsonAsStr);

		for (Object o : (JSONArray) obj) {
			JSONObject jo = (JSONObject) o;
			Set<String> completions = JsonUtils.makeStringSetFromJsonArray((JSONArray) jo.get(COMPLETIONS));
			map.put(jo.get(FROM).toString(), completions);
		}

	}

	public Set<String> getCompletions(String from) {
		return map.get(from);
	}

	public void prefix(String from, String prefix) {
		if (prefix == null || prefix.isEmpty()) {
			return;
		}
		Set<String> newSet = new HashSet<String>();
		for (String str : map.get(from)) {
			newSet.add(prefix + str);
		}
		map.put(from, newSet);
	}
}
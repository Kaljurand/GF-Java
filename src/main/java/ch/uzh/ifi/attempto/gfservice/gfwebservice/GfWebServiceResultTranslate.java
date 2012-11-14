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

import ch.uzh.ifi.attempto.gfservice.GfServiceResultTranslate;

/**
 * TODO: catch all cast errors and map them to checked exception ResultParseError
 * TODO: parse from a stream reader
 *
 * @author Kaarel Kaljurand
 *
 */
public class GfWebServiceResultTranslate implements GfServiceResultTranslate {

	public static final String FROM = "from";
	public static final String TO = "to";
	public static final String TREE = "tree";
	public static final String TRANSLATIONS = "translations";
	public static final String LINEARIZATIONS = "linearizations";
	public static final String TEXT = "text";

	private final Map<String, Set<String>> map = new HashMap<String, Set<String>>();

	public GfWebServiceResultTranslate(String jsonAsStr) throws IOException, ParseException {
		Object obj = JSONValue.parseWithException(jsonAsStr);
		for (Object o : (JSONArray) obj) {
			JSONObject jo = (JSONObject) o;
			map.put(jo.get(FROM).toString(), getLinearizations((JSONArray) jo.get(TRANSLATIONS)));
		}
	}

	public Set<String> getTranslations(String from) {
		return map.get(from);
	}


	private Set<String> getLinearizations(JSONArray jsonArray) {
		Set<String> lins = new HashSet<String>();
		if (jsonArray == null) {
			return lins;
		}
		for (Object o : jsonArray) {
			JSONObject jo = (JSONObject) o;
			Map<String, Set<String>> map = JsonUtils.makeMultimapSetFromJsonArray((JSONArray) jo.get(LINEARIZATIONS), TO, TEXT);
			for (String s : map.keySet()) {
				lins.addAll(map.get(s));
			}
		}
		return lins;
	}
}
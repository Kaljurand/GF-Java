package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonUtils {

	/**
	 * <p>Converts a JSONArray into a list of strings, not
	 * caring about the types of the objects in the array.</p>
	 *
	 * @param jsonArray JSON array
	 * @return list of string-representations of the array objects
	 */
	public static List<String> makeStringListFromJsonArray(JSONArray jsonArray) {
		List<String> list = new ArrayList<String>();
		if (jsonArray == null) {
			return list;
		}
		for (Object o : jsonArray) {
			list.add(o.toString());
		}
		return list;
	}


	/**
	 * <p>Converts a JSONArray into a set of strings, not
	 * caring about the types of the objects in the array.</p>
	 *
	 * @param jsonArray JSON array
	 * @return set of string-representations of the array objects
	 */
	public static Set<String> makeStringSetFromJsonArray(JSONArray jsonArray) {
		Set<String> set = new HashSet<String>();
		if (jsonArray == null) {
			return set;
		}
		for (Object o : jsonArray) {
			set.add(o.toString());
		}
		return set;
	}


	/**
	 * <p>Converts a JSONArray of JSON objects into a multimap of strings.</p>
	 *
	 * @param jsonArray JSON array
	 * @param keyName name of the JSON field whose value to be used as the map key
	 * @param valName name of the JSON field whose value to be put in the map value (set)
	 * @return map from Strings to Sets of Strings
	 */
	public static Map<String, Set<String>> makeMultimapSetFromJsonArray(JSONArray jsonArray, String keyName, String valName) {
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		if (jsonArray == null) {
			return map;
		}
		for (Object o : jsonArray) {
			JSONObject jo = (JSONObject) o;

			String key = jo.get(keyName).toString();
			String val = jo.get(valName).toString();

			if (map.containsKey(key)) {
				map.get(key).add(val);
			} else {
				Set<String> list = new HashSet<String>();
				list.add(val);
				map.put(key, list);
			}
		}
		return map;
	}

}
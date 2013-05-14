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
	 * @param keyName   name of the JSON field whose value to use as the map key
	 * @param valName   name of the JSON field whose value to put in the map value (list of strings)
	 * @return map from Strings to List of Strings
	 */
	public static Map<String, List<String>> makeMultimapListFromJsonArray(JSONArray jsonArray, String keyName, String valName) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		if (jsonArray == null) {
			return map;
		}
		for (Object o : jsonArray) {
			JSONObject jo = (JSONObject) o;

			// We assume that the key is a string
			String key = jo.get(keyName).toString();

			List<String> list = map.get(key);
			if (list == null) {
				list = new ArrayList<String>();
				map.put(key, list);
			}

			// If the value is an array then we add all the elements of this array,
			// otherwise we just add the serialization of the object (which we expect
			// to be a String.
			Object val = jo.get(valName);

			if (val instanceof JSONArray) {
				list.addAll((JSONArray) val);
			} else {
				list.add(val.toString());
			}
		}
		return map;
	}


	/**
	 * <p>Converts a JSONArray of JSON objects into a multimap of strings.</p>
	 *
	 * @param jsonArray JSON array
	 * @param keyName   name of the JSON field whose value to be used as the map key
	 * @param valName   name of the JSON field whose value to be put in the map value (set)
	 * @return map from Strings to Sets of Strings
	 */
	public static Map<String, Set<String>> makeMultimapSetFromJsonArray(JSONArray jsonArray, String keyName, String valName) {
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		if (jsonArray == null) {
			return map;
		}
		for (Object o : jsonArray) {
			JSONObject jo = (JSONObject) o;

			// We assume that the key is a string
			String key = jo.get(keyName).toString();

			Set<String> set = map.get(key);
			if (set == null) {
				set = new HashSet<String>();
				map.put(key, set);
			}

			// If the value is an array then we add all the elements of this array,
			// otherwise we just add the serialization of the object (which we expect
			// to be a String.
			Object val = jo.get(valName);

			if (val instanceof JSONArray) {
				set.addAll((JSONArray) val);
			} else {
				set.add(val.toString());
			}
		}
		return map;
	}


	public static String getString(JSONObject jo, String key) {
		Object obj = jo.get(key);
		if (obj instanceof String) {
			return obj.toString();
		}
		return null;
	}

}
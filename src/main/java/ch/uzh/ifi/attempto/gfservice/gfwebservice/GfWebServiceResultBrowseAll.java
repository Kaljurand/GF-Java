package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import com.google.common.collect.ImmutableSet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultBrowseAll;

import java.io.IOException;
import java.lang.Object;
import java.lang.String;
import java.util.Set;

/**
 * TODO: any NPE or CCE indicates a JSON format problem which should be reported
 * via some ParseException
 *
 * @author Kaarel Kaljurand
 */
public class GfWebServiceResultBrowseAll implements GfServiceResultBrowseAll {

	public static final String CATEGORIES = "cats";
	public static final String FUNCTIONS = "funs";
	public static final String DEF = "def";
	public static final String PRINTNAMES = "printnames";
	public static final String PRODUCERS = "producers";
	public static final String CONSUMERS = "consumers";

	private final JSONObject mJo;

	public GfWebServiceResultBrowseAll(String jsonAsStr) throws IOException, ParseException {
		mJo = (JSONObject) JSONValue.parseWithException(jsonAsStr);
	}

	public Set<String> getFunctions() {
		return getItems(FUNCTIONS);
	}

	public String getFunctionName(String function, String language) {
		return getName(FUNCTIONS, function, language);
	}

	public String getFunctionDef(String function) {
		return getDef(FUNCTIONS, function);
	}

	public Set<String> getCategories() {
		return getItems(CATEGORIES);
	}

	public String getCategoryName(String category, String language) {
		return getName(CATEGORIES, category, language);
	}

	public String getCategoryDef(String category) {
		return getDef(CATEGORIES, category);
	}

	public Set<String> getProducers(String category) {
		return getComponents(CATEGORIES, category, PRODUCERS);
	}

	public Set<String> getConsumers(String category) {
		return getComponents(CATEGORIES, category, CONSUMERS);
	}


	// We assume that the entries for funs and cats exist, although
	// they might be empty.
	private Set<String> getItems(String type) {
		return ((JSONObject) mJo.get(type)).keySet();
	}

	// If the item was not found or didn't have a definition then return null.
	private String getDef(String type, String id) {
		JSONObject id1 = getItem(type, id);
		if (id1 == null) {
			return null;
		}
		Object def = id1.get(DEF);
		if (def == null) {
			return null;
		}
		return def.toString();
	}

	// If the item was not found or didn't have a label then return null.
	// We assume that the printnames-entry is present though.
	private String getName(String type, String id, String language) {
		JSONObject id1 = getItem(type, id);
		if (id1 == null) {
			return null;
		}
		Object name = ((JSONObject) (id1.get(PRINTNAMES))).get(language);
		if (name == null) {
			return null;
		}
		return name.toString();
	}

	// If the item was not found then return null.
	// We assume that the components-entry is always present, although possibly empty.
	// We also assume that the components-entry is an array where order and duplication
	// does not matter.
	private Set<String> getComponents(String type, String id, String componentType) {
		JSONObject id1 = getItem(type, id);
		if (id1 == null) {
			return null;
		}
		return ImmutableSet.copyOf((JSONArray) (id1.get(componentType)));
	}

	// If the item is not present or does not have an attached record (JSONObject)
	// then return null.
	private JSONObject getItem(String type, String id) {
		Object id1 = ((JSONObject) mJo.get(type)).get(id);
		if (id1 == null || ! (id1 instanceof JSONObject)) {
			return null;
		}
		return (JSONObject) id1;
	}
}
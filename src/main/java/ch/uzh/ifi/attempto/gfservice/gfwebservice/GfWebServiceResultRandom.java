package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultRandom;

/**
 * TODO: catch all cast errors and map them to checked exception ResultParseError
 *
 * TODO: parse from a stream reader
 *
 * TODO: return immutable objects
 *
 * @author Kaarel Kaljurand
 *
 */
public class GfWebServiceResultRandom implements GfServiceResultRandom {

	public static final String TREE = "tree";
	public static final String LINEARIZATIONS = "linearizations";

	private final List<String> trees = new ArrayList<String>();

	public GfWebServiceResultRandom(String jsonAsStr) throws IOException, ParseException {

		Object obj = JSONValue.parseWithException(jsonAsStr);

		for (Object o : (JSONArray) obj) {
			JSONObject jo = (JSONObject) o;
			trees.add(jo.get(TREE).toString());
		}

	}

	public List<String> getTrees() {
		return trees;
	}
}
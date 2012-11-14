package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.io.IOException;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultBrowse;

/**
 * @author Kaarel Kaljurand
 */
public class GfWebServiceResultBrowse implements GfServiceResultBrowse {

	public static final String PRODUCERS = "producers";
	public static final String CONSUMERS = "consumers";

	private final Set<String> mProducers;
	private final Set<String> mConsumers;

	public GfWebServiceResultBrowse(String jsonAsStr) throws IOException, ParseException {

		JSONObject jo = (JSONObject) JSONValue.parseWithException(jsonAsStr);

		mProducers = JsonUtils.makeStringSetFromJsonArray((JSONArray) jo.get(PRODUCERS));
		mConsumers = JsonUtils.makeStringSetFromJsonArray((JSONArray) jo.get(CONSUMERS));
	}

	public Set<String> getProducers() {
		return mProducers;
	}

	public Set<String> getConsumers() {
		return mConsumers;
	}

}
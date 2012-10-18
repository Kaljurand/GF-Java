package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import ch.uzh.ifi.attempto.gfservice.GfParseResult;
import ch.uzh.ifi.attempto.gfservice.GfServiceException;

/**
 * <p>The error term has the form:</p>
 *
 * <pre>
 * {"ErrorSyntax2.gf":{"error":"syntax error","location":"2:7"}}
 * </pre>
 *
 * <p>The GF module term (in case there is no error) has the form:
 * TODO.</p>
 *
 * @author Kaarel Kaljurand
 *
 */
public class GfWebParseResult implements GfParseResult {

	public static final String RESULT_CODE = "error";
	public static final String RESULT_CODE_SYNTAX = "syntax error";
	public static final String LOCATION = "location";

	private final JSONObject mJo;
	private JSONObject mJo1;

	public GfWebParseResult(String jsonAsStr) throws IOException, ParseException, GfServiceException {

		Object obj = JSONValue.parseWithException(jsonAsStr);

		if (! (obj instanceof JSONObject)) {
			throw new GfServiceException("Expected JSON object");
		}

		mJo = (JSONObject) obj;

		// Get the first key, because there is only one key-value pair in this structure.
		// TODO: make sure this is actually so
		try {
			Object key1 = mJo.keySet().iterator().next();
			Object val1 = mJo.get(key1);
			if (val1 instanceof JSONObject) {
				mJo1 = (JSONObject) val1;
			}
		} catch (NoSuchElementException e) {}

		if (mJo1 == null) {
			throw new GfServiceException("Expected first value to be JSON object");
		}
	}


	public String getResultCode() {
		return JsonUtils.getString(mJo1, RESULT_CODE);
	}


	public boolean isSuccess() {
		return null == mJo1.get(RESULT_CODE);
	}


	public String getLocation() {
		return JsonUtils.getString(mJo1, LOCATION);
	}


	public boolean containsFilename(String filename) {
		return null != mJo.get(filename);
	}


	public String toString() {
		return mJo.toJSONString();
	}
}
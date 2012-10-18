package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import ch.uzh.ifi.attempto.gfservice.GfServiceException;
import ch.uzh.ifi.attempto.gfservice.GfStorageResult;

public class GfWebStorageResult implements GfStorageResult {

	public static final String RESULT_CODE = "errorcode";
	public static final String MESSAGE = "output";
	public static final String COMMAND = "command";

	public static final String RESULT_CODE_OK = "OK";
	public static final String RESULT_CODE_ERROR = "Error";

	private final JSONObject mJo;

	public GfWebStorageResult(String jsonAsStr) throws IOException, ParseException, GfServiceException {

		Object obj = JSONValue.parseWithException(jsonAsStr);

		if (! (obj instanceof JSONObject)) {
			throw new GfServiceException("Expected JSON object");
		}

		mJo = (JSONObject) obj;
	}


	public String getResultCode() {
		return JsonUtils.getString(mJo, RESULT_CODE);
	}


	public boolean isSuccess() {
		return RESULT_CODE_OK.equals(getResultCode());
	}


	public String getMessage() {
		return JsonUtils.getString(mJo, MESSAGE);
	}


	public String getCommand() {
		return JsonUtils.getString(mJo, COMMAND);
	}


	public String toString() {
		return mJo.toJSONString();
	}
}
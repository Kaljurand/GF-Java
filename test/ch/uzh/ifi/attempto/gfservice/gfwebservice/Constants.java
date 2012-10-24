package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class Constants {

	public static final String WS_URL_LOCALHOST = "http://localhost:41296";
	public static final String WS_URL_CLOUD = "http://cloud.grammaticalframework.org";
	public static final String GRAMMAR_DIR_CLOUD = "/tmp/gfse.74044909/";
	public static final String GRAMMAR_DIR_LOCALHOST = "/grammars/";

	public static final String MSGY_ILLEGAL_ARGUMENT_EXCEPTION = "should throw IllegalArgumentException";
	public static final String MSG_URI_SYNTAX_EXCEPTION = "should NOT throw URISyntaxException";
	public static final String MSG_GF_SERVICE_EXCEPTION = "should NOT throw GfServiceException";
	public static final String MSGY_GF_SERVICE_EXCEPTION = "should throw GfServiceException";
	public static final String MSG_PROGRAMMER_ERROR = "programmer error";

	public static final Set<String> EMPTY_STRING_SET = ImmutableSet.of();
	public static final String NON_EXISTENT = "non_existent";

	public static URI WS_URI = null;

	static {
		try {
			WS_URI = new URI(WS_URL_LOCALHOST);
		} catch (URISyntaxException e) { }
	}

}
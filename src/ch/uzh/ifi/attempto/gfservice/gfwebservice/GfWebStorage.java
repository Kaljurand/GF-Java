package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.parser.ParseException;

import ch.uzh.ifi.attempto.gfservice.GfModule;
import ch.uzh.ifi.attempto.gfservice.GfServiceException;
import ch.uzh.ifi.attempto.gfservice.GfStorage;

/**
 * @author Kaarel Kaljurand
 */
public class GfWebStorage implements GfStorage {

	private static final String DIR = "dir";
	private static final String COMMAND = "command";
	private static final String COMMAND_REMAKE = "remake";

	private final URI mUriNew;
	private final URI mUriParse;
	private final URI mUriCloud;

	public GfWebStorage(URI uri) {
		mUriNew = URI.create(uri + "/new");
		mUriParse = URI.create(uri + "/parse");
		mUriCloud = URI.create(uri + "/cloud");
	}


	public String create() throws GfServiceException {
		return HttpUtils.getHttpEntityAsString(new DefaultHttpClient(), new HttpGet(mUriNew));
	}


	public GfWebParseResult parse(GfModule module) throws GfServiceException {
		try {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair(module.getFilename(), module.getContent()));
			HttpPost post = HttpUtils.getHttpPost(mUriParse, pairs);
			return new GfWebParseResult(HttpUtils.getHttpEntityAsString(new DefaultHttpClient(), post));
		} catch (IOException e) {
			throw new GfServiceException(e);
		} catch (ParseException e) {
			throw new GfServiceException(e);
		}
	}


	public GfWebStorageResult update(String dirName, GfModule module, Iterable<String> moduleNames) throws GfServiceException {
		try {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair(DIR, dirName));
			pairs.add(new BasicNameValuePair(COMMAND, COMMAND_REMAKE));
			pairs.add(new BasicNameValuePair(module.getFilename(), module.getContent()));
			for (String moduleName : moduleNames) {
				// Note that "null" sets the query parameter value to empty string
				pairs.add(new BasicNameValuePair(moduleName + GfModule.EXT, null));
			}
			HttpPost post = HttpUtils.getHttpPost(mUriCloud, pairs);
			return new GfWebStorageResult(HttpUtils.getHttpEntityAsString(new DefaultHttpClient(), post));
		} catch (IOException e) {
			throw new GfServiceException(e);
		} catch (ParseException e) {
			throw new GfServiceException(e);
		}
	}


	public GfWebStorageResult update(String dirName, GfModule... modules) throws GfServiceException {
		try {
			return new GfWebStorageResult(push(mUriCloud, COMMAND_REMAKE, dirName, modules));
		} catch (IOException e) {
			throw new GfServiceException(e);
		} catch (ParseException e) {
			throw new GfServiceException(e);
		}
	}


	private String push(URI uri, String command, String dirName, GfModule... modules) throws GfServiceException {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (GfModule module : modules) {
			pairs.add(new BasicNameValuePair(module.getFilename(), module.getContent()));
		}
		pairs.add(new BasicNameValuePair(DIR, dirName));
		pairs.add(new BasicNameValuePair(COMMAND, command));
		HttpPost post = HttpUtils.getHttpPost(uri, pairs);
		return HttpUtils.getHttpEntityAsString(new DefaultHttpClient(), post);
	}
}
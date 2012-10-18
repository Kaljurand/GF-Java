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


	public GfWebStorageResult make(String dirName, GfModule... modules) throws GfServiceException {
		try {
			return new GfWebStorageResult(push(mUriCloud, "make", dirName, modules));
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
		pairs.add(new BasicNameValuePair("dir", dirName));
		pairs.add(new BasicNameValuePair("command", command));
		HttpPost post = HttpUtils.getHttpPost(uri, pairs);
		return HttpUtils.getHttpEntityAsString(new DefaultHttpClient(), post);
	}
}
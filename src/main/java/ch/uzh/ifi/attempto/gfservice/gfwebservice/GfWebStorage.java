package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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
	private static final String COMMAND_UPLOAD = "upload";
	private static final String COMMAND_LS = "ls";
	private static final String COMMAND_RM = "rm";
	private static final String COMMAND_DOWNLOAD = "download";
	private static final String START_CAT = "--startcat";
	private static final String OPTIMIZE_PGF = "--optimize-pgf";
	private static final String FILE = "file";
	private static final String EXT = "ext";

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
			List<NameValuePair> pairs = makeParameters(COMMAND_REMAKE, dirName, module);
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


	public GfWebStorageResult update(String dirName, Iterable<String> moduleNames) throws GfServiceException {
		try {
			List<NameValuePair> pairs = makeParameters(COMMAND_REMAKE, dirName);
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


	public GfWebStorageResult update(String dirName, String startCat, boolean optimize,
									 Iterable<String> moduleNames, GfModule... modules)
			throws GfServiceException {

		try {
			List<NameValuePair> pairs = makeParameters(COMMAND_REMAKE, dirName);
			for (String moduleName : moduleNames) {
				// Note that "null" sets the query parameter value to empty string
				pairs.add(new BasicNameValuePair(moduleName + GfModule.EXT, null));
			}
			for (GfModule module : modules) {
				pairs.add(new BasicNameValuePair(module.getFilename(), module.getContent()));
			}
			if (startCat != null) {
				pairs.add(new BasicNameValuePair(START_CAT, startCat));
			}
			if (optimize) {
				pairs.add(new BasicNameValuePair(OPTIMIZE_PGF, null));
			}
			HttpPost post = HttpUtils.getHttpPost(mUriCloud, pairs);
			return new GfWebStorageResult(HttpUtils.getHttpEntityAsString(new DefaultHttpClient(), post));
		} catch (IOException e) {
			throw new GfServiceException(e);
		} catch (ParseException e) {
			throw new GfServiceException(e);
		}
	}


	public void upload(String dirName, GfModule... modules) throws GfServiceException {
		try {
			HttpPost post = HttpUtils.getHttpPost(mUriCloud, makeParameters(COMMAND_UPLOAD, dirName, modules));
			HttpResponse response = new DefaultHttpClient().execute(post);

			int statusCode = response.getStatusLine().getStatusCode();
			// 204 = "No content"
			// file upload produces no content, another status code indicates an error
			if (statusCode != HttpStatus.SC_NO_CONTENT) {
				throw new GfServiceException(statusCode + ": " + response.getStatusLine().getReasonPhrase());
			}
		} catch (IOException e) {
			throw new GfServiceException(e);
		}
	}


	public GfWebStorageResultLs ls(String dirName, String extension) throws GfServiceException {
		List<NameValuePair> pairs = makeParameters(COMMAND_LS, dirName);
		pairs.add(new BasicNameValuePair(EXT, extension));
		try {
			HttpPost post = HttpUtils.getHttpPost(mUriCloud, pairs);
			return new GfWebStorageResultLs(HttpUtils.getHttpEntityAsString(new DefaultHttpClient(), post));
		} catch (IOException e) {
			throw new GfServiceException(e);
		} catch (ParseException e) {
			throw new GfServiceException(e);
		}
	}


	public void rm(String dirName, String path) throws GfServiceException {
		List<NameValuePair> pairs = makeParameters(COMMAND_RM, dirName);
		pairs.add(new BasicNameValuePair(FILE, path));
		try {
			HttpPost post = HttpUtils.getHttpPost(mUriCloud, pairs);
			HttpResponse response = new DefaultHttpClient().execute(post);

			int statusCode = response.getStatusLine().getStatusCode();
			// 404 = "File not found"
			// 400 = "Bad request" (the server seems to send this in case of non existent files)
			if (statusCode == HttpStatus.SC_NOT_FOUND || statusCode == HttpStatus.SC_BAD_REQUEST) {
				throw new GfServiceException(statusCode + ": " + response.getStatusLine().getReasonPhrase());
			}
		} catch (IOException e) {
			throw new GfServiceException(e);
		}
	}


	public void download(String dirName, String path) throws GfServiceException {
		// TODO
	}


	private String push(URI uri, String command, String dirName, GfModule... modules) throws GfServiceException {
		HttpPost post = HttpUtils.getHttpPost(uri, makeParameters(command, dirName, modules));
		return HttpUtils.getHttpEntityAsString(new DefaultHttpClient(), post);
	}


	private List<NameValuePair> makeParameters(String command, String dirName, GfModule... modules) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (GfModule module : modules) {
			pairs.add(new BasicNameValuePair(module.getFilename(), module.getContent()));
		}
		pairs.add(new BasicNameValuePair(DIR, dirName));
		pairs.add(new BasicNameValuePair(COMMAND, command));
		return pairs;
	}
}
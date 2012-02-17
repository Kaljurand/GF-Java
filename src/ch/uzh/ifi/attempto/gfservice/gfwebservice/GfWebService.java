package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.ParseException;

import ch.uzh.ifi.attempto.gfservice.Command;
import ch.uzh.ifi.attempto.gfservice.GfService;
import ch.uzh.ifi.attempto.gfservice.GfServiceException;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultAbstrtree;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultParsetree;
import ch.uzh.ifi.attempto.gfservice.Param;

/**
 * @author Kaarel Kaljurand
 */
public class GfWebService implements GfService {

	private static final int MAX_HTTP_GET_LENGTH = 1000;
	private static final String ERROR_MESSAGE_ENTITY_NULL = "Response entity is null";

	private final URI mUri;
	private String mGrammar;

	public GfWebService(URI uri, String grammar) {
		mUri = uri;
		mGrammar = grammar;
	}


	public GfWebServiceResultGrammar grammar() throws GfServiceException {
		Params p = new Params(Command.GRAMMAR);
		String response = getResponseAsString(p.get());
		try {
			return new GfWebServiceResultGrammar(response);
		} catch (IOException e) {
			throw new GfServiceException(e);
		} catch (ParseException e) {
			throw new GfServiceException(e);
		}
	}


	public GfWebServiceResultParse parse(String cat, String input, String from) throws GfServiceException {
		Params p = new Params(Command.PARSE);
		p.add(Param.CAT, cat);
		p.add(Param.INPUT, input);
		p.add(Param.FROM, from);
		String response = getResponseAsString(p.get());
		try {
			return new GfWebServiceResultParse(response);
		} catch (IOException e) {
			throw new GfServiceException(e);
		} catch (ParseException e) {
			throw new GfServiceException(e);
		}
	}


	public GfWebServiceResultLinearize linearize(String tree, String to) throws GfServiceException {
		if (tree == null) {
			throw new IllegalArgumentException("Tree MUST be given");
		}
		Params p = new Params(Command.LINEARIZE);
		p.add(Param.TREE, tree);
		p.add(Param.TO, to);
		String response = getResponseAsString(p.get());
		try {
			return new GfWebServiceResultLinearize(response);
		} catch (IOException e) {
			throw new GfServiceException(e);
		} catch (ParseException e) {
			throw new GfServiceException(e);
		}
	}


	public GfWebServiceResultTranslate translate(String cat, String input, String from, String to) throws GfServiceException {
		Params p = new Params(Command.TRANSLATE);
		p.add(Param.CAT, cat);
		p.add(Param.INPUT, input);
		p.add(Param.FROM, from);
		p.add(Param.TO, to);
		String response = getResponseAsString(p.get());
		try {
			return new GfWebServiceResultTranslate(response);
		} catch (IOException e) {
			throw new GfServiceException(e);
		} catch (ParseException e) {
			throw new GfServiceException(e);
		}
	}

	public GfWebServiceResultRandom random(String cat, int limit) throws GfServiceException {
		Params p = new Params(Command.RANDOM);
		p.add(Param.CAT, cat);
		p.add(Param.LIMIT, limit);
		String response = getResponseAsString(p.get());
		try {
			return new GfWebServiceResultRandom(response);
		} catch (IOException e) {
			throw new GfServiceException(e);
		} catch (ParseException e) {
			throw new GfServiceException(e);
		}
	}

	public GfWebServiceResultComplete complete(String cat, String input, String from, int limit) throws GfServiceException {
		Params p = new Params(Command.COMPLETE);
		p.add(Param.CAT, cat);
		p.add(Param.INPUT, input);
		p.add(Param.FROM, from);
		p.add(Param.LIMIT, limit);
		String response = getResponseAsString(p.get());
		try {
			return new GfWebServiceResultComplete(response);
		} catch (IOException e) {
			throw new GfServiceException(e);
		} catch (ParseException e) {
			throw new GfServiceException(e);
		}
	}


	public GfServiceResultAbstrtree abstrtree(String tree) {
		throw new RuntimeException("NOT IMPLEMENTED");
	}


	public GfServiceResultParsetree parsetree(String tree, String from) {
		throw new RuntimeException("NOT IMPLEMENTED");
	}


	public GfWebServiceResultAlignment alignment(String tree) throws GfServiceException {
		if (tree == null) {
			throw new IllegalArgumentException("Tree MUST be given");
		}
		Params p = new Params(Command.ALIGNMENT);
		p.add(Param.TREE, tree);
		byte[] response = getResponseAsBytes(p.get());
		return new GfWebServiceResultAlignment(response);
	}


	private String getResponseAsString(List<NameValuePair> nvps) throws GfServiceException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest request = getHttpUriRequest(nvps);
		return getHttpEntityAsString(httpClient, request);
	}


	private byte[] getResponseAsBytes(List<NameValuePair> nvps) throws GfServiceException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest request = getHttpUriRequest(nvps);
		return getHttpEntityAsByteArray(httpClient, request);
	}


	/**
	 * We create an HTTP GET query from the given parameters. If it turns out to be
	 * too long (which we expect to happen very infrequently) then we fall back to creating
	 * HTTP POST.
	 * 
	 * @param nvps Collection of name-value pairs
	 * @return HTTP request (either GET or POST)
	 */
	private HttpUriRequest getHttpUriRequest(List<NameValuePair> nvps) {
		String getQuery = URI.create(mUri + mGrammar) + "?" + URLEncodedUtils.format(nvps, HTTP.UTF_8);
		if (getQuery.length() > MAX_HTTP_GET_LENGTH) {
			HttpPost httppost = new HttpPost(mUri);
			try {
				httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// TODO: Assuming that this cannot happen
			}
			return httppost;
		}
		return new HttpGet(getQuery);
	}


	private static String getHttpEntityAsString(DefaultHttpClient httpClient, HttpUriRequest httpRequest) throws GfServiceException {
		try {
			HttpEntity entity = getHttpEntity(httpClient, httpRequest);
			// Assuming that the webservice returns data in UTF8,
			// in case it does not declare the encoding.
			if (entity.getContentEncoding() == null) {
				return EntityUtils.toString(entity, HTTP.UTF_8);
			}
			return EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			throw new GfServiceException(e);
		} catch (IOException e) {
			throw new GfServiceException(e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}


	private static byte[] getHttpEntityAsByteArray(DefaultHttpClient httpClient, HttpUriRequest httpRequest) throws GfServiceException {
		try {
			return EntityUtils.toByteArray(getHttpEntity(httpClient, httpRequest));
		} catch (ClientProtocolException e) {
			throw new GfServiceException(e);
		} catch (IOException e) {
			throw new GfServiceException(e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}


	private static HttpEntity getHttpEntity(DefaultHttpClient httpclient, HttpUriRequest httpRequest)
			throws ClientProtocolException, IOException, GfServiceException {
		HttpResponse response = httpclient.execute(httpRequest);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new GfServiceException(response.getStatusLine().getReasonPhrase());
		}

		HttpEntity entity = response.getEntity();
		if (entity == null) {
			throw new GfServiceException(ERROR_MESSAGE_ENTITY_NULL);
		}

		return entity;
	}
}
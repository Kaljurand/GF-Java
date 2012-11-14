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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import ch.uzh.ifi.attempto.gfservice.GfServiceException;

public class HttpUtils {

	public static final int MAX_HTTP_GET_LENGTH = 1000;
	private static final String ERROR_MESSAGE_ENTITY_NULL = "Response entity is null";

	public static HttpPost getHttpPost(URI uri, List<NameValuePair> nvps) {
		HttpPost httppost = new HttpPost(uri);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO: Assuming that this cannot happen
		}
		return httppost;
	}


	public static String getHttpEntityAsString(DefaultHttpClient httpClient, HttpUriRequest httpRequest) throws GfServiceException {
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


	public static byte[] getHttpEntityAsByteArray(DefaultHttpClient httpClient, HttpUriRequest httpRequest) throws GfServiceException {
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


	/**
	 * <p>Executes the given request and returns the response entity.</p>
	 *
	 * <p>In case of an HTTP error we turn the <code>Reason-Phrase</code>
	 * of the <code>Status-Line</code> into an exception message.</p>
	 *
	 * <pre>
	 * Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
	 * </pre>
	 *
	 * TODO: the server currently does not set a meaningful Reason-Phrase
	 *
	 * @param httpclient HTTP client
	 * @param httpRequest HTTP request
	 * @return response entity
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws GfServiceException
	 */
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
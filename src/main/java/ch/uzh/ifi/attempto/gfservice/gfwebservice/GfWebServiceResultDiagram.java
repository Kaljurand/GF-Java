package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import org.apache.commons.codec.binary.Base64;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultDiagram;

/**
 * @author Kaarel Kaljurand
 *
 */
public abstract class GfWebServiceResultDiagram implements GfServiceResultDiagram {

	private final String mMime = "image/png";
	private final byte[] mImage;

	public GfWebServiceResultDiagram(byte[] image) {
		mImage = image;
	}


	/**
	 * This did not work:
	 * Base64.encodeBase64URLSafeString(mImage)
	 */
	public String getDataUri() {
		return "data:" + mMime + ";base64," + Base64.encodeBase64String(mImage);
	}
}
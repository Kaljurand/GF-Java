package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import org.apache.commons.codec.binary.Base64;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultAlignment;

/**
 * @author Kaarel Kaljurand
 *
 */
public class GfWebServiceResultAlignment implements GfServiceResultAlignment {

	private final String mMime = "image/png";
	private final byte[] mImage;

	public GfWebServiceResultAlignment(byte[] image) {
		mImage = image;
	}


	/**
	 * This did not work:
	 * Base64.encodeBase64URLSafeString(mImage)
	 */
	public String getAlignmentAsDataUri() {
		return "data:" + mMime + ";base64," + Base64.encodeBase64String(mImage);
	}
}
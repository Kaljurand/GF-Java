package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import ch.uzh.ifi.attempto.gfservice.DiagramFormat;
import org.apache.commons.codec.binary.Base64;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultDiagram;

/**
 * @author Kaarel Kaljurand
 */
public abstract class GfWebServiceResultDiagram implements GfServiceResultDiagram {

	private final byte[] mImage;
	private final DiagramFormat mFormat;

	public GfWebServiceResultDiagram(byte[] image, DiagramFormat format) {
		mImage = image;
		mFormat = format;
	}


	/**
	 * TODO: This did not work:
	 * Base64.encodeBase64URLSafeString(mImage)
	 */
	public String getDataUri() {
		return "data:" + mFormat.getMime() + ";base64," + Base64.encodeBase64String(mImage);
	}
}
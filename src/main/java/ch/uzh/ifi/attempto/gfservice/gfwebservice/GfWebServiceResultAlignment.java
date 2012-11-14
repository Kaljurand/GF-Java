package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultAlignment;

/**
 * @author Kaarel Kaljurand
 *
 */
public class GfWebServiceResultAlignment extends GfWebServiceResultDiagram implements GfServiceResultAlignment {

	public GfWebServiceResultAlignment(byte[] image) {
		super(image);
	}
}
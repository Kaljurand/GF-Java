package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import ch.uzh.ifi.attempto.gfservice.DiagramFormat;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultAlignment;

/**
 * @author Kaarel Kaljurand
 *
 */
public class GfWebServiceResultAlignment extends GfWebServiceResultDiagram implements GfServiceResultAlignment {

	public GfWebServiceResultAlignment(byte[] image, DiagramFormat format) {
		super(image, format);
	}

}
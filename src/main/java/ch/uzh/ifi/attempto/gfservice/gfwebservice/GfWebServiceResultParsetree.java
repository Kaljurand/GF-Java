package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import ch.uzh.ifi.attempto.gfservice.DiagramFormat;
import ch.uzh.ifi.attempto.gfservice.GfServiceResultParsetree;

/**
 * @author Kaarel Kaljurand
 *
 */
public class GfWebServiceResultParsetree extends GfWebServiceResultDiagram implements GfServiceResultParsetree {

	public GfWebServiceResultParsetree(byte[] image, DiagramFormat format) {
		super(image, format);
	}
}
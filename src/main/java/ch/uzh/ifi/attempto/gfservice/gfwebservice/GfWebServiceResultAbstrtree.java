package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import ch.uzh.ifi.attempto.gfservice.GfServiceResultAbstrtree;

/**
 * @author Kaarel Kaljurand
 *
 */
public class GfWebServiceResultAbstrtree extends GfWebServiceResultDiagram implements GfServiceResultAbstrtree {

	public GfWebServiceResultAbstrtree(byte[] image) {
		super(image);
	}
}
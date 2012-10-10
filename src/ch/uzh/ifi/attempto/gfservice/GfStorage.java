package ch.uzh.ifi.attempto.gfservice;

/**
 * <p>Java front-end to the REST-based GF service storage API described in
 * <a href="http://cloud.grammaticalframework.org/gf-cloud-api.html">GF Cloud Service API (preliminary)</a>.</p>
 *
 * @author Kaarel Kaljurand
 */
public interface GfStorage {

	/**
	 * <p>Creates a new grammar directory on the server. Returns the name of the directory.</p>
	 */
	String create() throws GfServiceException;

}
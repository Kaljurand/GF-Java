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


	/**
	 * <p>Upload grammar files (GF modules) and compile them into a PGF file.</p>
	 *
	 * TODO: check the response code (i.e. it is supposed to be 204 for successful uploads)
	 *
	 * @param dirName name of the directory where the files are stored
	 * @param modules GF modules (name + content)
	 * @return GfStorageResult
	 */
	GfStorageResult make(String dirName, GfModule... modules) throws GfServiceException;

}
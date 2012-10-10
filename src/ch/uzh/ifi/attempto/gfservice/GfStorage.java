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


	String upload(String dirName, GfModule... modules) throws GfServiceException;


	/**
	 * <p>Upload grammar files and compile them into a PGF file. The response code is 204 if the upload was successful.</p>
	 *
	 * @param dirName name of the directory where the files are stored
	 * @param modules GF modules (name + content)
	 * @return TODO
	 */
	String make(String dirName, GfModule... modules) throws GfServiceException;

}
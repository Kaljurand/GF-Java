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
	 * <p>Checks the given GF module for syntax errors. If there are no syntax errors
	 * then returns the GF source code as a JSON term. In case of errors a simple error
	 * term is returned.
	 * Note that a lack of syntax errors does not mean that the module can be integrated
	 * into a PGF. This depends on the (presence) of other modules.</p>
	 *
	 * TODO: better return the error information (which is currently just a location?)
	 * as an exception, e.g. GfParseException
	 *
	 * @param module GF module (name + content)
	 * @return GfParseResult which is a JSON term for the module or a JSON error term
	 * @throws GfServiceException
	 */
	GfParseResult parse(GfModule module) throws GfServiceException;


	/**
	 * <p>Uploads the given grammar files (GF modules) into the given directory
	 * and compiles them into a PGF file.</p>
	 *
	 * TODO: check the response code (i.e. it is supposed to be 204 for successful uploads)
	 *
	 * @param dirName name of the directory where the files are stored
	 * @param modules GF modules (name + content)
	 * @return GfStorageResult
	 */
	GfStorageResult make(String dirName, GfModule... modules) throws GfServiceException;

}
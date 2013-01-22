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
	 * <p>Updates the given GF module in the given directory and compiles it into a new
	 * PGF together with the previously updated modules with the given names.</p>
	 *
	 * @param dirName name of the directory where the files are stored
	 * @param module GF module (name + content)
	 * @param moduleNames names of GF modules which are expected to exist in the directory
	 * @return GfStorageResult
	 */
	GfStorageResult update(String dirName, GfModule module, Iterable<String> moduleNames) throws GfServiceException;


	/**
	 * <p>Updates the PGF based on the previously uploaded modules with the given names.</p>
	 *
	 * @param dirName name of the directory where the files are stored
	 * @param moduleNames names of GF modules which are expected to exist in the directory
	 * @return GfStorageResult
	 */
	GfStorageResult update(String dirName, Iterable<String> moduleNames) throws GfServiceException;


	/**
	 * <p>Uploads the given GF modules into the given directory
	 * and compiles them into a PGF file.</p>
	 *
	 * @param dirName name of the directory where the files are stored
	 * @param modules GF modules (name + content)
	 * @return GfStorageResult
	 */
	GfStorageResult update(String dirName, GfModule... modules) throws GfServiceException;


	/**
	 * <p>Updates the given GF modules in the given directory and compiles them into a new
	 * PGF together with the previously updated modules with the given names.
	 * The default start category is overridden by the given category (unless it is <code>null</code>).
	 * Unlike the other <code>update</code>-methods, this method can produce an "optimized" PGF,
	 * where everything that is not reachable from the start category is removed, often resulting
	 * in a smaller and faster grammar.</p>
	 *
	 * @param dirName name of the directory where the files are stored
	 * @param startCat PGF start category to override the default start category
	 * @param optimize iff <code>true</code>, then optimize the PGF
	 * @param moduleNames names of GF modules which are expected to exist in the directory
	 * @param modules GF modules (name + content)
	 * @return GfStorageResult
	 */
	GfStorageResult update(String dirName, String startCat, boolean optimize,
			Iterable<String> moduleNames, GfModule... modules) throws GfServiceException;


	/**
	 * <p>Uploads files to be stored in the cloud. Unsuccessful attempts (i.e. response
	 * code != 204) cause an exception to be thrown.</p>
	 *
	 * @param dirName name of the directory where the files are stored
	 * @param modules GF modules (name + content)
	 * @throws GfServiceException
	 */
	void upload(String dirName, GfModule... modules) throws GfServiceException;


	/**
	 * <p>Returns a list of names of the files in the given directory
	 * matching the given filename extension.</p>
	 *
	 * @param dirName name of the directory where the files are stored
	 * @param extension file extension (with the dot), e.g. ".gf"
	 * @return result containing the list of matching filenames
	 * @throws GfServiceException
	 */
	GfStorageResultLs ls(String dirName, String extension) throws GfServiceException;


	/**
	 * <p>Deletes a file with the given name in the given directory.</p>
	 *
	 * @param dirName name of the directory where the files are stored
	 * @param path name of the file to be deletes
	 * @throws GfServiceException
	 */
	void rm(String dirName, String path) throws GfServiceException;


	/**
	 * <p>Downloads a file with the given name in the given directory.</p>
	 *
	 * @param dirName name of the directory where the files are stored
	 * @param path name of the file to be downloaded
	 * @throws GfServiceException
	 */
	void download(String dirName, String path) throws GfServiceException;

}
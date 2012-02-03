package ch.uzh.ifi.attempto.gfservice;

/**
 * <p>Java front-end to a GF service, modeled after the
 * <a href="http://code.google.com/p/grammatical-framework/wiki/GFWebServiceAPI">GF webservice</a>.</p>
 * 
 * <p>This interface can have multiple implementations, e.g. in addition to the wrapper
 * for the GF Webservice, we could also have a wrapper for JPGF.</p>
 * 
 * <p>All the methods expect that the name of the grammar is already specified (in the constructor).</p>
 *
 * @author Kaarel Kaljurand
 *
 */
public interface GfService {

	/**
	 * <p>Provides some information about the given grammar.</p>
	 *
	 * @return GfServiceResultGrammar
	 * @throws GfServiceException
	 */
	GfServiceResultGrammar grammar() throws GfServiceException;


	/**
	 * <p>Parses the given string into a list of abstract syntax trees.</p>
	 *
	 * @param cat
	 * @param input
	 * @param from
	 * @return GfServiceResultParse
	 * @throws GfServiceException
	 */
	GfServiceResultParse parse(String cat, String input, String from) throws GfServiceException;


	/**
	 * <p>Linearizes the given abstract syntax tree into strings in the given language(s).</p>
	 *
	 * @param tree
	 * @param to
	 * @return GfServiceResultLinearize
	 * @throws GfServiceException
	 */
	GfServiceResultLinearize linearize(@NotNull String tree, String to) throws GfServiceException;


	/**
	 * <p>Translates the given string from the given source language into the given target language.</p>
	 *
	 * <p>The translation is a two step process.
	 * First the given string is parsed with the given source language and then
	 * the resulting trees are linearized into the given target language.
	 * For that reason the input and the output for this command is the union
	 * of the input/output of the commands for parsing and the one for linearization.</p>
	 *
	 * @param cat
	 * @param input
	 * @param from
	 * @param to
	 * @return GfServiceResultTranslate
	 * @throws GfServiceException
	 */
	GfServiceResultTranslate translate(String cat, String input, String from, String to) throws GfServiceException;


	/**
	 * <p>Generates the given number of abstract trees randomly.
	 * The generated trees are not necessarily different.</p>
	 *
	 * @param cat the start category for the parser
	 * @param limit number of trees to be generated
	 * @return GfServiceResultRandom
	 * @throws GfServiceException
	 */
	GfServiceResultRandom random(String cat, int limit) throws GfServiceException;


	/**
	 * <p>Completes the last part of the given string.</p>
	 *
	 * @param cat the start category for the parser
	 * @param input the incomplete string the last token of which is to be completed
	 * @param from language to use for parsing
	 * @param limit maximal number of trees generated
	 * @return GfServiceResultComplete
	 * @throws GfServiceException
	 */
	GfServiceResultComplete complete(String cat, String input, String from, int limit) throws GfServiceException;


	/**
	 *
	 * @param tree
	 * @return GfServiceResultAbstrtree
	 * @throws GfServiceException
	 */
	GfServiceResultAbstrtree abstrtree(@NotNull String tree) throws GfServiceException;


	/**
	 *
	 * @param tree
	 * @param from
	 * @return GfServiceResultParsetree
	 * @throws GfServiceException
	 */
	GfServiceResultParsetree parsetree(@NotNull String tree, @NotNull String from) throws GfServiceException;


	/**
	 *
	 * @param tree
	 * @return GfServiceResultAlignment
	 * @throws GfServiceException
	 */
	GfServiceResultAlignment alignment(@NotNull String tree) throws GfServiceException;

}
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
 * <p>Setting a parameter to <code>null</code> results in the default value being used.</p>
 *
 * @author Kaarel Kaljurand
 */
public interface GfService {

	/**
	 * <p>Provides some information about the grammar.</p>
	 *
	 * @return GfServiceResultGrammar
	 * @throws GfServiceException
	 */
	GfServiceResultGrammar grammar() throws GfServiceException;


	/**
	 * <p>Parses the given string into a list of abstract syntax trees.</p>
	 *
	 * @param cat start category for the parser (defaults to: the default start category)
	 * @param input string to be parsed (defaults to: empty string)
	 * @param from language of the string (defaults to: all languages will be tried by the parser)
	 * @param limit max number of trees returned (defaults to: no limit)
	 * @return GfServiceResultParse
	 * @throws GfServiceException
	 */
	GfServiceResultParse parse(String cat, String input, String from, Integer limit) throws GfServiceException;
	GfServiceResultParse parse(String cat, String input, String from) throws GfServiceException;


	/**
	 * <p>Linearizes the given abstract syntax tree into strings in the given language(s).</p>
	 *
	 * @param tree abstract tree to be linearized
	 * @param to linearization language (defaults to: linearizes into all the languages of the grammar)
	 * @return GfServiceResultLinearize
	 * @throws GfServiceException
	 */
	GfServiceResultLinearize linearize(@NotNull String tree, String to) throws GfServiceException;


	/**
	 * <p>Linearizes the given abstract syntax tree into strings in the given language(s).</p>
	 */
	GfServiceResultLinearizeAll linearizeAll(@NotNull String tree, String to) throws GfServiceException;


	/**
	 * <p>Translates the given string from the given source language into the given target language.</p>
	 *
	 * <p>The translation is a two step process.
	 * First the given string is parsed with the given source language and then
	 * the resulting trees are linearized into the given target language.
	 * For that reason the input and the output for this command is the union
	 * of the input/output of the commands for parsing and the one for linearization.</p>
	 *
	 * @param cat start category for the parser (defaults to: the default start category)
	 * @param input string to be parsed (defaults to: empty string)
	 * @param from language of the string (defaults to: all languages will be tried by the parser)
	 * @param to linearization language (defaults to: linearizes into all the languages of the grammar)
	 * @param limit max number of trees used (defaults to: no limit)
	 * @return GfServiceResultTranslate
	 * @throws GfServiceException
	 */
	GfServiceResultTranslate translate(String cat, String input, String from, String to, Integer limit) throws GfServiceException;
	GfServiceResultTranslate translate(String cat, String input, String from, String to) throws GfServiceException;


	/**
	 * <p>Generates the given number of abstract trees randomly.
	 * The generated trees are not necessarily different.</p>
	 *
	 * @param cat start category for the parser (defaults to: the default start category)
	 * @param limit number of trees to be generated (defaults to: 1)
	 * @return GfServiceResultRandom
	 * @throws GfServiceException
	 */
	GfServiceResultRandom random(String cat, Integer limit) throws GfServiceException;


	/**
	 * <p>Completes the last part of the given string.</p>
	 *
	 * @param cat start category for the parser (defaults to: the default start category)
	 * @param input incomplete string the last token of which is to be completed
	 * @param from language to use for parsing
	 * @param limit maximal number of trees generated (defaults to: all)
	 * @return GfServiceResultComplete
	 * @throws GfServiceException
	 */
	GfServiceResultComplete complete(String cat, String input, String from, Integer limit) throws GfServiceException;


	/**
	 * <p>Searches for the ambiguous completions of the given string.</p>
	 * <p>Performs {@code length} number of steps before stopping.
	 * If {@code length == null} then searches until the ambiguity or the end,
	 * whichever comes first, but neither of which might come, so it is safer not to set {@code length = null}.</p>
	 * <p>{@code limit} should be larger than 1 or {@code null}.</p>
	 *
	 * <p>For example, in a language</p>
	 *
	 * <pre>
	 *     go (one | two | three | four | five) meters (forward | back)
	 * </pre>
	 *
	 * <p>the following ambiguous completions occur:</p>
	 *
	 * <pre>
	 *     "g" -> {"go one", "go two", ...}
	 *     "go o" -> {"one meters forward", "one meters back"}
	 *     "go one meters" -> {"forward", "back"}
	 *     "go one meters b" -> {"back"}
	 * </pre>
	 *
	 * @param cat start category for the parser ({@code null} = the default start category)
	 * @param input incomplete string to be completed ({@code null = ""})
	 * @param from language to use for parsing
	 * @param limit maximal number of completions generated ({@code null} = all)
	 * @param length maximal number of completion steps before ambiguity is reached ({@code null} = unlimited)
	 * @return GfServiceResultComplete
	 * @throws GfServiceException
	 */
	GfServiceResultComplete complete(String cat, String input, String from, Integer limit, Integer length) throws GfServiceException;


	/**
	 * <p>Returns the abstract tree diagram.</p>
	 *
	 * @param tree abstract syntax tree to be rendered
	 * @param format format of the diagram (png, svg, gv, gif)
	 * @return GfServiceResultAbstrtree
	 * @throws GfServiceException
	 */
	GfServiceResultAbstrtree abstrtree(@NotNull String tree, @NotNull DiagramFormat format) throws GfServiceException;
	GfServiceResultAbstrtree abstrtree(@NotNull String tree) throws GfServiceException;


	/**
	 * <p>Returns the parse tree diagram.</p>
	 *
	 * @param tree abstract syntax tree to be rendered
	 * @param from language to be used in the rendering
	 * @param format format of the diagram (png, svg, gv, gif)
	 * @return GfServiceResultParsetree
	 * @throws GfServiceException
	 */
	GfServiceResultParsetree parsetree(@NotNull String tree, @NotNull String from, @NotNull DiagramFormat format) throws GfServiceException;
	GfServiceResultParsetree parsetree(@NotNull String tree, @NotNull String from) throws GfServiceException;


	/**
	 * <p>Returns the word alignment diagram.</p>
	 *
	 * @param tree abstract syntax tree to be rendered
	 * @param format format of the diagram (png, svg, gv, gif)
	 * @return GfServiceResultAlignment
	 * @throws GfServiceException
	 */
	GfServiceResultAlignment alignment(@NotNull String tree, @NotNull DiagramFormat format) throws GfServiceException;
	GfServiceResultAlignment alignment(@NotNull String tree) throws GfServiceException;


	/**
	 * <p>Returns information (producers and consumers) about the given category or function.</p>
	 *
	 * @param id search query (name of a category or function)
	 * @return GfServiceResultBrowse
	 * @throws GfServiceException
	 */
	GfServiceResultBrowse browse(@NotNull String id) throws GfServiceException;


	/**
	 * <p>Generates prefixes of parseable strings obtained
	 * from growing the input string one token at a time, exhaustively covering all
	 * token lengths.</p>
	 *
	 * @param cat start category for the parser (<code>null</code> = default start category)
	 * @param input incomplete string the rest of which will be generated
	 * @param from language to use for completion
	 * @param limit maximal number of completions to generate at each step (<code>null</code> = all)
	 * @return iterator over prefixes
	 */
	Iterable<String> generatePrefix(String cat, String input, String from, Integer limit);


	/**
	 * <p>Generates prefixes of parseable strings obtained
	 * from growing the input one token at a time, exhaustively covering all
	 * token lengths.</p>
	 *
	 * @param from language to use for completion
	 * @return iterator over prefixes
	 */
	Iterable<String> generatePrefix(String from);

}
package ch.uzh.ifi.attempto.gfservice;

public enum Command {
	GRAMMAR("grammar"),
	PARSE("parse"),
	LINEARIZE("linearize"),
	LINEARIZE_ALL("linearizeAll"),
	TRANSLATE("translate"),
	RANDOM("random"),
	COMPLETE("complete"),
	ABSTRTREE("abstrtree"),
	PARSETREE("parsetree"),
	ALIGNMENT("alignment"),
	BROWSE("browse");

	private final String mCommand;

	Command(String command) {
		mCommand = command;
	}

	public String getCommand() {
		return mCommand;
	}
}
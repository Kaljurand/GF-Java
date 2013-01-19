package ch.uzh.ifi.attempto.gfservice;

public class GfTreeParseException extends Exception {

	private final int mPos;
	public GfTreeParseException(int pos) {
		super("syntax error: position = " + pos);
		mPos = pos;
	}

	public int getPosition() {
		return mPos;
	}

}
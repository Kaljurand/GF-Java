package ch.uzh.ifi.attempto.gfservice;

public enum DiagramFormat {

	SVG("image/svg+xml"),
	GIF("image/gif"),
	GV("text/vnd.graphviz"), // TODO: not sure
	PNG("image/png");

	public static DiagramFormat DEFAULT = SVG;

	private final String mMime;

	DiagramFormat(String mime) {
		mMime = mime;
	}

	public String getMime() {
		return mMime;
	}
}
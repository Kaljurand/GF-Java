package ch.uzh.ifi.attempto.gfservice;

public class GfModule {

	public static final String EXT = ".gf";

	private final String mName;
	private final String mContent;

	public GfModule(String name, String content) {
		mName = name;
		mContent = content;
	}


	public String getName() {
		return mName;
	}


	public String getFilename() {
		return mName + EXT;
	}


	public String getContent() {
		return mContent;
	}

}
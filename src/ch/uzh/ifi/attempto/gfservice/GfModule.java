package ch.uzh.ifi.attempto.gfservice;

public class GfModule {

	private final String mName;
	private final String mContent;

	public GfModule(String name, String content) {
		mName = name;
		mContent = content;
	}


	public String getName() {
		return mName;
	}


	public String getContent() {
		return mContent;
	}

}
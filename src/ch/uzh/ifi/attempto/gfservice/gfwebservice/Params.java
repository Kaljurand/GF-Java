package ch.uzh.ifi.attempto.gfservice.gfwebservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import ch.uzh.ifi.attempto.gfservice.Command;
import ch.uzh.ifi.attempto.gfservice.Param;


public class Params {

	private final List<NameValuePair> pairs = new ArrayList<NameValuePair>();

	public Params(Command command) {
		add(Param.COMMAND, command.toString().toLowerCase());
	}

	public void add(Param key, int value) {
		if (value < 0) {
			add(key, "-1");
		} else {
			add(key, Integer.toString(value));
		}
	}

	public void add(Param key, String value) {
		if (value != null) {
			String keyAsStr = key.toString().toLowerCase();
			pairs.add(new BasicNameValuePair(keyAsStr, value));
		}
	}

	public List<NameValuePair> get() {
		return pairs;
	}
}
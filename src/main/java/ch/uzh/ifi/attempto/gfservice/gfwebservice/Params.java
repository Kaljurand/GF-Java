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


	/**
	 * <p>Adds a key-value pair.</p>
	 * <p>Passing <code>null</code> as value will not add a new pair.</p>
	 *
	 * @param key webservice query key
	 * @param value webservice query value
	 */
	public void add(Param key, Object value) {
		if (value != null) {
			String keyAsStr = key.toString().toLowerCase();
			pairs.add(new BasicNameValuePair(keyAsStr, value.toString()));
		}
	}


	/**
	 * @return list of pairs
	 */
	public List<NameValuePair> get() {
		return pairs;
	}
}
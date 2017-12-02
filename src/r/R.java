package r;

import java.util.HashMap;

public class R {
	private static R instance = null;
	private HashMap<String, Object> map;

	private R() {
		map = new HashMap<String, Object>();
	}

	public static R getInstance() {
		if (instance == null)
			instance = new R();
		return instance;
	}

	public void registObject(String str, Object obj) {
		map.put(str, obj);
	}

	public Object getObject(String str) {
		return map.get(str);
	}
}
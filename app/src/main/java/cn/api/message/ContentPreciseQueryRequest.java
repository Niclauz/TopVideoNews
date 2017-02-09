package cn.api.message;

import java.util.HashMap;
import java.util.List;

public class ContentPreciseQueryRequest {
	private List<HashMap<String, Integer>> msg;

	public List<HashMap<String, Integer>> getMsg() {
		return msg;
	}

	public void setMsg(List<HashMap<String, Integer>> msg) {
		this.msg = msg;
	}
	
}

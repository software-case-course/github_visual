package com.AriesT.Entity;

import java.util.Map;


public class ResultEntity<T> {

	private int status;
	private String msg;
	private T data;
	private String error;

	{
		status = 0000;
		msg = "fail";
		error = null;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ResultEntity [status=" + status + ", msg=" + msg + ", data=" + data + ", error=" + error + "]";
	}

	public static ResultEntity<Map> SetResultForController(Map map) {//来自大佬的思路
		ResultEntity<Map> result = new ResultEntity<Map>();
		if (map != null && map.get("data") != null) {
			result.setStatus(200);
			result.setMsg("OK");
			result.setData(map);
		} else {
			result.setStatus(201);
			result.setMsg("fail");
			result.setData(map);
		}
		return result;
	}
}

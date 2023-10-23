package com.danaga.exception;

public class ExistedMemberException extends Exception{
	private Object data;
	public ExistedMemberException(String msg) {
		super(msg);
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}

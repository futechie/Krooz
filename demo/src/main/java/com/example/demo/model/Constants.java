package com.example.demo.model;

public class Constants {

	private String message;
	private int status;
	public Constants(int statuscode, String message) {
		this.status=statuscode;
		this.message=message;
		
	}
	public String getMessage() {
		return message;
	}
	public int getStatus() {
		return status;
	}

	
}

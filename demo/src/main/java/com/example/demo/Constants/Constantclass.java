package com.example.demo.Constants;

import com.example.demo.model.Constants;

public class Constantclass {

	public final String statusSent= "Sent";
	public Constants getResultJSON(int statuscode,String message){
		
		return new Constants(statuscode,message.toLowerCase());
		
	}
}

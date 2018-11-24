package com.example.demo.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import com.example.demo.util.ValidationUtils;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class testclass {
	public static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
	public static void main(String[] args) {
		
		
		
		
//		
//			JsonObject json1=new  JsonObject();
//			JsonArray jsonarr = new JsonArray();
//			json1.add("json", jsonarr);
//			System.out.println(json1);
		
		
		
		
		System.out.println(new testclass().getClass().getClassLoader().getResource("/demo/src/main/resources/static/schemaJSON.txt"));
		String json="{\r\n" + 
				"    \"id\": 1,\"name\": \"A green door\",\r\n" + 
				"    \"price\": 12.50,\r\n" + 
				"    \"tags: [\"home\", \"green\"]\r\n" + 
				"}";
		new testclass().sample(json);
	}
	
	void sample(String json) {
		String url = null;
		try {
			url = URLDecoder.decode( this.getClass().getClassLoader().getResource("static/schemaJSON.txt").getFile().toString(), "UTF-8" );
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String schemaFile="";
		try {
			String content = new String(Files.readAllBytes(Paths.get(url.substring(1))));
		
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String prettyJsonString="";
		try {
			if (ValidationUtils.isJsonValid(schemaFile, json)){
			    	System.out.println("Valid!");
			    	 System.out.println(json);
					 Gson gson = new GsonBuilder().setPrettyPrinting().create();
					 JsonParser jp = new JsonParser();
					 JsonElement je = jp.parse(json);
					 prettyJsonString = gson.toJson(je);
			    }else{
			    	System.out.println("NOT valid!");
			    }
		} catch (ProcessingException e) {
			e.printStackTrace();
			prettyJsonString=e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			prettyJsonString=e.getMessage();
		}
	}
	
}


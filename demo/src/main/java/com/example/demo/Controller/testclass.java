package com.example.demo.Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class testclass {
	public static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
	public static void main(String[] args) {
		
		
		
			
			
			JsonParser parser = new JsonParser();
			 Object obj = null;
			try {
				obj = parser.parse(new FileReader("D:\\Studies\\Eclipse\\oxygen workspace\\demo\\src\\main\\resources\\static\\JSON.txt"));
			} catch (JsonIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        JsonObject jsonObject =  (JsonObject) obj;
	        System.out.println(jsonObject);
	       JsonArray arr= (JsonArray) jsonObject.get("json_id");
	        
	     
	       JsonArray jsonArray =  new JsonArray();
	       for (JsonElement jsonElement : arr) {    	   jsonArray.add(jsonElement);	}
	       JsonObject JO=new  JsonObject();
			JO.addProperty("Message", "Hi, how are you");
			JO.addProperty("MessageType", "seperate");
			JO.addProperty("flag", "Sent");
			JO.addProperty("createdTime", "10:03");
	       jsonArray.add(JO);
	       jsonObject.add("json_id", jsonArray);
	       
	       System.out.println(jsonObject);
	        /*
	        
			JsonObject json=new  JsonObject();
			JsonArray jsonarr = new JsonArray();
			JsonObject JO=new  JsonObject();
			JO.addProperty("Message", "Hi, how are you");
			JO.addProperty("MessageType", "seperate");
			JO.addProperty("flag", "Sent");
			JO.addProperty("createdTime", "10:00");
			
			jsonarr.add(JO);
			
			JO=new  JsonObject();
			JO.addProperty("Message", "Yes,Fine");
			JO.addProperty("MessageType", "seperate");
			JO.addProperty("flag", "Received");
			JO.addProperty("createdTime", "10:01");
			jsonarr.add(JO);
			
			json.add("json", jsonarr);
			System.out.println(json);*/
		
//		
//			JsonObject json1=new  JsonObject();
//			JsonArray jsonarr = new JsonArray();
//			json1.add("json", jsonarr);
//			System.out.println(json1);
	
	}
	

}


package com.example.demo.Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.UserService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


@CrossOrigin(origins="*")
@RestController(value="/webapi")
public class RestControler {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/send", method=RequestMethod.POST)
	@ResponseBody
	public String sendMsg(@RequestParam(value="message") String text,
			@RequestParam(value="S_id") int sender_id,
			@RequestParam(value="R_id") int receiver_id,
			@RequestParam(value="C_id") int chat_id,
			@RequestParam(value="Type_id") int msg_Type){
		userService.sendMsg(text,sender_id,receiver_id,chat_id,msg_Type);
		return "success";
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
	}
}

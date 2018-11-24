package com.example.demo.ServiceImpl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.Service.UserService;
import com.example.demo.model.MessageMaster;
import com.example.demo.model.User;
import com.example.demo.repo.MessageMasterRepository;
import com.example.demo.repo.UserRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

@Service
public class ServiceImplementation implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MessageMasterRepository messageMasterRepo;

	@Override
	public void sendMsg(String text, int sender_id, int receiver_id,int chat_id) {
		
		MessageMaster mm= new MessageMaster();
		mm.setSenderId(sender_id);
		mm.setReceiverId(receiver_id);
		String conversationId="";
		
		MessageMaster result= new MessageMaster();
		if(chat_id==0) {
			conversationId=String.valueOf(sender_id)+String.valueOf(receiver_id)+System.currentTimeMillis();	
		}else {
			conversationId=messageMasterRepo.getOne(chat_id).getJsonId();
		}
		
		
		
			
			
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
	         JsonArray arr=new JsonArray();
	         
	          arr= (JsonArray) jsonObject.get(conversationId);
	         
	         
	         
	         JsonArray jsonArray =  new JsonArray();
	         for (JsonElement jsonElement : arr) {    	   jsonArray.add(jsonElement);	}
	         JsonObject JO=new  JsonObject();
		  		 JO.addProperty("Message", text);
		  		 JO.addProperty("MessageType", "seperate");
		  		 JO.addProperty("flag", "From");
		  		 JO.addProperty("createdTime", System.currentTimeMillis());
	         jsonArray.add(JO);
	         jsonObject.add("json_id", jsonArray);
		
         
		
		
		mm.setJsonId(conversationId);
		userRepository.save(null);
	}
	

}

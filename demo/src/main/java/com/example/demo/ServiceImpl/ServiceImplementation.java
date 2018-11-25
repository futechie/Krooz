package com.example.demo.ServiceImpl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.Service.UserService;
import com.example.demo.model.GroupMaster;
import com.example.demo.model.MessageMaster;
import com.example.demo.model.User;
import com.example.demo.repo.GroupMasterRepository;
import com.example.demo.repo.MessageMasterRepository;
import com.example.demo.repo.UserRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

@Service
public class ServiceImplementation implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GroupMasterRepository groupmasterRepository;

	@Autowired
	MessageMasterRepository messageMasterRepo;

	
	@Override
	public List<User> getParticipants() {
		
		return  userRepository.findAll();
	}

	
	@Override
	public void sendMsg(String text, int sender_id, int receiver_id, int chat_id, int msg_Type) {

		MessageMaster mm = new MessageMaster();
		mm.setSenderId(sender_id);
		mm.setReceiverId(receiver_id);
		String conversationId = "";

		MessageMaster result = new MessageMaster();
		if (chat_id == 0) {
			conversationId = String.valueOf(sender_id) + String.valueOf(receiver_id) + System.currentTimeMillis();
		} else {
			conversationId = messageMasterRepo.getOne(chat_id).getJsonId();
		}

		JsonObject jsonObject = (JsonObject) readJSON();
		JsonArray arr = new JsonArray();

		arr = (JsonArray) jsonObject.get(conversationId);

		JsonArray jsonArray = new JsonArray();
		for (JsonElement jsonElement : arr) {
			jsonArray.add(jsonElement);
		}
		JsonObject JO = new JsonObject();
		JO.addProperty("Message", text);
		JO.addProperty("MessageType", msg_Type == 1 ? "G" : "S");
		JO.addProperty("flag", "From");
		JO.addProperty("createdTime", System.currentTimeMillis());
		jsonArray.add(JO);
		jsonObject.add(conversationId, jsonArray);

		writeJSON(jsonObject.toString());

		mm.setJsonId(conversationId);
		messageMasterRepo.save(mm);
	}

	
	
	
	
	private Object readJSON() {

		JsonParser parser = new JsonParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(
					"D:\\Studies\\Eclipse\\oxygen workspace\\demo\\src\\main\\resources\\static\\JSON.txt"));
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
		return obj;

	}

	private void writeJSON(String fileContent) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("c://samplefile2.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileWriter.write(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@Override
	public String createGroup(GroupMaster grpmast) {
		StringBuilder st = new StringBuilder();
		List<User> usr=new ArrayList();
		for (User user : grpmast.participants) {
			st.append(user.getUid());
			user =userRepository.findById(user.getUid()).get();
			usr.add(user);
			
			
		}
		st.append(System.currentTimeMillis());
		if(grpmast.getGrpId()==0) {			
			grpmast.setGroupName("grp_"+st.toString());
		}else {
			grpmast=groupmasterRepository.findById(grpmast.getGrpId()).get();
			
		}
		grpmast.setParticipants(usr);
		
		groupmasterRepository.save(grpmast);
		return "success";
	}

	
}

package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.UserService;
import com.example.demo.model.GroupMaster;
import com.example.demo.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("webapi")
public class ChatController{
	
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

	@RequestMapping(value="/getParticipants", method=RequestMethod.GET)
	@ResponseBody
	public List<User> getParticipants() {
		
		return userService.getParticipants();
		
	}
	
	// input: {"grpId":0,"groupName":null,"participants":[{"uid":0},{"uid":0]}
	@RequestMapping(value="/createGroup", method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public String createGroup(@RequestBody GroupMaster grpmast) {
	
		return userService.createGroup(grpmast);
		
	}
	
	public static void main(String[] args) {
		
	
		// TODO Auto-generated method stub
		GroupMaster g= new GroupMaster();
		List<User> l=new ArrayList();
		l.add(new User());
		l.add(new User());
		g.setParticipants(l);

		try {
			System.out.println(new ObjectMapper().writeValueAsString(g));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}

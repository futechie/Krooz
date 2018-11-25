package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.UserService;
import com.example.demo.model.GroupMaster;
import com.example.demo.model.MessagingVO;
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
	public String sendMsg(@RequestBody MessagingVO messageVo){
		
		return userService.sendMsg(messageVo);
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
	@RequestMapping(value="/deleteGroup", method=RequestMethod.DELETE)
	@ResponseBody
	public boolean deleteGroup(@RequestParam("groupId") String groupId) {
		MessagingVO objMessagingVO = new MessagingVO();
		objMessagingVO.setGroupChatId(groupId);
		return userService.deleteChat(objMessagingVO);
	}
public static void main(String[] args) {
	MessagingVO messageVo = new MessagingVO();
	try {
		System.out.println(new ObjectMapper().writeValueAsString(messageVo));
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
	
	
}

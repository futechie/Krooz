package com.example.demo.Service;

import java.util.List;

import com.example.demo.model.GroupMaster;
import com.example.demo.model.MessagingVO;
import com.example.demo.model.User;

public interface UserService {

	String sendMsg(MessagingVO messageVo);

	List<User> getParticipants();

	String createGroup(GroupMaster grpmast);
	
	public boolean deleteChat(MessagingVO messageVo);
}

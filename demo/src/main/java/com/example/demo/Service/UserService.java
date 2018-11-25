package com.example.demo.Service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;

public interface UserService {

	void sendMsg(String text, int sender_id, int receiver_id, int chat_id, int msg_Type);

	List<User> getParticipants();
	
}

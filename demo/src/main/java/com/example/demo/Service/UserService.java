package com.example.demo.Service;

import java.util.Map;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repo.UserRepository;

public interface UserService {

	void sendMsg(String text, int sender_id, int receiver_id, int chat_id);
	

	
}

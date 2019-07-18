package com.example.demo.Service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Constants;
import com.example.demo.model.FileUpload;
import com.example.demo.model.GroupMaster;
import com.example.demo.model.MessagingVO;
import com.example.demo.model.User;
import com.example.demo.model.UserDTO;
import com.example.demo.model.chatListDTO;
import com.google.gson.JsonObject;

public interface UserService {

	Constants sendMsg(MessagingVO messageVo,MultipartFile[] files);

	List<User> getParticipants();

	String createGroup(GroupMaster grpmast);
	
	Constants deleteChat(int chatId);

	List<chatListDTO> getChatList();

	Constants createUser(UserDTO user);

	String getConversation(int chatId);

	FileUpload downloadFile(String objectId);
}

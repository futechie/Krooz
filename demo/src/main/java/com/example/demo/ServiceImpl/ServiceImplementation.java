package com.example.demo.ServiceImpl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Constants.Constantclass;
import com.example.demo.Service.UserService;
import com.example.demo.model.Constants;
import com.example.demo.model.FileUpload;
import com.example.demo.model.GroupMaster;
import com.example.demo.model.MessageMaster;
import com.example.demo.model.MessagingVO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserDTO;
import com.example.demo.model.chatListDTO;
import com.example.demo.repo.FileUploadRepository;
import com.example.demo.repo.GroupMasterRepository;
import com.example.demo.repo.GroupUserRepository;
import com.example.demo.repo.MessageMasterRepository;
import com.example.demo.repo.RoleRepository;
import com.example.demo.repo.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	RoleRepository roleRepository;

	@Autowired
	GroupMasterRepository groupmasterRepository;

	@Autowired
	MessageMasterRepository messageMasterRepo;
	
	@Autowired
	FileUploadRepository fileUploadRepository;
	
	@Autowired
	GroupUserRepository groupUserRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	@Override
	public List<User> getParticipants() {
		return userRepository.findAll();
	}
	
	Constantclass result= new Constantclass();

	public Constants validator(MessagingVO messageVo) {
		if (messageVo.getMsg_Type() == 0) {

			if (messageVo.getReceiver_id() == 0 || messageVo.getSender_id() == 0) {
				return result.getResultJSON(301,"Sender/Receiver Id is missing");
			}
		} else if (messageVo.getMsg_Type() == 1) {
			if (messageVo.getChat_id() == 0) {
				return result.getResultJSON(301,"Group doesn't Exist");
			}
			if (messageVo.getReceiver_id() != 0) {
				return result.getResultJSON(301,"Additional ReceiverId Parameter");
			}
		} else {
			return result.getResultJSON(301,"MessageType is Invalid");
		}
		return result.getResultJSON(200, "valid");
	}

	@Override
	public Constants sendMsg(MessagingVO messageVo,MultipartFile[] files) {
		//String text, int sender_id, int receiver_id, int chat_id, int msg_Type
		/*
		 * if(true) { if (validator(messageVo)!="") { return validator(messageVo); } }
		 */
		//fileUploadRepository.setCollectionName("hello");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		User usrdet = userRepository.findByUsername(authentication.getName());
		messageVo.setSender_id(usrdet.getUid());
		
		
		MessageMaster mm = new MessageMaster();
		JsonArray jsonArray = new JsonArray();
		JsonParser parser = new JsonParser();
		JsonArray Result = new JsonArray();

		if (messageVo.getChat_id() != 0) {
			mm = messageMasterRepo.findOne(messageVo.getChat_id());
			if (mm.getGroupId() != null) { //whether it is a group / not (group)
				messageVo.setMsg_Type(1);
				messageVo.setReceiver_id(0); 
				GroupMaster group = groupmasterRepository.findByName(mm.getGroupId());
				
				int flag=0;
				for (User user : group.getParticipants()) {
					if(user.getUid()==mm.getSenderId()) {
						flag=1;
					}
				}
				if(flag==0) {
					result.getResultJSON(301, "User Does't Exist");
				}
				
			}else {
				messageVo.setMsg_Type(0);
				if (mm.getSenderId() == 0 || mm.getReceiverId() == 0) { //whether it is a seperate / not (seperate)
					return result.getResultJSON(301, "Sender/Receiver Id is missing");
				}
			}
			
		} else {
			messageVo.setMsg_Type(0);
			 if(validator(messageVo).getStatus()!=200) {
				return validator(messageVo); 
			 }
		}

		mm.setSenderId(messageVo.getSender_id());
		mm.setReceiverId(messageVo.getReceiver_id());

		String conversationId = "",Chats="";
		if (messageVo.getChat_id() == 0) {
			conversationId = "sep_" + String.valueOf(messageVo.getSender_id())
					+ String.valueOf(messageVo.getReceiver_id()) + System.currentTimeMillis();
		} else {
			conversationId = messageVo.getMsg_Type() == 1 ? mm.getGroupId() : mm.getSeperateid();
			Chats="chats";
			if (conversationId == null || conversationId.equals("null"))
				return result.getResultJSON(301,"MessageType is Invalid");
		}

		JsonObject jsonObject = (JsonObject) readJSON();
		String json=jsonObject.toString();
		if (jsonObject.has(conversationId)) {
			Result=(JsonArray) parser.parse(json).getAsJsonObject().getAsJsonObject(conversationId).get(Chats);
			for (JsonElement jsonElement : Result) {
				jsonArray.add(jsonElement);
			}
		}
		JsonObject JO = new JsonObject();
		JO.addProperty("Message", messageVo.getText());
		JO.addProperty("MessageType", messageVo.getMsg_Type() == 1 ? "G" : "S");// 2== sep / 1== grp
		JO.addProperty("SentBy", messageVo.getSender_id());
		//need to implement count
		/*if (messageVo.getChat_id() == 0) {
		}else {}*/
		JO.addProperty("files", UploadFile(files));
		JO.addProperty("createdTime", System.currentTimeMillis());
		jsonArray.add(JO);
		//added to Chats 
		JsonObject JO1 = new JsonObject();
		JO1.add("chats", jsonArray);
		JO1.add("Status", parser.parse(result.statusSent));
		jsonObject.add(conversationId, JO1);
		System.out.println("Final JSON===== "+jsonObject);
		writeJSON(jsonObject.toString());
		if (messageVo.getMsg_Type() == 1) {
			mm.setReceiverId(0);
		} else {
			mm.setSeperateid(conversationId);
		}
		messageMasterRepo.save(mm);
		return result.getResultJSON(200,"success");
	}

	
	private String UploadFile(MultipartFile[] files) {
		StringBuilder st =new StringBuilder();
		for (MultipartFile multipartFile : files) {
			FileUpload file = new FileUpload();
			file.setName(multipartFile.getOriginalFilename());
			try {
				file.setFileContent(multipartFile.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			fileUploadRepository.save(file);
			mongoTemplate.save(file, "files");
			st.append(file.get_id()+",");
			
		}
		String str="";
		if(st.length()!=0) {
			str=st.toString().substring(0, st.toString().length()-1);
		}else {
			str=st.toString();
		}
		return str;
		
	}
	private Object readJSON() {

		JsonParser parser = new JsonParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(
					"D:\\Studies\\Eclipse\\oxygen workspace\\Krooz\\demo\\src\\main\\resources\\static\\JSON.json"));
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
			fileWriter = new FileWriter(
					"D:\\Studies\\Eclipse\\oxygen workspace\\Krooz\\demo\\src\\main\\resources\\static\\JSON.json");
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println(currentPrincipalName);
		StringBuilder st = new StringBuilder();
		List<User> usr = new ArrayList();

		GroupMaster grp = new GroupMaster();
		if (grpmast.getGrpId() != 0) {

			grp = groupmasterRepository.findOne(grpmast.getGrpId());
			if (grp == null)
				return "group does not exist";
		}

		for (User user : grpmast.participants) {
			st.append(user.getUid());
			user = userRepository.findOne(user.getUid());
			if (user == null) {
				return "user does not exist";
			}
			usr.add(user);

		}
		st.append(System.currentTimeMillis());
		if (grpmast.getGrpId() == 0) {
			if (grpmast.getGroupName() == "" || grpmast.getGroupName() == null) {
				grpmast.setGroupName("grp_" + st.toString());
			}
			MessageMaster result = new MessageMaster();
			result.setGroupId("grp_" + st.toString());
//			currentPrincipalName
			User usrdet = userRepository.findByUsername(currentPrincipalName);
			result.setSenderId(usrdet.getUid());
			messageMasterRepo.save(result);
		} else {
			String str = grpmast.getNickName();
			grpmast = grp;
			grpmast.setNickName(str);

		}
		grpmast.setParticipants(usr);
		groupmasterRepository.save(grpmast);
		return "success";
	}

	@Override
	public Constants deleteChat(int chatId) {
		MessageMaster messages = messageMasterRepo.findById(chatId).get();
		if(messages!=null) {
			if(messages.getSeperateid()!=null) {
				messageMasterRepo.deleteById(messages.getChatId());
			}else {
				//If it is group delete the chat in group user assoc.
				GroupMaster group=groupmasterRepository.findByName(messages.getGroupId());
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				
				User usrdet = userRepository.findByUsername(authentication.getName());
				
				groupUserRepository.DeleteGroupActive(group.getGrpId(),usrdet.getUid());
			}
		}else {
			return result.getResultJSON(301, "Delete Not Successful"); 
		}
		return result.getResultJSON(301, "Deleted Successfully"); 
	}

	public List<chatListDTO> getChatList() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User usrdet = userRepository.findByUsername(currentPrincipalName);
		List<chatListDTO> resultset = new ArrayList<chatListDTO>();
		chatListDTO dto = new chatListDTO();
		List<MessageMaster> msgMaster = messageMasterRepo.findChatsById(usrdet.getUid());
		
		JsonParser parser = new JsonParser();
		JsonArray Result = new JsonArray();
		for (MessageMaster messageMaster : msgMaster) {
			if (messageMaster.getSeperateid() != null) {
				List<User> user = userRepository.findChatsById(messageMaster.getSenderId(),
						messageMaster.getReceiverId());
				for (User usr : user) {
					if (usr.getUid() == usrdet.getUid()) {
						dto = new chatListDTO();
						dto.setName(userRepository.findOne(messageMaster.getReceiverId()).getUsername());
						dto.setChatId(messageMaster.getChatId());
						dto.setTime(System.currentTimeMillis());

						JsonObject jsonObject = (JsonObject) readJSON();
						JsonArray arr = new JsonArray();
						JsonArray jsonArray = new JsonArray();
						int cnt = 0;
						String lastSentMsg = "";
						if (jsonObject.has(messageMaster.getSeperateid())) {
							arr = (JsonArray) jsonObject.getAsJsonObject(messageMaster.getSeperateid()).get("chats");

							for (JsonElement jsonElement : arr) {

							/*	if (jsonElement.getAsJsonObject().get("flag").getAsString().equals("New")) {
									cnt++;
								}*/
								lastSentMsg = jsonElement.getAsJsonObject().get("Message").toString();
							}
						}
						//dto.setMsgcount(cnt);
						dto.setLastsentmsg(lastSentMsg);
						resultset.add(dto);
					}
				}
			} else {
				if (messageMaster.getGroupId() != null) {
					GroupMaster group = groupmasterRepository.findByName(messageMaster.getGroupId());
					System.out.println(group);
					dto = new chatListDTO();
					dto.setName(group.getNickName());
					dto.setChatId(messageMaster.getChatId());
					dto.setTime(System.currentTimeMillis());

					JsonObject jsonObject = (JsonObject) readJSON();
					JsonArray arr = new JsonArray();
					JsonArray jsonArray = new JsonArray();
					int cnt = 0;
					String lastSentMsg = "";
					if (jsonObject.has(messageMaster.getGroupId())) {
						arr = (JsonArray) jsonObject.getAsJsonObject(messageMaster.getGroupId()).get("chats");

						for (JsonElement jsonElement : arr) {
/*
							if (jsonElement.getAsJsonObject().get("flag").equals("New")) {
								cnt++;
							}*/
							lastSentMsg = jsonElement.getAsJsonObject().get("Message").toString();
						}
					}
					//dto.setMsgcount(cnt);
					dto.setLastsentmsg(lastSentMsg);
					//dto.setMsgcount(0);
					dto.setLastsentmsg("");
					resultset.add(dto);
				}
			}

		}

		return resultset;
	}

	public Constants createUser(UserDTO usr) {
		
		User u=userRepository.findByUsername(usr.getUsername());
		if(u!=null) {
			return result.getResultJSON(301, "User Already Exists");
		}
		u=userRepository.findByUsername(usr.getEmail());
		if(u!=null) {
			return result.getResultJSON(301, "Email Already Exists");
		}
		
		User user= new User();
		user.setEmail(usr.getEmail());
		user.setUsername(usr.getUsername());
		user.setPassword(usr.getPassword());
		user.setEnabled(true);
		List<Role> role=new ArrayList<>();
		Optional<Role> r=roleRepository.findById(2);
		
		role.add(r.get());
		user.setRoles(role);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	
		return result.getResultJSON(200, "success");
	}

	@Override
	public String getConversation(int chatId) {
		JsonParser parser = new JsonParser();
		JsonObject ResultJson= new JsonObject();
		MessageMaster messages = messageMasterRepo.findById(chatId).get();
		if(messages!=null) {
			JsonObject jsonObject = (JsonObject) readJSON();
			String json=jsonObject.toString();
				if(messages.getSeperateid()!=null) {
					 parser.parse(json).getAsJsonObject().getAsJsonObject(messages.getSeperateid());
					ResultJson.add("Conversation", parser.parse(json).getAsJsonObject().getAsJsonObject(messages.getSeperateid()));
				}else {
					ResultJson.add("Conversation", jsonObject.get(messages.getGroupId()));
				}			
		}else {
			ResultJson.addProperty("message", "Chat Not Exists");
			ResultJson.addProperty("status", "200");
		}
		
		String str=String.valueOf(ResultJson);
		/*try {
			
			System.out.println(str);
			map = mapper.readValue(str, new TypeReference<Map<String, String>>(){});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return str;
	}

	@Override
	public FileUpload downloadFile(String objectId) {
		
		return mongoTemplate.findById(objectId, FileUpload.class, "files");
	}
}

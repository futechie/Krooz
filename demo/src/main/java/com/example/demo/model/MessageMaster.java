package com.example.demo.model;

import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MessageMaster")
public class MessageMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="chatid")
	public int chatId;
	
	@Column(name ="senderid")
	public int senderId;

	
	@Column(name ="receiverid")
	public int receiverId;
	
	@Column(name ="groupid")
	public int groupId;
	
	@Column(name ="jsonid")
	public String jsonId;

	@Column(name ="createdDate")
	public Timestamp date;
	
	public String getJsonId() {
		return jsonId;
	}

	public void setJsonId(String jsonId) {
		this.jsonId = jsonId;
	}

	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	
	
}

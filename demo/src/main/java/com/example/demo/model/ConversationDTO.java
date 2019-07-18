package com.example.demo.model;

import java.sql.Timestamp;

public class ConversationDTO {
	
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getSentBy() {
		return SentBy;
	}
	public void setSentBy(String sentBy) {
		SentBy = sentBy;
	}
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	public String message;
	public String messageType;
	public String SentBy;
	public Timestamp createdTime;
	


}

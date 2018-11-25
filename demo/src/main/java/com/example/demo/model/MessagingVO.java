/**
 * 
 */
package com.example.demo.model;

/**
 * @author krishnanrajendran
 *
 */
public class MessagingVO {

	private String text;
	private int sender_id;
	private int receiver_id;
	private int chat_id;
	private int msg_Type;
	private String groupChatId;
	private String singleChatId; 
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getSender_id() {
		return sender_id;
	}
	public void setSender_id(int sender_id) {
		this.sender_id = sender_id;
	}
	public int getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(int receiver_id) {
		this.receiver_id = receiver_id;
	}
	public int getChat_id() {
		return chat_id;
	}
	public void setChat_id(int chat_id) {
		this.chat_id = chat_id;
	}
	public int getMsg_Type() {
		return msg_Type;
	}
	public void setMsg_Type(int msg_Type) {
		this.msg_Type = msg_Type;
	}
	public String getGroupChatId() {
		return groupChatId;
	}
	public void setGroupChatId(String groupChatId) {
		this.groupChatId = groupChatId;
	}
	public String getSingleChatId() {
		return singleChatId;
	}
	public void setSingleChatId(String singleChatId) {
		this.singleChatId = singleChatId;
	}
	
	@Override
	public String toString() {
		return "text :"+this.text+" sender_id :"+this.sender_id+" receiver_id :"
				+ " "+this.receiver_id+" chat_id : "+
				this.chat_id+" msg_Type : "+this.msg_Type+" groupChatId : "+this.groupChatId+
				" singleChatId : "+this.singleChatId;
	}
	
}

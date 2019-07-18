package com.example.demo.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.hibernate.annotations.GeneratorType;
import org.springframework.data.mongodb.core.mapping.Document;

public class FileUpload {
    public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	@Id
	public ObjectId _id;
	 
	private String name;
	private byte[] FileContent;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getFileContent() {
		return FileContent;
	}
	public void setFileContent(byte[] bs) {
		FileContent = bs;
	}
	

}

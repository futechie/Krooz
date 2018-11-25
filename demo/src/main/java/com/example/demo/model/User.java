package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Users")
public class User implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id")
	private int Uid;
	
	@Column(name = "username", nullable = false)
	@NotNull(message = "Please Enter Username")
	private String Username;
	
	@Column(name = "roleid", nullable = false)
	@NotNull(message = "Please Enter Username")
	private String RoleId;
	
	@Column(name = "emailid", nullable = true)
	private String email;
	
	@Column(name = "password", nullable = true)
	private String password;
	
	public int getUid() {
		return Uid;
	}
	public void setUid(int uid) {
		Uid = uid;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [Username=" + Username + ", RoleId=" + RoleId + ", email=" + email + "]";
	}
	
	
}
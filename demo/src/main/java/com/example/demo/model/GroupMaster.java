package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "groupmaster")
public class GroupMaster {
	public List<User> getParticipants() {
		return participants;
	}

	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="grpid")
	public int grpId;
	
	@Column(name ="groupname")
	public String groupName;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "groupuser_assoc", joinColumns = { @JoinColumn(name = "grpid") }, inverseJoinColumns = { @JoinColumn(name = "uid") })
	public List<User> participants;
	

	public int getGrpId() {
		return grpId;
	}

	public void setGrpId(int grpId) {
		this.grpId = grpId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String string) {
		this.groupName = string;
	}

}

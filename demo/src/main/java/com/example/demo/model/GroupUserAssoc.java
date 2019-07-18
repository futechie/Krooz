package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "groupuser_assoc")
public class GroupUserAssoc {


	@Id
	@Column(name ="grpid")
	public int grpId;
	
	@Column(name ="uid")
	public int userId;
	
	@Column(name ="isactive")
	public int isactive;
	
	
	public int getIsactive() {
		return isactive;
	}

	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

	public int getGrpId() {
		return grpId;
	}

	public void setGrpId(int grpId) {
		this.grpId = grpId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}

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

}

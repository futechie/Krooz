package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.GroupMaster;

@Repository
public interface GroupMasterRepository extends JpaRepository<GroupMaster, Integer>{
	@Query(value="select * from GroupMaster where grpId=:id",nativeQuery = true)
	GroupMaster findOne(int id);
	
	@Query(value="select * from GroupMaster where groupName=:groupId",nativeQuery = true)
	GroupMaster findByName(String groupId);
}

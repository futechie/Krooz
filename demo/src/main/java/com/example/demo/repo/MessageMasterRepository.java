package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.MessageMaster;

@Repository
public interface MessageMasterRepository extends JpaRepository<MessageMaster, Integer>{

	@Query(value="select m from MessageMaster m where chatId=:id")
	MessageMaster findOne(int id);
	
	@Query(value="select m from MessageMaster m where senderid=:userId or receiverid=:userId")
	List<MessageMaster> findChatsById(int userId);
	
}

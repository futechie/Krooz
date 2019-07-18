package com.example.demo.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.GroupMaster;
import com.example.demo.model.GroupUserAssoc;
import com.example.demo.model.Role;

@Repository
@Transactional
public interface GroupUserRepository extends JpaRepository<GroupUserAssoc, Integer>{
	
	@Query(value="select * from groupuser_assoc where grpid=:groupId",nativeQuery = true)
	List<GroupUserAssoc> findByGroupId(int groupId);
	
	@Modifying
	@Query(value="delete from groupuser_assoc  where grpid=:groupId and uid=:Uid",nativeQuery = true)
	void DeleteGroupActive(int groupId,int Uid);
	
	
}

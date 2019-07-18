package com.example.demo.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer>{
	@Query(value="select * from User where id=:id",nativeQuery = true)
	User findOne(int id);
	
	@Query(value="select * from User where email=:email",nativeQuery = true)
	User findByEmail(String email);
	
	User findByUsername(String username);

	@Query(value="select * from User where id in(:senderId,:receiverId) group by id",nativeQuery = true)
	List<User> findChatsById(int senderId, int receiverId);
	
}

package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.GroupMaster;
import com.example.demo.model.User;

@Repository
public interface GroupMasterRepository extends JpaRepository<GroupMaster, Integer>{


}
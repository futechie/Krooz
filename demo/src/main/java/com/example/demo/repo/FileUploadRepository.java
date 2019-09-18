package com.example.demo.repo;

import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.FileUpload;

public interface FileUploadRepository extends MongoRepository<FileUpload, String>,CollectionRepository {
	
	
  
}
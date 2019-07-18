package com.example.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.FileUpload;

public interface FileUploadRepository extends MongoRepository<FileUpload, String>,CollectionRepository {

  
}
package com.example.demo.repo;

public class CollectionRepositoryImpl implements CollectionRepository{
	
	private static String collectionName = "Images";

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

}

package com.example.spotifyrest.repository;

import com.example.spotifyrest.model.CustomPlaylistMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomPlaylistMongoRepository extends MongoRepository<CustomPlaylistMongo, String> {


}

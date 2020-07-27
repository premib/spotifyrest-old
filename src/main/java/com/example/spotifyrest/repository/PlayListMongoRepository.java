package com.example.spotifyrest.repository;

import com.example.spotifyrest.model.PlayListMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PlayListMongoRepository extends MongoRepository<PlayListMongo, String> {

    public boolean existsById(String id);

    public Optional<PlayListMongo> findById(String id);
}

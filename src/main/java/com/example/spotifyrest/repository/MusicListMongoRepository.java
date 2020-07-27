package com.example.spotifyrest.repository;

import com.example.spotifyrest.model.MusicListMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface MusicListMongoRepository extends MongoRepository<MusicListMongo, Integer> {

    public boolean existsByMusicName(String musicFileName);

    public List<MusicListMongo> findByMusicName(String musicFileName);

}

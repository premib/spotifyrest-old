package com.example.spotifyrest.repository;

import com.example.spotifyrest.model.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayListRepository extends JpaRepository<PlayList, String> {

//    PlayList findByPlayListId(String playListId);
//
    boolean existsByPlaylistName(String name);

    PlayList findByPlaylistName(String name);

    List<PlayList> findAll();
}

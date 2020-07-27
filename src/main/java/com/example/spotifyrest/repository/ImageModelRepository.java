package com.example.spotifyrest.repository;

import com.example.spotifyrest.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageModelRepository extends JpaRepository<ImageModel, String> {

    ImageModel findByPlaylistName(String name);
}

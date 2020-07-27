package com.example.spotifyrest.service;

import com.example.spotifyrest.model.ImageModel;
import com.example.spotifyrest.vo.AuthResponse;
import com.example.spotifyrest.vo.Music;
import com.example.spotifyrest.vo.PlayListPojo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AdminServiceable {

    Boolean uploadImage(MultipartFile imageFile, String imageName) throws IOException;

    ImageModel retrieveImage(String imageName);

    AuthResponse uploadPlayList(PlayListPojo playListPojo);

    AuthResponse AddSongsToPlaylist(String playlistName, String songId);

    AuthResponse uploadMusicFile(MultipartFile audioFile) throws IOException;

    Music retrieveMusicFile(String fileName);

//    @Transactional
//    AuthResponse uploadPlayList(MultipartFile image, String name, PlayListAddition playListAddition) throws IOException;
}

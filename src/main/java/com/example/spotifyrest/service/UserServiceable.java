package com.example.spotifyrest.service;

import com.example.spotifyrest.vo.*;

import java.util.List;


public interface UserServiceable {

    AuthResponse registerNewUser(Register newUser);

    AuthResponse login(UserLogin credential);

    List<HomeRequest> retrieveAllPlaylists();

    List<SongListRetrieve> getPlayList(String playlistName);
}

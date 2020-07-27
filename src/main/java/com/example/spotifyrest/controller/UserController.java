package com.example.spotifyrest.controller;

import com.example.spotifyrest.service.UserServiceable;
import com.example.spotifyrest.vo.AuthResponse;
import com.example.spotifyrest.vo.HomeRequest;
import com.example.spotifyrest.vo.SongListRetrieve;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/user/")
public class UserController {

    @Autowired
    UserServiceable userServiceable;

    @GetMapping(value = "home")
    public ResponseEntity<List<HomeRequest>> setHomePage(){
        return new ResponseEntity<>(userServiceable.retrieveAllPlaylists(), HttpStatus.OK);
    }

    @GetMapping(value = "playlist")
    public ResponseEntity<List<SongListRetrieve>> getplaylistDetails(@RequestParam String playlistMongoId){
        return new ResponseEntity<>(userServiceable.getPlayList(playlistMongoId), HttpStatus.OK);
    }
}

package com.example.spotifyrest.controller;

import com.example.spotifyrest.model.PlayList;
import com.example.spotifyrest.repository.PlayListRepository;
import com.example.spotifyrest.security.JWTProvider;
import com.example.spotifyrest.service.UserServiceable;
import com.example.spotifyrest.vo.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "v1/auth/")
public class AuthenticationController {

    @Autowired
    UserServiceable userServiceable;

    @Autowired
    PlayListRepository playListRepository;

    @Autowired
    JWTProvider jwtProvider;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> registerAccount(@RequestBody Register credentials){
        return new ResponseEntity<>(userServiceable.registerNewUser(credentials), HttpStatus.OK);
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLogin credentials){
        return new ResponseEntity<>(userServiceable.login(credentials), HttpStatus.OK);
    }

    @GetMapping(value = "home")
    public ResponseEntity<List<HomeRequest>> setHomePage(){
        return new ResponseEntity<>(userServiceable.retrieveAllPlaylists(), HttpStatus.OK);
    }

    @GetMapping(value = "playlist")
    public ResponseEntity<List<SongListRetrieve>> getplaylistDetails(@RequestParam String playlistMongoId){
        return new ResponseEntity<>(userServiceable.getPlayList(playlistMongoId), HttpStatus.OK);
    }

//    @GetMapping(value = "username")
//    public ResponseEntity<AuthResponse> gets(@RequestParam("token") String token){
//        System.out.println(jwtProvider.getUsername(token));
//        System.out.println(jwtProvider.getAuthentication(token));
//        System.out.println(jwtProvider.validateToken(token));
//        return new ResponseEntity<>(new AuthResponse(jwtProvider.getUsername(token)), HttpStatus.OK);
//    }



//    @PostMapping(value = "upload")
//    public ResponseEntity<AuthResponse> upload(@RequestBody MultipartFile)
//    @Autowired
//    AccountsRepository accountsRepository;
//    @DeleteMapping(value = "delete")
//    public ResponseEntity<AuthResponse> delete(@RequestBody UserLogin email){
//        System.out.print("asdasd");
//        System.out.println(email.getEmail()+" "+email.getPassword());
//        accountsRepository.deleteByEmail(email.getEmail());
//        return new ResponseEntity<>(new AuthResponse("asdsad"),HttpStatus.OK);
//    }
}

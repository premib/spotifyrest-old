package com.example.spotifyrest.controller;

import com.example.spotifyrest.exception.ServiceException;
import com.example.spotifyrest.repository.AccountsRepository;
import com.example.spotifyrest.repository.ImageModelRepository;
import com.example.spotifyrest.repository.MusicListMongoRepository;
import com.example.spotifyrest.repository.PlayListRepository;
import com.example.spotifyrest.service.AdminServiceable;
import com.example.spotifyrest.vo.AuthResponse;
import com.example.spotifyrest.vo.Music;
import com.example.spotifyrest.vo.PlayListPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "v1/admin/")
public class AdminController {

    @Autowired
    AdminServiceable adminServiceable;

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    PlayListRepository playListRepository;

    @Autowired
    ImageModelRepository imageModelRepository;

    @Autowired
    MusicListMongoRepository musicListMongoRepository;

    @PostMapping("playlist")
    public ResponseEntity<AuthResponse> uploadImage(@RequestParam("imageFile") MultipartFile image, PlayListPojo file) throws IOException {
        System.out.println("Original Image Byte Size - " + image.getBytes().length);
        if(adminServiceable.uploadImage(image, file.getPlayListName())){
            return new ResponseEntity<>(adminServiceable.uploadPlayList(file), HttpStatus.OK);
        }
        throw new ServiceException("Can't upload playlist to database", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = {"image/{imageName}"})
    public ResponseEntity<AuthResponse> getImage(@PathVariable("imageName") String imageName) throws IOException {
        return new ResponseEntity<>(new AuthResponse(adminServiceable.retrieveImage(imageName).toString()), HttpStatus.OK);
    }

    @GetMapping(path = {"music/{musicName}"})
    public ResponseEntity<Music> getMusic(@PathVariable("musicName") String file){
        return new ResponseEntity<>(adminServiceable.retrieveMusicFile(file), HttpStatus.OK);
    }

    @PostMapping(path = {"playlist/update"})
    public ResponseEntity<AuthResponse> updatePlaylist(@RequestParam("playlistName") String playlistName, @RequestParam("songId") String songId){
        return new ResponseEntity<>(adminServiceable.AddSongsToPlaylist(playlistName, songId), HttpStatus.OK);
    }

    @PostMapping("music")
    public ResponseEntity<AuthResponse> audio(@RequestParam("audioFile") MultipartFile audio) throws IOException {
        return new ResponseEntity<>(adminServiceable.uploadMusicFile(audio), HttpStatus.OK);
    }
//    @PostMapping("/music")
//    public ResponseEntity<AuthResponse> uploadMusic(@RequestParam("audioFile") MultipartFile music){
//
//    }
    //    @GetMapping(path = { "/get/{imageName}" })
//    public PlayList getImage(@PathVariable("imageName") String imageName) throws IOException {
//        final Optional<PlayList> retrievedImage = playListRepository.findByName(imageName);
//
//        PlayList img = new PlayList(retrievedImage.get().getName(), retrievedImage.get().getType(),
//                decompressBytes(retrievedImage.get().getPicByte()));
//        System.out.println(img  );
//        return img;
//    }

}

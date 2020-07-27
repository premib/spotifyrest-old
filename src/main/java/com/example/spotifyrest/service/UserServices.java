package com.example.spotifyrest.service;

import com.example.spotifyrest.exception.ServiceException;
import com.example.spotifyrest.model.*;
import com.example.spotifyrest.repository.*;
import com.example.spotifyrest.security.JWTProvider;
import com.example.spotifyrest.vo.*;

import static com.example.spotifyrest.service.FileService.decompressBytes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices implements UserServiceable {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private PlayListMongoRepository playListMongoRepository;

    @Autowired
    private CustomPlaylistMongoRepository customPlaylistMongoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public AuthResponse registerNewUser(Register newUser) {

        if(!accountsRepository.existsByEmail(newUser.getEmail()) && !accountDetailRepository.existsById(newUser.getEmail())){

            CustomPlaylistMongo customPlaylistMongo = new CustomPlaylistMongo();
            customPlaylistMongo.setEmail(newUser.getEmail());
            CustomPlaylistMongo retrieveCustomPlaylistMongo = customPlaylistMongoRepository.save(customPlaylistMongo);

            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            Accounts newUserAccount = modelMapper.map(newUser, Accounts.class);
            AccountDetail newUserAccountDetail = modelMapper.map(newUser, AccountDetail.class);
            newUserAccountDetail.setPlaylistId(retrieveCustomPlaylistMongo.getId());

            accountsRepository.save(newUserAccount);
            accountDetailRepository.save(newUserAccountDetail);

            String token = jwtProvider.createToken(newUser.getEmail(), Arrays.asList(Role.ROLE_USER));

            return new AuthResponse(token);
        }
        else{
            throw new ServiceException("User with email already exists!", HttpStatus.CONFLICT);
        }
    }

    @Transactional
    @Override
    public AuthResponse login(UserLogin credential){

        try{
            String email = credential.getEmail();
            System.out.println(accountsRepository.findByEmail(email));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, credential.getPassword()));
            return new AuthResponse(jwtProvider.createToken(email, Arrays.asList(Role.ROLE_USER)));
        } catch (AuthenticationException e) {
            throw new ServiceException("Invalid email/ password", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public List<HomeRequest> retrieveAllPlaylists() {
        try{
            List<PlayList> playListList = playListRepository.findAll();
            List<HomeRequest> homeRequestList = new ArrayList<>();
            for (PlayList playList : playListList) {
                HomeRequest homeRequest = new HomeRequest();
                homeRequest.setPlaylistName(playList.getPlaylistName());
                homeRequest.setPlaylistDescription(playList.getPlaylistDescription());
                homeRequest.setType(playList.getImageModel().getType());
                homeRequest.setPicByte(decompressBytes(playList.getImageModel().getPicByte()));
                homeRequest.setPlaylistMongoId(playList.getPlaylistMongoId());
                homeRequestList.add(homeRequest);
            }
            return homeRequestList;
        }
        catch (Exception e){
            throw new ServiceException("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<SongListRetrieve> getPlayList(String playlistMongoId){
        List<SongListRetrieve> songListRetrieves;
        try{
            if(playListMongoRepository.existsById(playlistMongoId)){
                songListRetrieves = new ArrayList<>();

                Optional<PlayListMongo> retrievedPlayListMongo = playListMongoRepository.findById(playlistMongoId);
                List<String> retrievedSongList = retrievedPlayListMongo.get().getSongId();
                retrievedSongList.forEach((songId)->{
                    Query query = new Query();
                    query.addCriteria(Criteria.where("id").is(songId)).fields().include("id").include("musicName").include("musicType");
                    MusicListMongo musicListMongo = mongoTemplate.findOne(query, MusicListMongo.class);

                    SongListRetrieve songListRetrieve = new SongListRetrieve();
                    songListRetrieve.setSongDescriptions(musicListMongo.getMusicType());
                    songListRetrieve.setSongId(musicListMongo.getId());
                    songListRetrieve.setSongNames(musicListMongo.getMusicName());

                    songListRetrieves.add(songListRetrieve);
                });
                return songListRetrieves;
            }
        }
        catch (Exception e){
            throw new ServiceException("Bad request"+e, HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}

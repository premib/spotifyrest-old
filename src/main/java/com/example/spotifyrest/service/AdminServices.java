package com.example.spotifyrest.service;

import com.example.spotifyrest.exception.ServiceException;
import com.example.spotifyrest.model.ImageModel;
import com.example.spotifyrest.model.MusicListMongo;
import com.example.spotifyrest.model.PlayList;
import com.example.spotifyrest.model.PlayListMongo;
import com.example.spotifyrest.repository.ImageModelRepository;
import com.example.spotifyrest.repository.MusicListMongoRepository;
import com.example.spotifyrest.repository.PlayListMongoRepository;
import com.example.spotifyrest.repository.PlayListRepository;
import com.example.spotifyrest.vo.AuthResponse;
import com.example.spotifyrest.vo.Music;
import com.example.spotifyrest.vo.PlayListPojo;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.spotifyrest.service.FileService.compressBytes;
import static com.example.spotifyrest.service.FileService.decompressBytes;

@Service
public class AdminServices implements AdminServiceable {

    @Autowired
    ImageModelRepository imageModelRepository;

    @Autowired
    PlayListRepository playListRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MusicListMongoRepository musicListMongoRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PlayListMongoRepository playListMongoRepository;


    @Override
    public Boolean uploadImage(MultipartFile image, String name) throws IOException {

        ImageModel img = new ImageModel(name, image.getContentType(), compressBytes(image.getBytes()));
        imageModelRepository.save(img);
        return true;
    }

    @Override
    public ImageModel retrieveImage(String imageName){

        ImageModel img = imageModelRepository.findByPlaylistName(imageName);
        img.setPicByte(decompressBytes(img.getPicByte()));
        return img;
    }

    @Override
    public AuthResponse uploadPlayList( PlayListPojo playListPojo)  {
        try{
            PlayListMongo playListMongo = new PlayListMongo(playListPojo.getPlayListName());
            PlayListMongo retrievedPlayList = playListMongoRepository.save(playListMongo);

            PlayList playList = modelMapper.map(playListPojo, PlayList.class);
            playList.setPlaylistMongoId(retrievedPlayList.getId());
            playListRepository.save(playList);

            return new AuthResponse("Created PlayList");
        }
        catch (NullPointerException nully){
            throw new ServiceException("Bad request format faced "+nully+" while processing", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public AuthResponse AddSongsToPlaylist(String playlistName, String songId){

        try{
            Query query = new Query();
            Update update = new Update();

            String mongoPlaylistId = playListRepository.findByPlaylistName(playlistName).getPlaylistMongoId();
            query.addCriteria(Criteria.where("id").is(mongoPlaylistId));
            update.push("songId", songId);
            PlayListMongo retrievedPlayListMongo = mongoTemplate.findAndModify(query, update, PlayListMongo.class);

            return new AuthResponse(retrievedPlayListMongo.toString());
        }
        catch (NullPointerException e){
            throw new ServiceException("Null value found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @Override
    public AuthResponse uploadMusicFile(MultipartFile audioFile){

        try{
            MusicListMongo musicFile = new MusicListMongo(audioFile.getOriginalFilename(), audioFile.getContentType());
            musicFile.setAudioBytes(new Binary(BsonBinarySubType.BINARY, compressBytes(audioFile.getBytes())));
            musicListMongoRepository.save(musicFile);

            return new AuthResponse("Created MusicFile");
        } catch (IOException e) {

            System.out.println(e);
            throw new ServiceException("IOException", HttpStatus.BAD_REQUEST);
        }

    }


    @Override
    public Music retrieveMusicFile(String fileName){

        try{
            Query query = new Query();

            query.addCriteria(Criteria.where("musicName").is(fileName));
            MusicListMongo musicList = mongoTemplate.findOne(query, MusicListMongo.class);

            Music music = null;
            if (musicList != null) {
                music = new Music(musicList.getId(), musicList.getMusicName(),musicList.getMusicType(), decompressBytes(musicList.getAudioBytes().getData()));
            }
            return music;
        }
        catch (NullPointerException e){
            throw new ServiceException("File Not found", HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            throw new ServiceException("Error occured", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

//    @Override
//    public

}

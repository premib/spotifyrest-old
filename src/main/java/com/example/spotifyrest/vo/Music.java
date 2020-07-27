package com.example.spotifyrest.vo;

import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;

@Data
@ToString
public class Music {

    private String musicId;

    private String musicName;

    private String musicType;

    private byte[] audio;

    public Music(String musicId, String musicName, String musicType, byte[] audio){

        this.musicId = musicId;
        this.musicName = musicName;
        this.musicType = musicType;
        this.audio = audio;
    }
}

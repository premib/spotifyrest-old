package com.example.spotifyrest.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Data
@ToString
@Entity
@NoArgsConstructor
@Document(collection = "music")
public class MusicListMongo {

    public MusicListMongo(String name, String type){
        musicName = name;
        musicType = type;
    }

    @Id
    private String id;

    @Indexed(unique = true)
    private String musicName;

    private String musicType;

    private Binary audioBytes;
}

package com.example.spotifyrest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@Document("playlistMongo")
public class PlayListMongo {

    @Id
    private String id;

    private String playlistName;

    private List<String> songId;

    public PlayListMongo(String name){

        playlistName = name;
    }
}

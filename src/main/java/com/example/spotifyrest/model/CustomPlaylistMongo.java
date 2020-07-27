package com.example.spotifyrest.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.List;

@Data
@ToString
@Document("customPlaylist")
public class CustomPlaylistMongo {

    @Id
    private String id;

    private String email;

    private HashMap<String, String[]> playListId;
}

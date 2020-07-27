package com.example.spotifyrest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "PlayList")
public class PlayList {

    @Id
    private String playlistName;

    @Column(nullable = false)
    private String playlistDescription;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "playlistName")
    private ImageModel imageModel;

    @Column(name = "playlist_mongo_id")
    private String playlistMongoId;
}

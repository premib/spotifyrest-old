package com.example.spotifyrest.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SongListRetrieve {

    String songId;
    String songNames;
    String songDescriptions;
}

package com.example.spotifyrest.vo;


import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@ToString
public class HomeRequest {

    @Id
    private String playlistName;

    private String playlistDescription;

    private String type;

    private byte[] picByte;

    private String playlistMongoId;
}

package com.example.spotifyrest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "ImageModel")
@Data
@ToString
@NoArgsConstructor
public class ImageModel {

    public ImageModel( String name, String type, byte[] picByte) {
        this.playlistName = name;
        this.type = type;
        this.picByte = picByte;
    }

    @Id
    private String playlistName;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "picByte")
    private byte[] picByte;

}

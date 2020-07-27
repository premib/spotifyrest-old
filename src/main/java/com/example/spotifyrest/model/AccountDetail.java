package com.example.spotifyrest.model;

import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
@Table(name = "AccountDetail")
public class AccountDetail {

    @Id
    @Column(name = "email")
    private String email;

    private String userName;

    private Date dob;

    private String gender;

    private boolean shareData;

    private String playlistId;
}

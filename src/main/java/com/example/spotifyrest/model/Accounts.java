package com.example.spotifyrest.model;

import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "Accounts")
@Data
@ToString
public class Accounts {

    @Id
    @Column(name = "email")
    private String email;

    private String password;

    private LocalDateTime lastLogin = LocalDateTime.now();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="user_roles", joinColumns=@JoinColumn(name="user_id"))
    private List<Role> role;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private AccountDetail accountDetail;

    private ObjectId customPlaylist;
}

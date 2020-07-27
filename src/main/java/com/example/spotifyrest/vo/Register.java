package com.example.spotifyrest.vo;

import com.example.spotifyrest.model.Role;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@NoArgsConstructor
public class Register extends UserLogin {

    @NonNull
    private String userName;

    private List<Role> role;

    @NonNull
    private Date dob;

    @NonNull
    private boolean shareData;

    @NonNull
    private String gender;

    public List<Role> getRole(){

        return  Arrays.asList(Role.ROLE_USER);
    }

}

package com.example.spotifyrest.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER, ROLE_PREMIUM_USER;

    public String getAuthority() {
        return name();
    }

}

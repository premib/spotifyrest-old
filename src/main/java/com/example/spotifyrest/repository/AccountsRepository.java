package com.example.spotifyrest.repository;

import com.example.spotifyrest.model.Accounts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AccountsRepository extends JpaRepository<Accounts, String> {

    boolean existsByEmail(String email);

    Accounts findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
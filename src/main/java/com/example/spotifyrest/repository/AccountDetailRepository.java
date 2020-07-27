package com.example.spotifyrest.repository;

import com.example.spotifyrest.model.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDetailRepository extends JpaRepository<AccountDetail, String> {

    boolean existsByEmail(String email);

    AccountDetail getOne(String email);

    AccountDetail findByEmail(String email);

}

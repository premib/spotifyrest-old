package com.example.spotifyrest;

import com.example.spotifyrest.model.Accounts;
import com.example.spotifyrest.repository.AccountsRepository;
import com.example.spotifyrest.repository.PlayListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class SpotifyrestApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(SpotifyrestApplication.class, args);
	}

	@Autowired
	AccountsRepository accountsRepository;


	@Override
	public void run(String... args) throws Exception {



	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}

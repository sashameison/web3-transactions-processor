package com.narozhnyi.web3;

import com.narozhnyi.web3.service.Web3ARBNodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class Application implements CommandLineRunner {
	private final Web3ARBNodeService web3ARBNodeService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		web3ARBNodeService.subscribeToARBTransactions();
	}
}

package com.atyeti.tradingApp;

import com.atyeti.tradingApp.models.HistoryModel;
import com.atyeti.tradingApp.repository.HistoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBackendApplication.class, args);
    }


}

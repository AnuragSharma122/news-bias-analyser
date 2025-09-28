package com.example.news.rss_ingestion_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RssIngestionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RssIngestionServiceApplication.class, args);
	}

}

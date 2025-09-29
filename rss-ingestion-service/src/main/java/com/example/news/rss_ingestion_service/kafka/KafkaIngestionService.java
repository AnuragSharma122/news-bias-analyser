package com.example.news.rss_ingestion_service.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaIngestionService {
    public void ingest(KafkaMessageBuilder kafkaMessageBuilder){
        log.info("ingesting");
    }
}

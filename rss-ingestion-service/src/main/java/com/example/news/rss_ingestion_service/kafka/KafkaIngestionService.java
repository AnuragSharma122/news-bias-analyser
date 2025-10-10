package com.example.news.rss_ingestion_service.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaIngestionService {

    @Autowired
    private KafkaTemplate<String, ArticleKafkaMessage> kafkaTemplate;

    @Value("${app.kafka.topic.article}")
    private String topic;

    public void sendMessage(ArticleKafkaMessage message){
        log.info("ingesting");
        kafkaTemplate.send(topic, message.getGuid(), message);
    }
}

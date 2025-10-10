package com.example.news.rss_ingestion_service.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleKafkaMessage {
    private String publisher;
    private String title;
    private String link;
    private LocalDateTime pubDate;
    private String content;
    private String guid;
}

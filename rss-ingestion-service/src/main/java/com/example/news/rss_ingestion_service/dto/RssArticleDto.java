package com.example.news.rss_ingestion_service.dto;

import lombok.*;


import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RssArticleDto {
    private String title;
    private String link;
    private String guid;
    private LocalDateTime pubDate;
    private String content;
    private String guidHash;
}

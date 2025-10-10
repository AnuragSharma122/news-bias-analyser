package com.example.news.rss_ingestion_service.services;

public interface ArticleContentExtractor {
    public String extractArticleContent(String publisher, String url);
}

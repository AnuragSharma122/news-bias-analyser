package com.example.news.rss_ingestion_service.parser;

public interface HTMLArticleParser {
    public String extractContentFromArticle(String url);
}

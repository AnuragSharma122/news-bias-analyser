package com.example.news.rss_ingestion_service.services.Impl;

import com.example.news.rss_ingestion_service.dto.ArticlePublisher;
import com.example.news.rss_ingestion_service.parser.HTMLArticleParserFactory;
import com.example.news.rss_ingestion_service.services.ArticleContentExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArticleContentExtractorImpl implements ArticleContentExtractor {
    @Override
    public String extractArticleContent(String publisher, String url) {
        return HTMLArticleParserFactory.getParser(ArticlePublisher.valueOf(publisher)).extractContentFromArticle(url);
    }
}

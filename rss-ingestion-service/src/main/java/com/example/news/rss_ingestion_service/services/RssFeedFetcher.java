package com.example.news.rss_ingestion_service.services;

import com.example.news.rss_ingestion_service.dto.RssArticleDto;

import java.util.List;

public interface RssFeedFetcher {
    public List<RssArticleDto> getRssArticlesFromFeedLink(String feedUrl);
}

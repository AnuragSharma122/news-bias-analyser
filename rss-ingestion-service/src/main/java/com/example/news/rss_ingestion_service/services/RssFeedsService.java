package com.example.news.rss_ingestion_service.services;


import com.example.news.rss_ingestion_service.dto.RssFeedDto;

import java.io.IOException;
import java.util.List;

public interface RssFeedsService {
    public List<RssFeedDto> getAllRssFeeds() throws IOException;
}

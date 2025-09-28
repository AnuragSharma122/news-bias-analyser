package com.example.news.rss_ingestion_service.services.Impl;

import com.example.news.rss_ingestion_service.dto.RssFeedDto;
import com.example.news.rss_ingestion_service.services.RssFeedsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class RssFeedsServiceImpl implements RssFeedsService {
    @Value("classpath:rssFeeds.json")
    private Resource rssJson;

    @Override
    public List<RssFeedDto> getAllRssFeeds() throws IOException {
        log.info("Fetching Rss feeds from properties");
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = rssJson.getInputStream()) {
            return objectMapper.readValue(is, new TypeReference<List<RssFeedDto>>() {});
        }
    }
}

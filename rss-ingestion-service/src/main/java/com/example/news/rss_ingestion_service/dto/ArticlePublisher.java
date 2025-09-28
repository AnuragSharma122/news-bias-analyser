package com.example.news.rss_ingestion_service.dto;


public enum ArticlePublisher {
    TIMES_OF_INDIA("The Times of India"),
    NDTV("NDTV"),
    OP_INDIA("OpIndia"),
    INDIA_TODAY("India Today"),
    REPUBLIC_BHARAT("Republic Bharat"),
    INDIAN_EXPRESS("Indian Express"),
    THE_HINDU("The Hindu");

    private final String domain;

    ArticlePublisher(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

}


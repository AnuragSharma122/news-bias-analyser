package com.example.news.rss_ingestion_service.parser;
import com.example.news.rss_ingestion_service.dto.ArticlePublisher;

public class HTMLArticleParserFactory {
    public static HTMLArticleParser getParser(ArticlePublisher publisher) {
        return switch (publisher) {
            case TIMES_OF_INDIA -> new TimesOfIndiaParser();
            case NDTV -> new NDTVParser();
            case OP_INDIA -> new OpIndiaParser();
            case INDIA_TODAY -> new IndiaTodayParser();
            case REPUBLIC_BHARAT -> new RepublicBharatParser();
            case THE_HINDU -> new TheHinduParser();
            case INDIAN_EXPRESS -> new IndianExpressParser();
        };
    }
}

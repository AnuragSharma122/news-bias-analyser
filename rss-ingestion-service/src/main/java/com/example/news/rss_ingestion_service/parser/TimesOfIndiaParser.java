package com.example.news.rss_ingestion_service.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Slf4j
public class TimesOfIndiaParser implements HTMLArticleParser{
    @Override
    public String extractContentFromArticle(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10_000)
                    .get();

            Element articleDiv = doc.selectFirst("div[data-articlebody]");
            if (articleDiv != null) {
                return articleDiv.text();
            }else{
                return null;
            }
        } catch (Exception e) {
            log.error("Failed to fetch article content from {}", url, e);
            return null;
        }
    }
}

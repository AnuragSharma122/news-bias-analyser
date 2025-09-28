package com.example.news.rss_ingestion_service.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class TheHinduParser implements HTMLArticleParser{
    @Override
    public String extractContentFromArticle(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10_000)
                    .get();

            Elements articleDiv = doc.select("div.articlebodycontent");

            StringBuilder articleText = new StringBuilder();
            for (Element p : articleDiv.select("p")) {
                articleText.append(p.text());
            }

            return articleText.toString();
        } catch (Exception e) {
            log.error("Failed to fetch article content from {}", url, e);
            return null;
        }
    }
}

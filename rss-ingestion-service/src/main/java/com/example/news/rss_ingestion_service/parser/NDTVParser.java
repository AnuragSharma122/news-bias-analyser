package com.example.news.rss_ingestion_service.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Slf4j
public class NDTVParser implements HTMLArticleParser{
    @Override
    public String extractContentFromArticle(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                            + "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .referrer("https://www.google.com/")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("authority","www.ndtv.com")
                    .header("cookie","PublisherProvidedIdsNew=publisherprovidedids268221102ndtvids; __usrCntry=IN; uid-s=5e356c7-a119-4760-becc-b810a595177f; vuukle_geo_region={%22country_code%22:%22IN%22%2C%22region%22:%22Haryana%22%2C%22os%22:%22Windows%22%2C%22device%22:%22Desktop%22%2C%22browser%22:%22Chrome%22}; vsid=ed8f3c98-7ba8-4c05-9f37-74d43a8dc80d")
                    .timeout(10_000)
                    .get();

            Elements paragraphs = doc.select("p");

            return paragraphs.stream()
                    .map(Element::text)
                    .reduce((p1, p2) -> p1 + "\n" + p2)
                    .orElse(null);
        } catch (Exception e) {
            log.error("Failed to fetch article content from {}", url, e);
            return null;
        }
    }
}

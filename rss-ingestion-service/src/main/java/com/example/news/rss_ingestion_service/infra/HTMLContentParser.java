package com.example.news.rss_ingestion_service.infra;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

import java.net.URL;

@Service
@Slf4j
public class HTMLContentParser {
    private final RestTemplate restTemplate;

    public HTMLContentParser(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getContentFromArticleLink(String newsArticleUrl){
        //get the content from the article and return as string.
        try {
            Document doc = Jsoup.connect(newsArticleUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10_000)
                    .get();

            Element articleDiv = doc.selectFirst("div[data-articlebody]");
            if (articleDiv != null) {
                return articleDiv.text();
            }

            Element article = doc.selectFirst("articlebody");
            if (article != null) {
                return article.text();
            }

            // 2. Try div with id containing 'article'
            Element divById = doc.selectFirst("div[id*=articlebody]");
            if (divById != null) {
                return divById.text();
            }

            // 3. Try div with class containing 'article'
            Element divByClass = doc.selectFirst("div[class*=article]");
            if (divByClass != null) {
                return divByClass.text();
            }


            Element divByIdBody = doc.selectFirst("div[id*=body]");
            if (divByIdBody != null) {
                return divByIdBody.text();
            }

            // 3. Try div with class containing 'article'
            Element divByClassBody = doc.selectFirst("div[class*=body]");
            if (divByClassBody != null) {
                return divByClassBody.text();
            }
            return null;
        } catch (Exception e) {
            log.error("Failed to fetch article content from {}", newsArticleUrl, e);
            return null;
        }
    }
}

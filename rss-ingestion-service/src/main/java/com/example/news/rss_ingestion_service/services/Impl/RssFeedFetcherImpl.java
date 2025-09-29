package com.example.news.rss_ingestion_service.services.Impl;

import com.example.news.rss_ingestion_service.dto.RssArticleDto;
import com.example.news.rss_ingestion_service.services.RssFeedFetcher;
import com.example.news.rss_ingestion_service.util.HashUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RssFeedFetcherImpl implements RssFeedFetcher {

    private final RestTemplate restTemplate;

    public List<RssArticleDto> getRssArticlesFromFeedLink(String feedUrl){

        List<RssArticleDto> articles = new ArrayList<>();
        try {
            String rssXml = restTemplate.getForObject(feedUrl, String.class);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(rssXml));
            Document doc = builder.parse(is);

            NodeList itemNodes = doc.getElementsByTagName("item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                Element item = (Element) itemNodes.item(i);
                String title = item.getElementsByTagName("title").item(0).getTextContent();
                String link = item.getElementsByTagName("link").item(0).getTextContent();
                String pubDateStr = item.getElementsByTagName("pubDate").item(0).getTextContent();
                LocalDateTime pubDate = LocalDateTime.parse(pubDateStr, DateTimeFormatter.RFC_1123_DATE_TIME);
                String guid;
                NodeList guidNodeList = item.getElementsByTagName("guid");
                if(guidNodeList.item(0) == null || guidNodeList.getLength() == 0){
                    guid = link;
                }else{
                    guid = guidNodeList.item(0).getTextContent();
                }
                String guidHash = HashUtil.sha256(guid);

                /*
                    Check if guidHash is present in redis or not. If not then only add the article
                 */

                RssArticleDto article = RssArticleDto.builder().title(title)
                        .link(link)
                        .guid(guid)
                        .pubDate(pubDate)
                        .guidHash(guidHash)
                        .build();

                articles.add(article);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error("Error fetching/parsing RSS feed", e);
        }
        return articles;
    }
}

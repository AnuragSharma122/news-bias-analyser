package com.example.news.rss_ingestion_service.scheduler;

import com.example.news.rss_ingestion_service.dto.ArticlePublisher;
import com.example.news.rss_ingestion_service.dto.RssArticleDto;
import com.example.news.rss_ingestion_service.dto.RssFeedDto;
import com.example.news.rss_ingestion_service.infra.HTMLContentParser;
import com.example.news.rss_ingestion_service.infra.HttpCommunicationService;
import com.example.news.rss_ingestion_service.parser.HTMLArticleParserFactory;
import com.example.news.rss_ingestion_service.services.RssFeedsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class RssFeedIngestionScheduler {

    private final RssFeedsService rssFeedsService;
    private final HttpCommunicationService httpCommunicationService;
    private final HTMLContentParser htmlContentParser;

    @Scheduled(fixedRate = 600000L)
    public void rssFeedIngestionScheduler(){
        try {
            List<RssFeedDto> rssFeedDtoList = rssFeedsService.getAllRssFeeds();
            for(RssFeedDto rssFeed : rssFeedDtoList){
                log.info("Fetching the rss feed {}", rssFeed.toString());
                //fetch the xml data from link.
                List<RssArticleDto> rssArticleDtoList = this.httpCommunicationService.getRssArticlesFromFeedLink(rssFeed.getRssLink());
                log.info("Fetched {} rss Articles from {} publication",rssArticleDtoList.size(),rssFeed.getPublisher());
                //Fetch the content from the link, create kafka message and ingest in kafka
                for(RssArticleDto rssArticleDto : rssArticleDtoList){
                    String Content = HTMLArticleParserFactory.getParser(ArticlePublisher.valueOf(rssFeed.getPublisher())).extractContentFromArticle(rssArticleDto.getLink());
                    if(Content != null){
                        log.info("Content : {}", Content);
                        // ingest the article to kafka
                        // mark the article as read in redis
                    }
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

    }
}

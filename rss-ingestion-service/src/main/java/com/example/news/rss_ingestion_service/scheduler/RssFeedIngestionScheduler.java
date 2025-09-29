package com.example.news.rss_ingestion_service.scheduler;

import com.example.news.rss_ingestion_service.dto.RssArticleDto;
import com.example.news.rss_ingestion_service.dto.RssFeedDto;
import com.example.news.rss_ingestion_service.kafka.KafkaIngestionService;
import com.example.news.rss_ingestion_service.kafka.KafkaMessageBuilder;
import com.example.news.rss_ingestion_service.services.ArticleContentExtractor;
import com.example.news.rss_ingestion_service.services.RssFeedFetcher;
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
    private final RssFeedFetcher rssFeedFetcher;
    private final ArticleContentExtractor articleContentExtractor;
    private final KafkaIngestionService kafkaIngestionService;

    @Scheduled(fixedRate = 600000L)
    public void rssFeedIngestionScheduler(){
        try {
            rssFeedsService.getAllRssFeeds().parallelStream().forEach(this::processFeed);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    public void processFeed(RssFeedDto rssFeed){
        log.info("Fetching the rss feed {}", rssFeed.toString());
        List<RssArticleDto> rssArticleDtoList = this.rssFeedFetcher.getRssArticlesFromFeedLink(rssFeed.getRssLink());
        log.info("Fetched {} rss Articles from {} publication",rssArticleDtoList.size(),rssFeed.getPublisher());
        for(RssArticleDto rssArticleDto : rssArticleDtoList) {
            String content = articleContentExtractor.extractArticleContent(rssFeed.getPublisher(), rssArticleDto.getLink());
            KafkaMessageBuilder kafkaMessage = KafkaMessageBuilder
                    .builder()
                    .publisher(rssFeed.getPublisher())
                    .title(rssArticleDto.getTitle())
                    .link(rssArticleDto.getLink())
                    .pubDate(rssArticleDto.getPubDate())
                    .content(content)
                    .guid(rssArticleDto.getGuidHash())
                    .build();
            kafkaIngestionService.ingest(kafkaMessage);
        }
    }
}

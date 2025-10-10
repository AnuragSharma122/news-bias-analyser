package com.example.news.rss_ingestion_service.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RssFeedDto {

    @JsonProperty("Publisher")
    private String publisher;

    @JsonProperty("Link")
    private String rssLink;

    @Override
    public String toString() {
        return "RssFeedDto{" +
                "publisher='" + publisher + '\'' +
                ", rssLink='" + rssLink + '\'' +
                '}';
    }
}

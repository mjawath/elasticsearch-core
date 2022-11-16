package com.techstart.elasticcore.indexer.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * Created by jawa on 11/4/2020.
 */
//@Configuration
@Lazy
public class ESRestClientConfig  extends AbstractElasticsearchConfiguration {

    @Value("${elasticserver.url}")
    private String elasticurl;

    @Lazy
//    @Bean
    public RestHighLevelClient  elasticsearchClient() {
        ClientConfiguration clientConfiguration
                = ClientConfiguration.builder()
                .connectedTo(elasticurl)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

//    @Lazy
//    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

}

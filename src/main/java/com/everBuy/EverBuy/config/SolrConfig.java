package com.everBuy.EverBuy.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {

    private static final String SOLR_URL = "http://localhost:8983/solr"; // Change if needed

    @Bean
    public SolrClient solrClient() {
        return new HttpSolrClient.Builder(SOLR_URL).build();
    }
}

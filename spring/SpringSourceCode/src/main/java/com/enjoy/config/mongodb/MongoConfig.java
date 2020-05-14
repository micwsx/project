package com.enjoy.config.mongodb;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.*;
import org.springframework.stereotype.Component;

@PropertySource("classpath:mongodb.properties")
@Component
public class MongoConfig {

    @Value("${mongo.hostPort}")
    private String hostPort;

    @Value("${mongo.dbname}")
    private String dbname;

    @Value("${mongo.connectionsPerHost}")
    private Integer connectionsPerHost;

    @Value("${mongo.threadsAllowedToBlockForConnectionMultiplier}")
    private Integer threadsAllowedToBlockForConnectionMultiplier;

    @Value("${mongo.connectTimeout}")
    private Integer connectTimeout;

    @Value("${mongo.maxWaitTime}")
    private Integer maxWaitTime;

    @Value("${mongo.socketKeepAlive}")
    private Boolean socketKeepAlive;

    @Value("${mongo.socketTimeout}")
    private Integer socketTimeout;

    @Bean
    public MongoOperations mongoTemplate() {
        MongoClientSettings mongoClientSetting= MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(hostPort))
                .build();
        MongoClient mongoClient = MongoClients.create(mongoClientSetting);
        MongoOperations mongoOps = new MongoTemplate(mongoClient,dbname);
        return mongoOps;
    }
}

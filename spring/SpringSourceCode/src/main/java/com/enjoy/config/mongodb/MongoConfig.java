package com.enjoy.config.mongodb;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.aspectj.weaver.tools.cache.SimpleCacheFactory;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.ArrayList;

@PropertySource("classpath:mongodb.properties")
@Component
public class MongoConfig {

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private Integer port;

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
    public MongoDbFactory mongoDbFactory() {
        SimpleMongoDbFactory simpleMongoDbFactory = null;
        try {
            MongoClientOptions mongoClientOptions= MongoClientOptions.builder()
                    .connectTimeout(connectTimeout)
                    .connectionsPerHost(connectionsPerHost)
                    .threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier)
                    .maxWaitTime(maxWaitTime)
                    .socketKeepAlive(socketKeepAlive)
                    .socketTimeout(socketTimeout)
                    .build();
            ServerAddress serverAddress=new ServerAddress(host, port);
            MongoClient mongoClient = new MongoClient(serverAddress,mongoClientOptions);
            simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClient,dbname);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return simpleMongoDbFactory;
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        MongoTemplate mongoTemplate=new MongoTemplate(mongoDbFactory);
        return  mongoTemplate;
    }
}

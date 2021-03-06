package com.localjobs.config;

import javax.sql.DataSource;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.orm.jpa.vendor.Database;

public interface DatasourceConfig {

    DataSource dataSource();

    MongoDbFactory mongoDbFactory() throws Exception;

    Database database();

    public abstract RedisConnectionFactory redisConnectionFactory();

}

package com.localjobs.config;

import javax.sql.DataSource;

import org.springframework.data.mongodb.MongoDbFactory;

public interface DatasourceConfig {

	DataSource dataSource();

	MongoDbFactory mongoDbFactory() throws Exception;

	
}

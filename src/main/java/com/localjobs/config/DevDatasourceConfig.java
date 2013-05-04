package com.localjobs.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.vendor.Database;

import com.mongodb.Mongo;

@Configuration
@Profile("dev")
public class DevDatasourceConfig implements DatasourceConfig{

	@Bean(destroyMethod = "shutdown")
	public DataSource dataSource() {
		EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();
		factory.setDatabaseName("localjobs");
		factory.setDatabaseType(EmbeddedDatabaseType.H2);
		return factory.getDatabase();
	}

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		Mongo mongo = new Mongo("localhost", 27017);
		String databaseName = "localjobs";
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo,
				databaseName);
		return mongoDbFactory;
	}

	@Override
	public Database database() {
		return Database.H2;
	}
}

package com.localjobs.config;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@ActiveProfiles("dev")
public class ApplicationConfigTest {

	@Inject
	private JdbcTemplate jdbcTemplate;
	
	@Inject
	private MongoTemplate mongoTemplate;
	
	
	@Test
	public void jdbcTemplateAndMongoTemplateShouldBeNotNull() {
		assertNotNull(jdbcTemplate);
		assertNotNull(mongoTemplate);
	}

}

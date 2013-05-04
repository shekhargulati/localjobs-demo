package com.localjobs.config;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@ActiveProfiles("dev")
public class ApplicationConfigTest {

	@PersistenceContext
	EntityManager entityManager;

	@Inject
	private MongoTemplate mongoTemplate;

	@Test
	public void entityManagerAndMongoTemplateShouldBeNotNull() {
		assertNotNull(entityManager);
		assertNotNull(mongoTemplate);
	}

}

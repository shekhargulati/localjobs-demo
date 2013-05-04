package com.localjobs.mongodb.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.localjobs.domain.Job;

@Repository
public interface JobRepository extends PagingAndSortingRepository<Job, String> {

	List<Job> findAll();
	
}

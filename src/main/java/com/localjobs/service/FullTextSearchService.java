package com.localjobs.service;

import java.util.Map;
import java.util.Set;

public interface FullTextSearchService {

	public void addToIndex(String documentId, Map<String, String> fields);
	
	public Set<String> search(String query);
}

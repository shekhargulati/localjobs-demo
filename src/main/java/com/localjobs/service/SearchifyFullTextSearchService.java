package com.localjobs.service;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.flaptor.indextank.apiclient.Index;
import com.flaptor.indextank.apiclient.IndexTankClient;
import com.flaptor.indextank.apiclient.IndexTankClient.Query;
import com.flaptor.indextank.apiclient.IndexTankClient.SearchResults;

@Service
public class SearchifyFullTextSearchService implements FullTextSearchService {

	private static final String API_URL = "http://:pBt1pstGOpxEd3@dxm6q.api.searchify.com";
	private static final String INDEX_NAME = "localjobs_index";

	private final IndexTankClient client;
	private final Index index;

	public SearchifyFullTextSearchService() {
		client = new IndexTankClient(API_URL);
		index = client.getIndex(INDEX_NAME);
	}

	@Override
	public void addToIndex(String documentId, Map<String, String> fields) {
		try {
			index.addDocument(documentId, fields);
		} catch (Exception e) {
			throw new RuntimeException(
					"Exception occured while adding document to index .. ", e);
		}
	}

	@Override
	public Set<String> search(String query) {
		Set<String> documentIds = new LinkedHashSet<String>();
		try {
			SearchResults results = index.search(Query.forString(query));
			System.out.println("Matches: " + results.matches);
			for (Map<String, Object> document : results.results) {
			    System.out.println("doc id: " + document.get("docid"));
			    documentIds.add((String)document.get("docid"));
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"Exception occured while searching for results with query "
							+ query, e);
		}
		return documentIds;
	}

}

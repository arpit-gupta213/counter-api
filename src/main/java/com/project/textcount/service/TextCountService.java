package com.project.textcount.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project.textcount.ro.SearchText;
import com.project.textcount.ro.TextSearchResult;

@Service
public class TextCountService {
	
	public TextSearchResult getTextSearchResult(SearchText searchText) throws IOException {
		Map<String, Integer> searchResult = new LinkedHashMap<>(searchText.getSearchText().size());
		for(String word : searchText.getSearchText()) {
			//Capitalizing to handle the case of passing capitalized string for search as used in the example API
			searchResult.put(StringUtils.capitalize(word), 0);
		}
		InputStream is = TextCountService.class.getResourceAsStream("/SampleParagraph");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String s= " ";
		String[] stringArray;
		while((s = br.readLine()) != null){
			s = s.replace(",", "").replace(".", "");
			stringArray = s.split(" ");
			for(String word : stringArray) {
				String searchString = StringUtils.capitalize(word);
				if(searchResult.containsKey(searchString)) {
					searchResult.put(searchString, searchResult.get(searchString) + 1);
				}	
			}
		}
		TextSearchResult result = new TextSearchResult();
		result.setCounts(searchResult);
		return result;
	}
	
	public List<List<String>> getTopNTexts(int n) throws IOException {
		Map<String, Integer> topNWords = new HashMap<>();
		InputStream is = TextCountService.class.getResourceAsStream("/SampleParagraph");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String s= " ";
		String[] stringArray;
		while((s = br.readLine()) != null){
			s = s.replace(",", "").replace(".", "");
			stringArray = s.split(" ");
			for(String word : stringArray) {
				topNWords.put(word.toLowerCase(), topNWords.getOrDefault(word.toLowerCase(), 0) + 1);
			}
		}
		List<Entry<String, Integer>> entryList = new ArrayList<>(topNWords.entrySet());
		Collections.sort(entryList, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
		return convertToCSVData(entryList.subList(0, n));	
	}

	private List<List<String>> convertToCSVData(List<Entry<String, Integer>> entryList) {
		List<List<String>> csvData = new ArrayList<>();
		for(Entry<String, Integer> entry : entryList) {
			csvData.add(Arrays.asList(entry.getKey(), String.valueOf(entry.getValue())));
		}
		return csvData;
	}

}

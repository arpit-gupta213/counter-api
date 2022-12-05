package com.project.textcount.ro;

import java.util.LinkedHashMap;
import java.util.Map;

public class TextSearchResult {
	
	Map<String, Integer> counts = new LinkedHashMap<>(10);

	public Map<String, Integer> getCounts() {
		return counts;
	}

	public void setCounts(Map<String, Integer> counts) {
		this.counts = counts;
	}

}

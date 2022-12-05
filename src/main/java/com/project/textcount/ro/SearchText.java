package com.project.textcount.ro;

import java.util.ArrayList;
import java.util.List;

public class SearchText {
	List<String> searchText = new ArrayList<>(10);

	public List<String> getSearchText() {
		return searchText;
	}

	public void setSearchText(List<String> searchText) {
		this.searchText = searchText;
	}

}

package com.project.textcount.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpHeaders;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.project.textcount.ro.SearchText;
import com.project.textcount.ro.TextSearchResult;
import com.project.textcount.service.TextCountService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/counter-api")
public class TextCountController {
	
	@Autowired
	TextCountService textCountService;

	
	@PostMapping(value = "/search", consumes = "application/json")
	public TextSearchResult getTextCount(@RequestBody SearchText searchText) {
		try {
			return textCountService.getTextSearchResult(searchText);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error finding the occurances for input strings", e);
		}
	}
	
	@GetMapping(value = "/top/{n}", produces = "text/csv")
	public void getTopNTexts(@PathVariable String n, HttpServletResponse response) {
		try {
			List<List<String>> csvData = textCountService.getTopNTexts(Integer.valueOf(n));
			response.setContentType("text/csv");
			CSVPrinter printer = new CSVPrinter(response.getWriter(), CSVFormat.INFORMIX_UNLOAD);
			for (List<String> words : csvData) {
				printer.printRecord(words.get(0), words.get(1));
			}
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error finding top N words", e);
		}
	}

}

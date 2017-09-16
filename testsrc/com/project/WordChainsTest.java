package com.project;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class WordChainsTest {
	
	private WordChains wordChains;


	@Before
	public void setUp() throws Exception {
		wordChains = new WordChains();
	}
	
	@Test
	public void testCountOccurrences() {
		int expected = 2;
		int value = WordChains.countOccurrences("cog", "dog");
		assertEquals("occured passed: ", expected, value);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCountOccurrencesNullPointerException() {
		int value = WordChains.countOccurrences(null, null);
	}
	
	@Test
	public void testSearchForMatch() {
		List<String> expected = new ArrayList<>();
		expected.add("aat");
		expected.add("bat");
		expected.add("dat");
		expected.add("eat");
		
		List<String> oneCallableResult = new ArrayList<>();
		oneCallableResult.add("aat");
		oneCallableResult.add("bat");
		oneCallableResult.add("dat");
		oneCallableResult.add("eat");		
		oneCallableResult.add("azt");
		oneCallableResult.add("bay");
		oneCallableResult.add("day");
		oneCallableResult.add("ett");
		
		String regex = ".at";
		String startWord = "cat";
		
		List<String> value = WordChains.searchForMatch(oneCallableResult, regex, startWord);
		
		assertArrayEquals(expected.toArray(), value.toArray());
		expected.clear();
		oneCallableResult.clear();
		
		expected.add("cot");
		expected.add("cbt");
		expected.add("cqt");
		expected.add("cet");
		
		oneCallableResult.add("zzz");
		oneCallableResult.add("cot");
		oneCallableResult.add("cbt");
		oneCallableResult.add("cqt");
		oneCallableResult.add("cet");		
		oneCallableResult.add("azt");
		oneCallableResult.add("bay");
		oneCallableResult.add("day");
		oneCallableResult.add("ett");
		
		regex = "c.t";
		
		value = WordChains.searchForMatch(oneCallableResult, regex, startWord);
		assertArrayEquals(expected.toArray(), value.toArray());
		
	}
	
	@Test(expected=NullPointerException.class)
	public void testSearchForMatchNullPointerException() {
		List<String> value = WordChains.searchForMatch(null, null, null);
	}
	
	@Test
	public void testGetWord() {
		String expected = "rode";		
		String endWord = "ruby";		
		Map<String, Integer> wordOccured = new HashMap<>();
		
		wordOccured.put("rode", 1);
		wordOccured.put("node", 0);
		wordOccured.put("mode", 0);
		wordOccured.put("lode", 0);		
		String value = WordChains.getWord(wordOccured, endWord);		
		assertEquals("get word based on max occurrences: ", expected, value);		
		wordOccured.clear();
		
		expected = "ruby";
		wordOccured.put("ruby", 4);
		wordOccured.put("rubs", 3);
		value = WordChains.getWord(wordOccured, endWord);		
		assertEquals("get word which all letter match:", expected, value);		
		wordOccured.clear();
		
		expected = "goad";
		endWord = "lead";
		wordOccured.put("goad", 2);
		wordOccured.put("geld", 2);
		wordOccured.put("lald", 2);
		value = WordChains.getWord(wordOccured, endWord);
		assertEquals("get word based on max num of match sequence of string to endWord", expected, value);
	}
	
	@Test(expected=NullPointerException.class)
	public void testGetWordNullPointerException() {
		String value = WordChains.getWord(null, null);	
	}
	

}

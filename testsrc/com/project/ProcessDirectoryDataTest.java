package com.project;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ProcessDirectoryDataTest {
	
	private ProcessDirectoryData processDirectoryData;
	
	@Before
	public void setUp() throws Exception {
		processDirectoryData = new ProcessDirectoryData();
	}
	
	@Test
	public void testCountTheOccurrences() {
		int expected = 2;
		int value = ProcessDirectoryData.countWordsOccurrences("cog", "dog");
		assertEquals("Test count word occurrences", expected, value);
		
		expected = 5;
		value = ProcessDirectoryData.countWordsOccurrences("zxcvb", "zxcvb");
		assertEquals("Test count word occurrences", expected, value);
		

	}
	
	@Test(expected = NullPointerException.class)
	public void testCountTheOccurrencesNullPointerException() {
		int value = ProcessDirectoryData.countWordsOccurrences(null, null);
	}
	
	@Test
	public void testMatchByRegex() {
		List<String> expected = new ArrayList<>();
		expected.add("aat");
		expected.add("bat");
		expected.add("dat");
		expected.add("eat");

		List<String> wordList = new ArrayList<>();
		wordList.add("aat");
		wordList.add("bat");
		wordList.add("dat");
		wordList.add("eat");
		wordList.add("azt");
		wordList.add("bay");
		wordList.add("day");
		wordList.add("ett");

		String regex = ".at";
		String startWord = "cat";

		List<String> value = ProcessDirectoryData.matchByRegex(wordList, regex, startWord);

		assertArrayEquals("Test arrays by regex .at", expected.toArray(), value.toArray());
		expected.clear();
		wordList.clear();

		expected.add("cot");
		expected.add("cbt");
		expected.add("cqt");
		expected.add("cet");

		wordList.add("zzz");
		wordList.add("cot");
		wordList.add("cbt");
		wordList.add("cqt");
		wordList.add("cet");
		wordList.add("azt");
		wordList.add("bay");
		wordList.add("day");
		wordList.add("ett");

		regex = "c.t";

		value = ProcessDirectoryData.matchByRegex(wordList, regex, startWord);
		assertArrayEquals("Test arrays by regex c.t",expected.toArray(), value.toArray());

	}
	
	@Test(expected = NullPointerException.class)
	public void testMatchByRegexNullPointerException() {
		List<String> value = ProcessDirectoryData.matchByRegex(null, null, null);
	}
	
	@Test
	public void testGetMatchingWord() {
		String expected = "rude";
		String endWord = "ruby";
		Map<String, Integer> wordOccured = new HashMap<>();

		wordOccured.put("rude", 2);
		wordOccured.put("rode", 1);
		wordOccured.put("node", 0);
		wordOccured.put("mode", 0);
		wordOccured.put("lode", 0);
		String value = null;
		value = ProcessDirectoryData.getMatchingWord(wordOccured, endWord);

		assertEquals("Test get word based on max occurrences", expected, value);
		wordOccured.clear();

		expected = "ruby";
		wordOccured.put("ruby", 4);
		wordOccured.put("rubs", 3);
		value = ProcessDirectoryData.getMatchingWord(wordOccured, endWord);

		assertEquals("Test get word which all letter match", expected, value);
		wordOccured.clear();

		expected = "goad";
		endWord = "lead";
		wordOccured.put("goad", 2);
		wordOccured.put("geld", 2);
		wordOccured.put("lald", 2);

		value = ProcessDirectoryData.getMatchingWord(wordOccured, endWord);

		assertEquals("Test get word based on max num of match sequence of string to endWord", expected, value);
	}
	
	@Test(expected = NullPointerException.class)
	public void testGetMatchingWordNullPointerException() {
		String value = null;
		value = ProcessDirectoryData.getMatchingWord(null, null);

	}

}

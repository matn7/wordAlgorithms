package com.project;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ProcessDataTest {
	
	private ProcessData processData;
	
	@Before
	public void setUp() throws Exception {
		processData = new ProcessData();
	}
	
	@Test
	public void testCountTheOccurrences() {
		int expected = 2;
		int value = ProcessData.countTheOccurrences("cog", "dog");
		assertEquals("occured passed: ", expected, value);
	}
	
	@Test(expected = NullPointerException.class)
	public void testCountTheOccurrencesNullPointerException() {
		int value = ProcessData.countTheOccurrences(null, null);
	}
	
	@Test
	public void testMatchByRegex() {
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

		List<String> value = ProcessData.matchByRegex(oneCallableResult, regex, startWord);

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

		value = ProcessData.matchByRegex(oneCallableResult, regex, startWord);
		assertArrayEquals(expected.toArray(), value.toArray());

	}
	
	@Test(expected = NullPointerException.class)
	public void testMatchByRegexNullPointerException() {
		List<String> value = ProcessData.matchByRegex(null, null, null);
	}
	
	@Test
	public void testGetMatchingWord() {
		String expected = "rode";
		String endWord = "ruby";
		Map<String, Integer> wordOccured = new HashMap<>();

		wordOccured.put("rode", 1);
		wordOccured.put("node", 0);
		wordOccured.put("mode", 0);
		wordOccured.put("lode", 0);
		String value = null;
		value = ProcessData.getMatchingWord(wordOccured, endWord);

		assertEquals("get word based on max occurrences: ", expected, value);
		wordOccured.clear();

		expected = "ruby";
		wordOccured.put("ruby", 4);
		wordOccured.put("rubs", 3);
		value = ProcessData.getMatchingWord(wordOccured, endWord);

		assertEquals("get word which all letter match:", expected, value);
		wordOccured.clear();

		expected = "goad";
		endWord = "lead";
		wordOccured.put("goad", 2);
		wordOccured.put("geld", 2);
		wordOccured.put("lald", 2);

		value = ProcessData.getMatchingWord(wordOccured, endWord);

		assertEquals("get word based on max num of match sequence of string to endWord", expected, value);
	}
	
	@Test(expected = NullPointerException.class)
	public void testGetMatchingWordNullPointerException() {
		String value = null;
		value = ProcessData.getMatchingWord(null, null);

	}
	

}

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
		int value = ProcessData.countTheOccurrences("cog", "dog");
		assertEquals("occured passed: ", expected, value);
	}


	
	@Test
	public void testSgn() {
		int value = 2;
		
		assertEquals(value, Math.abs(-2));
		//testSgn();
		
	}

}

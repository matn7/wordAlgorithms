package com.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FindWordChainTest {
	
	private FindWordChain findWordChain;
	
	@Before
	public void setUp() throws Exception {

		findWordChain = new FindWordChain();
	}
	
	@Test
	public void testFindChain() {
		List<String> expected = new ArrayList<>();
		expected.add("cat");
		expected.add("cot");
		expected.add("cog");
		expected.add("dog");
		
		List<String> directoryData = new ArrayList<>();
		directoryData.add("cab");
		directoryData.add("cad");
		directoryData.add("cat");
		directoryData.add("chi");
		directoryData.add("cir");
		directoryData.add("cot");
		directoryData.add("cog");
		directoryData.add("dog");
		directoryData.add("due");
		
		String startWord = "cat";
		String endWord = "dog";
		List<String> value = findWordChain.findChain(directoryData, startWord, endWord);
		assertArrayEquals("Test found word chain", expected.toArray(), value.toArray());		
	}
	
	@Test(expected = NullPointerException.class)
	public void testFindChainNullPointerException() {
		List<String> value = null;
		value = findWordChain.findChain(null, null, null);

	}
	
	@Test
	public void testNeighborWordDifference() {
		boolean value = findWordChain.neighborWordDifference("berm", "beam", "bdrm");
		Assert.assertFalse("Word differ with one word, positive case",value);
		
		value = findWordChain.neighborWordDifference("berm", "bzzm", "bdrm");
		Assert.assertTrue("Word differ with two words",value);
		
		value = findWordChain.neighborWordDifference("berm", "bzzz", "bdrm");
		Assert.assertTrue("Word differ with three words",value);
		
		value = findWordChain.neighborWordDifference("berm", "zzzz", "bdrm");
		Assert.assertTrue("Word differ with all words",value);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testNeighborWordDifferenceNullPointerException() {
		boolean value = false;
		value = findWordChain.neighborWordDifference(null, null, null);

	}

}

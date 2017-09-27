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
		assertArrayEquals("occured passed: ", expected.toArray(), value.toArray());		
	}
	
	@Test
	public void testNeighborWordDifference() {
		boolean value = findWordChain.neighborWordDifference("berm", "bearm", "bdrm");
		Assert.assertTrue(value);
	}
	
	@Test(expected = NullPointerException.class)
	public void testFindChainNullPointerException() {
		List<String> value = null;
		value = findWordChain.findChain(null, null, null);

	}

}

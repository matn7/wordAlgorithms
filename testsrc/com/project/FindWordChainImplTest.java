package com.project;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FindWordChainImplTest {
	
	private FindWordChainImpl findWordChainImpl;
	
	@Before
	public void setUp() throws Exception {
		findWordChainImpl = new FindWordChainImpl();
	}
	
	@Test
	public void testProcessDirectory() {
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
		List<String> value = FindWordChainImpl.processDirectory(directoryData, startWord, endWord);
		assertArrayEquals("Test result", expected.toArray(), value.toArray());
	}
	
	@Test(expected = NullPointerException.class)
	public void testPrepareDataPointerException() {
		FindWordChainImpl.processDirectory(null, null, null);

	}
	

}

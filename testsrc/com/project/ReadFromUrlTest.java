package com.project;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReadFromUrlTest {
	
	private ReadFromUrl readFromUrl;
	
	@Before
	public void setUp() throws Exception {
		readFromUrl = new ReadFromUrl();
	}
	
	@Test
	public void testAlpabeticalOrder() {
		String start = "dog";
		String end = "cat";
		boolean actual = readFromUrl.alpabeticalOrder(start, end);
		Assert.assertTrue(actual);
		
		start = "lead";
		end = "gold";
		actual = readFromUrl.alpabeticalOrder(start, end);
		Assert.assertTrue(actual);	
		
		start = "gold";
		end = "lead";
		actual = readFromUrl.alpabeticalOrder(start, end);
		Assert.assertFalse(actual);	
		
		start = "caba";
		end = "rabe";
		actual = readFromUrl.alpabeticalOrder(start, end);
		Assert.assertFalse(actual);	
	}
	
	@Test(expected = NullPointerException.class)
	public void testAlpabeticalOrderNullPointerException() {
		 readFromUrl.alpabeticalOrder(null, null);
	}

}

package com.project;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DownloadDataTest {
	
	private DownloadData downloadData;
	
	@Before
	public void setUp() throws Exception {
		downloadData = new DownloadData();
	}
	
	@Test
	public void testDownloadDirectory() {
		String url = "http://codekata.com/data/wordlist.txt";
		String startWord = "AAA";
		String endWord = "ABM";
		List<String> directoryData = DownloadData.downloadDirectory(url, startWord, endWord);
		
		Assert.assertNotNull("Test download directory", directoryData);
	}
	
	@Test(expected = NullPointerException.class)
	public void testDownloadDirectoryNullPointerException() {
		DownloadData.downloadDirectory(null, null, null);
	}

}

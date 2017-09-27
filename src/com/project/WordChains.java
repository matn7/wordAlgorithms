package com.project;

import java.util.*;

public class WordChains {

	public static void main(String[] args) {
		String[] startArr = { "cat", "gold", "code", "bdrm", "adam" , "caba"};
		String[] endArr = { "dog", "lead", "ruby", "ruby", "ruby", "rype" };

		String startWord = startArr[3];
		String endWord = endArr[3];
		String url = "http://codekata.com/data/wordlist.txt";
		
		List<String> directoryData = DownloadData.downloadDirectory(url, startWord, endWord);
		
		List<String> foundWordChainList = FindWordChainImpl.prepareData(directoryData, startWord, endWord);

		displayResult(foundWordChainList);
	}

	public static void displayResult(List<String> foundWordChain) {
		System.out.println("============================");
		System.out.println("Found word chain");
		System.out.println("============================");
		for (String word : foundWordChain) {
			System.out.println(word);
		}
	}


}

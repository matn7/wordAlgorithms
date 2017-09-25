package com.project;

import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

public class WordChains {

	private static List<String> directoryData;

	private static List<String> contentList;

	private static String searchedWord;

	private static volatile boolean resultFound;

	private static List<String> foundWordChainList;

	private static Map<String, Integer> wordOccurred;

	private static int countOccurencesValue;

	public static void main(String[] args) {
		String[] startArr = { "cat", "gold", "code", "bdrm", "adam" };
		String[] endArr = { "dog", "lead", "ruby", "ruby", "ruby" };

		String startWord = startArr[2];
		String endWord = endArr[2];

		contentList = new ArrayList<>();
		wordOccurred = new HashMap<>();
		foundWordChainList = new ArrayList<>();
		searchedWord = startWord;
		resultFound = false;
		foundWordChainList.add(startWord);
		int loopIteration = 0;
		String regex = null;

		directoryData = DownloadData.downloadDirectory(startWord, endWord);
		foundWordChainList = mainLoop(startWord, endWord, regex, loopIteration);

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

	public static List<String> mainLoop(String startWord, String endWord, String regex, int loopIteration) {

		System.out.println("START");

		while (resultFound != true) {
			for (int i = 0; i < endWord.length(); i++) {
				regex = searchedWord.replace(searchedWord.charAt(i), '.');
				contentList = ProcessData.matchByRegex(directoryData, regex, searchedWord);
				for (String result : contentList) {
					if (result.contains(endWord)) {
						foundWordChainList.add(endWord);
						resultFound = true;
						break;
					} else {
						countOccurencesValue = ProcessData.countTheOccurrences(result, endWord);
						wordOccurred.put(result, countOccurencesValue);
					}
				}
			}
			if (resultFound == true) {
				break;
			}

			try {
				searchedWord = ProcessData.getMatchingWord(wordOccurred, endWord);
			} catch (NoSuchElementException e) {
				System.out.println("============================");
				System.out.println(e.getMessage());
				break;

			}

			// append result to path List
			foundWordChainList.add(searchedWord);

			if (loopIteration > 0) {
				validateData(loopIteration, startWord);
			} else {
				loopIteration++;
			}
		}
		return foundWordChainList;
	}

	public static void validateData(int loopIteration, String startWord) {
		if (foundWordChainList.get(loopIteration).equals(foundWordChainList.get(loopIteration - 1))
				|| ProcessData.countTheOccurrences(foundWordChainList.get(loopIteration),
						foundWordChainList.get(loopIteration - 1)) != startWord.length() - 1) {
			searchedWord = startWord;
			directoryData.remove(directoryData.indexOf(foundWordChainList.get(loopIteration)));
			foundWordChainList.clear();
			contentList.clear();
			countOccurencesValue = 0;
			wordOccurred.clear();
			foundWordChainList.add(startWord);
			loopIteration = 1;
		} else {
			loopIteration++;
		}
	}

}

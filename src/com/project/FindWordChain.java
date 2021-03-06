package com.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

import com.google.common.base.Preconditions;

public class FindWordChain implements Callable<List<String>> {

	private List<String> directoryData;
	private String startWord;
	private String endWord;
	private List<String> foundWordChainList;

	public FindWordChain(List<String> directoryData, String startWord, String endWord) {
		this.directoryData = directoryData;
		this.startWord = startWord;
		this.endWord = endWord;
	}

	public FindWordChain() {

	}

	public List<String> findChain(List<String> directoryData, String startWord, String endWord) {
		Preconditions.checkNotNull(directoryData, "directoryData cannot be null");
		Preconditions.checkNotNull(startWord, "Start word cannot be null");
		Preconditions.checkNotNull(endWord, "End word cannot be null");

		System.out.println("============================");
		System.out.println("START");

		boolean resultFound = false;
		String searchedWord = startWord;
		int countOccurences = 0;
		int loopIteration = 0;
		String regex = null;

		String wordIndex = null;
		String wordPrevIndex = null;
		List<String> matchRegexList = new ArrayList<>();
		Map<String, Integer> wordOccurred = new HashMap<>();

		foundWordChainList = new ArrayList<>();
		foundWordChainList.add(startWord);
		while (resultFound != true) {
			for (int i = 0; i < endWord.length(); i++) {
				regex = searchedWord.replace(searchedWord.charAt(i), '.');
				matchRegexList = ProcessDirectoryData.matchByRegex(directoryData, regex, searchedWord);
				for (String result : matchRegexList) {
					if (result.contains(endWord)) {
						foundWordChainList.add(endWord);
						resultFound = true;
						break;
					} else {
						countOccurences = ProcessDirectoryData.countWordsOccurrences(result, endWord);
						wordOccurred.put(result, countOccurences);
					}
				}
			}
			if (resultFound == true) {
				break;
			}

			try {
				searchedWord = ProcessDirectoryData.getMatchingWord(wordOccurred, endWord);
			} catch (NoSuchElementException e) {
				System.out.println("============================");
				System.out.println(e.getMessage());
				break;

			}

			foundWordChainList.add(searchedWord);

			if (loopIteration > 0) {
				wordIndex = foundWordChainList.get(loopIteration);
				wordPrevIndex = foundWordChainList.get(loopIteration - 1);
				// Check for errors
				if (wordIndex.equals(wordPrevIndex)
						|| neighborWordDifference(wordIndex, wordPrevIndex, startWord)) {
					// Clean up
					searchedWord = startWord;
					directoryData.remove(directoryData.indexOf(foundWordChainList.get(loopIteration)));
					foundWordChainList.clear();
					matchRegexList.clear();
					countOccurences = 0;
					wordOccurred.clear();
					foundWordChainList.add(startWord);
					loopIteration = 1;
				} else {
					loopIteration++;
				}
			} else {
				loopIteration++;
			}
		}
		return foundWordChainList;
	}

	public boolean neighborWordDifference(String wordIndex, String wordPrevIndex, String startWord) {
		Preconditions.checkNotNull(wordIndex, "Current word cannot be null");
		Preconditions.checkNotNull(wordPrevIndex, "Previous word cannot be null");
		Preconditions.checkNotNull(startWord, "End word cannot be null");
		// Neighbor words must differ with one letter
		return ProcessDirectoryData.countWordsOccurrences(wordIndex, wordPrevIndex) != startWord.length() - 1;
	}

	@Override
	public List<String> call() throws Exception {
		findChain(directoryData, startWord, endWord);
		return foundWordChainList;
	}

}

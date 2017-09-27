package com.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

public class ProcessDirectoryData {	
	
	public static List<String> matchByRegex(List<String> directoryData, String regex, String startWord) {
		Preconditions.checkNotNull(directoryData, "List directoryData cannot be null");
		Preconditions.checkNotNull(regex, "Regex cannot be null");
		Preconditions.checkNotNull(startWord, "Start word cannot be null");
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		Matcher matcher = null;
		boolean matchFound = false;
		List<String> wordList = new ArrayList<>();
		for (String s : directoryData) {
			if (!s.equals(startWord)) {
				matcher = pattern.matcher(s);
				matchFound = matcher.matches();
				if (matchFound) {
					wordList.add(s);
				}
			}
		}
		return wordList;
	}
	
	public static String getMatchingWord(Map<String, Integer> wordOccured, String endWord) throws NoSuchElementException {
		Preconditions.checkNotNull(wordOccured, "Map wordOccured cannot be null");
		Preconditions.checkNotNull(endWord, "End word cannot be null");
		int maxTimeOccured = 0;
		String matchWord = null;
		Map<String, Integer> matchingResult = new HashMap<>();
		List<String> matchingResultSeqString = new ArrayList<>();
		int biggestSequence = 0;
		for (Map.Entry<String, Integer> pair : wordOccured.entrySet()) {
			if (maxTimeOccured < pair.getValue()) {
				maxTimeOccured = pair.getValue();
			}
		}
		String keyIfOneResult = null;
		for (Map.Entry<String, Integer> pair : wordOccured.entrySet()) {
			if (pair.getValue() == maxTimeOccured) {
				keyIfOneResult = pair.getKey();
				matchingResult.put(pair.getKey(), pair.getValue());
			}
		}
		if (matchingResult.size() == 1) {
			matchWord = keyIfOneResult;
		} else {
			int sequenceIndex = 0;
			List<Integer> biggestSequenceNumber = new ArrayList<>();
			boolean activateSequence = false;
			for (Map.Entry<String, Integer> pair : matchingResult.entrySet()) {
				for (int i = 0; i < endWord.length(); i++) {
					if (endWord.charAt(i) == pair.getKey().charAt(i)) {
						sequenceIndex++;
						activateSequence = true;
					} else {
						if (i >= 1) {
							if (activateSequence) {
								sequenceIndex--;
							}
						}
					}
				}
				biggestSequenceNumber.add(sequenceIndex);
				matchingResultSeqString.add(pair.getKey());
				sequenceIndex = 0;
				activateSequence = false;
			}
			if (biggestSequenceNumber.size() != 0) {
				biggestSequence = Collections.max(biggestSequenceNumber);
			} else {
				throw new NoSuchElementException("Do not found path sorry");
			}

			int index = biggestSequenceNumber.indexOf(biggestSequence);
			matchWord = matchingResultSeqString.get(index);
		}
		return matchWord;
	}
	
	public static Integer countWordsOccurrences(String word, String endWord) {
		Preconditions.checkNotNull(word, "Word cannot be null");
		Preconditions.checkNotNull(endWord, "End word cannot be null");
		int wordOccurred = 0;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == endWord.charAt(i)) {
				wordOccurred++;
			}
		}
		return wordOccurred;
	}

}

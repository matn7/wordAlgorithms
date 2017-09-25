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

public class ProcessData {
	
	public static List<String> matchByRegex(List<String> directoryData, String regex, String startWord) {
		Preconditions.checkNotNull(directoryData, "List oneCallableResult cannot be null");
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
		int max = 0;
		String matchWord = null;
		Map<String, Integer> wordHelper = new HashMap<>();
		List<Integer> wordHelperSeqInt = new ArrayList<>();
		List<String> wordHelperSeqString = new ArrayList<>();
		int biggest = 0;
		for (Map.Entry<String, Integer> pair : wordOccured.entrySet()) {
			if (max < pair.getValue()) {
				max = pair.getValue();
			}
		}
		String keyIfOneResult = null;
		for (Map.Entry<String, Integer> pair : wordOccured.entrySet()) {
			if (pair.getValue() == max) {
				keyIfOneResult = pair.getKey();
				wordHelper.put(pair.getKey(), pair.getValue());
			}
		}
		if (wordHelper.size() == 1) {
			matchWord = keyIfOneResult;
		} else {
			// Find sequence of repeated occurrences
			int sequenceIndex = 0;
			List<Integer> biggestNumber = new ArrayList<>();
			boolean activateSequence = false;
			for (Map.Entry<String, Integer> pair : wordHelper.entrySet()) {
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
				biggestNumber.add(sequenceIndex);
				wordHelperSeqInt.add(sequenceIndex);
				wordHelperSeqString.add(pair.getKey());
				sequenceIndex = 0;
				activateSequence = false;
			}
			if (biggestNumber.size() != 0) {
				biggest = Collections.max(biggestNumber);
			} else {
				// If no match values found throw NoSuchElementException.
				// Program ends.
				throw new NoSuchElementException("Do not found path sorry");
			}

			int index = wordHelperSeqInt.indexOf(biggest);
			matchWord = wordHelperSeqString.get(index);
		}
		return matchWord;
	}
	
	public static Integer countTheOccurrences(String word, String endWord) {
		Preconditions.checkNotNull(word, "Word cannot be null");
		Preconditions.checkNotNull(endWord, "End word cannot be null");
		int k = 0;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == endWord.charAt(i)) {
				k++;
			}
		}
		return k;
	}

}

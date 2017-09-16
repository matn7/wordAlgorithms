package com.project;

import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

public class WordChains {
	   private static List<String> contentList;

	    private static String searchedWord;

	    private static volatile boolean found;

	    private static List<String> path;

	    private static Map<String, Integer> wordOccured;

	    private static int countOccurencesValue;

	    public static void main(String[] args) {

	        String[] startArr = {"cat", "gold", "code", "adam"};
	        String[] endArr = {"dog", "lead", "ruby", "ruby"};

	        String startWord = startArr[3];
	        String endWord = endArr[3];

	        // Download resource as List<String>
	        Callable<List<String>> readResources = new ReadFromURL("http://codekata.com/data/wordlist.txt", startWord.length(), startWord, endWord);

	        // Create instance of ExecutorService, use all available processors
	        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	        List<Future<List<String>>> listOfFutures = new ArrayList<>();

	        // Submit Callable to ExecutorService, get Future as a result
	        Future<List<String>> oneFuture = executorService.submit(readResources);
	        listOfFutures.add(oneFuture);

	        List<String> oneCallableResult = null;
	        try {
	        	// Get retrieved result and and output to console
	            oneCallableResult = oneFuture.get();
	            for (String result : oneCallableResult) {
	                System.out.println(result);
	            }

	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } catch (ExecutionException e) {
	            e.printStackTrace();
	        }
	        
	        executorService.shutdown();
	        try {
	            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        String regex = null;
	        System.out.println();

	        System.out.println("START");
	        contentList = new ArrayList<>();

	        // Map which check of occurrences of word. For example cog, has 2 match letter from dog.
	        // Output <"cog", 2>
	        wordOccured = new HashMap<>();
	        
	        // Result of algorithm is List of word.
	        path = new ArrayList<>();
	        
	        // Start word, first element in path
	        searchedWord = startWord;
	        found = false;
	        path.add(startWord);
	        int k = 0;

	        while (found != true) {
	            for (int i = 0; i < endWord.length(); i++) {
	            	// Requirements was that we can traverse through list by changing just one letter in word.
	            	// For example start word is cat: check regex from retrieved list match [.at,c.t,ca.]
	                regex = searchedWord.replace(searchedWord.charAt(i), '.');

	                // Word List from Dictionary that match our regex
	                contentList = searchForMatch(oneCallableResult, regex, searchedWord);

	                // Loop through contentList to filter output that match requirements.
	                for (String result : contentList) {
	                    if (result.contains(endWord)) {
	                    	// If result contains word break out loop, program is done
	                        path.add(endWord);
	                        found = true;
	                        break;
	                    } else {
	                    	// Count occurrences how many letters from result match endWord
	                        countOccurencesValue = countOccurrences(result, endWord);
	                        // Put result to map
	                        wordOccured.put(result, countOccurencesValue);
	                    }
	                }
	            }
	            // In case of word found break out while loop
	            if (found == true) {
	                break;
	            }
	            
	            // Use wordOccured map to get word that match requirements
	            searchedWord = getWord(wordOccured, endWord);
	            
	            // append result to path List
	            path.add(searchedWord);
	            
	            if (k > 0) {
	            	if (path.get(k).equals(path.get(k-1))) {
	            		// path values are the same than we detected infinite loop.
	            		// Remove this element from oneCallableResult list, and start process again.
	            		searchedWord = startWord;
	            		oneCallableResult.remove(oneCallableResult.indexOf(path.get(k)));
	            		// clear all collections and variables
	            		path.clear();
	            		contentList.clear();
	            		countOccurencesValue = 0;
	            		wordOccured.clear();
	            		// new path List add first element
	            		path.add(startWord);
	            		k = 0;
	            	} else {
	            		k++;
	            	}
	            } else {
	            	k++;
	            }
	        }


	        System.out.println("============================");
	        System.out.println("Found Path");
	        for (String word : path) {

	            System.out.println(word);
	        }

	    }

	    public static List<String> searchForMatch(List<String> oneCallableResult, String regex, String startWord) {
	    	Preconditions.checkNotNull(oneCallableResult, "List oneCallableResult cannot be null");
	    	Preconditions.checkNotNull(regex, "Regex cannot be null");
	    	Preconditions.checkNotNull(startWord, "Start word cannot be null");
	    	Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
	        Matcher matcher = null;
	        boolean matchFound = false;
	        List<String> wordList = new ArrayList<>();
	        // Get word based on regex, ignore startWord.
	        for (String s : oneCallableResult) {
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

	    public static String getWord(Map<String, Integer> wordOccured, String endWord) {
	    	Preconditions.checkNotNull(wordOccured, "Map wordOccured cannot be null");
	    	Preconditions.checkNotNull(endWord, "End word cannot be null");
	    	// Main part of program find match word of each step.
	    	// First check based on word occurred.
	    	// If only one word has the biggest number of match letter with endWord pick that one.
	    	// Otherwise if two or more words has the same number of occurrences
	    	// pick that one with has word in sequence:
	    	// match based on occurrences [geld, goad], end word [lead].
	    	// geld has two match letters (e and d) but not in sequence (index 1 and 3) _e_d [lead]
	    	// goad has two match letters (a and d) but in sequence (index 2 and 3) __ad [lead]
	    	// Algorithm choose goad.
	        int max = 0; 
	        String matchWord = null; 
	        Map<String, Integer> wordHelper = new HashMap<>(); 
	        Map<Integer, String> wordHelperSeq = new HashMap<>();
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
	                wordHelperSeq.put(sequenceIndex, pair.getKey());
	                sequenceIndex = 0;
	                activateSequence = false;
	            }
	            int biggest = Collections.max(biggestNumber);
	            matchWord = wordHelperSeq.get(biggest);
	        }
	        return matchWord;
	    }

	    public static Integer countOccurrences(String word, String endWord) {
	    	Preconditions.checkNotNull(word, "Word cannot be null");
	    	Preconditions.checkNotNull(endWord, "End word cannot be null");
	        int k = 0;
	        // Loop to count number of occured of word in endWord, for example 
	        // cog has 2 the same letters (and on he same index) as dog.
	        for (int i = 0; i < word.length(); i++) {
	            if (word.charAt(i) == endWord.charAt(i)) {
	                k++;
	            }
	        }
	        return k;
	    }
}


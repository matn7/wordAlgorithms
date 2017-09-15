package com.project;

import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordChains {
	   private static List<String> contentList;

	    private static String searchedWord;

	    private static volatile boolean found;

	    private static List<String> path;

	    private static Map<String, Integer> wordOccured;

	    private static int countOccurencesValue;

	    public static void main(String[] args) {

	        String[] startArr = {"cat", "gold", "code"};
	        String[] endArr = {"dog", "lead", "ruby"};

	        String startWord = startArr[1];
	        String endWord = endArr[1];

	        Callable<List<String>> readResources = new ReadFromURL("http://codekata.com/data/wordlist.txt", startWord.length(), startWord, endWord);

	        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	        List<Future<List<String>>> listOfFutures = new ArrayList<>();

	        Future<List<String>> oneFuture = executorService.submit(readResources);
	        listOfFutures.add(oneFuture);

	        List<String> oneCallableResult = null;
	        try {
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

	        wordOccured = new HashMap<>();
	        path = new ArrayList<>();
	        searchedWord = startWord;
	        found = false;
	        path.add(startWord);

	        while (found != true) {
	            for (int i = 0; i < endWord.length(); i++) {
	                regex = searchedWord.replace(searchedWord.charAt(i), '.');

	                contentList = searchForMatch(oneCallableResult, regex, searchedWord);

	                for (String result : contentList) {
	                    if (result.contains(endWord)) {
	                        path.add(endWord);
	                        found = true;
	                        break;
	                    } else {
	                        countOccurencesValue = countOccurrences(result, endWord);
	                        wordOccured.put(result, countOccurencesValue);
	                    }
	                }
	            }

	            searchedWord = getWord(wordOccured, endWord);

	            if (found == true) {
	                break;
	            }
	            System.out.println("Searched Word");
	            System.out.println(searchedWord);
	            path.add(searchedWord);
	        }


	        System.out.println("============================");
	        System.out.println("Found Path");
	        for (String word : path) {

	            System.out.println(word);
	        }

	    }

	    public static List<String> searchForMatch(List<String> oneCallableResult, String regex, String startWord) {
	        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
	        Matcher matcher = null;
	        boolean matchFound = false;
	        List<String> wordList = new ArrayList<>();
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
	        int k = 0;
	        for (int i = 0; i < word.length(); i++) {
	            if (word.charAt(i) == endWord.charAt(i)) {
	                k++;
	            }
	        }
	        return k;
	    }
}


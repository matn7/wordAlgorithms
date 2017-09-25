package com.project;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class DownloadData {
	private static List<String> directoryData;

	public static List<String> downloadDirectory(String startWord, String endWord) {
		Callable<List<String>> readResources = new ReadFromURL("http://codekata.com/data/wordlist.txt",
				startWord.length(), startWord, endWord);

		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		List<Future<List<String>>> listOfFutures = new ArrayList<>();

		Future<List<String>> oneFuture = executorService.submit(readResources);
		listOfFutures.add(oneFuture);

		try {
			// Get retrieved result and and output to console
			directoryData = oneFuture.get();
			for (String result : directoryData) {
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

		return directoryData;
	}
}

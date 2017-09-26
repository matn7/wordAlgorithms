package com.project;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FindWordChain {
	
	private static List<String> wordChainDataList;
	
	public static List<String> prepareData(List<String> directoryData, String startWord, String endWord) {
		
		Callable<List<String>> processWordChain = new FindWordChainCallable(directoryData, startWord, endWord);
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		List<Future<List<String>>> listOfFutures = new ArrayList<>();

		Future<List<String>> oneFuture = executorService.submit(processWordChain);
		listOfFutures.add(oneFuture);
		
		try {
			wordChainDataList = oneFuture.get();
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
		
		return wordChainDataList;
	}

}

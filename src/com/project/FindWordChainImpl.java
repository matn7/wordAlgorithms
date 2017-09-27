package com.project;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

public class FindWordChainImpl {
	
	private static List<String> wordChainDataList;
	
	private static final int processors = Runtime.getRuntime().availableProcessors();
	
	public static List<String> prepareData(List<String> directoryData, String startWord, String endWord) {
		Preconditions.checkNotNull(directoryData, "directoryData cannot be null");
		Preconditions.checkNotNull(startWord, "Start word cannot be null");
		Preconditions.checkNotNull(endWord, "End word cannot be null");
		
		Callable<List<String>> findWordChain = new FindWordChain(directoryData, startWord, endWord);
		ExecutorService executorService = Executors.newFixedThreadPool(processors);

		Future<List<String>> oneFuture = executorService.submit(findWordChain);
		
		try {
			wordChainDataList = oneFuture.get();
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

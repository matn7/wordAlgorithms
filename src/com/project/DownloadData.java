package com.project;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

public class DownloadData {
	private static List<String> directoryData;
	private static final int processors = Runtime.getRuntime().availableProcessors();

	public static List<String> downloadDirectory(String url, String startWord, String endWord) {
		Preconditions.checkNotNull(url, "Url cannot be null");
		Preconditions.checkNotNull(startWord, "Start word cannot be null");
		Preconditions.checkNotNull(endWord, "End word cannot be null");
		int stringLength = startWord.length();
		Callable<List<String>> readResources = new ReadFromUrl(url, stringLength, startWord, endWord);

		ExecutorService executorService = Executors.newFixedThreadPool(processors);
		CompletionService<List<String>> completionService = new ExecutorCompletionService<>(executorService);

		//Future<List<String>> resultList = executorService.submit(readResources);
		completionService.submit(readResources);

		try {
			//directoryData = resultList.get();
			directoryData = completionService.take().get();
			System.out.println("Directory content");
			System.out.println("============================");
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

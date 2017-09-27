package com.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class ReadFromUrl implements Callable<List<String>> {

	private String stringUrl;
	private Integer stringLength;
	private String startString;
	private String endString;
	List<String> lista;

	public ReadFromUrl(String stringUrl, Integer stringLength, String startString, String endString) {
		this.stringUrl = stringUrl;
		this.stringLength = stringLength;
		this.startString = startString;
		this.endString = endString;
	}
	
	public ReadFromUrl() {
		
	}
	
	public boolean alpabeticalOrder(String startWord, String endWord) {
		String[] words = {startWord, endWord};
		boolean positionChanged = false;
		Arrays.sort(words);
		if (startWord == words[0]) {
			return positionChanged;
		} else {
			positionChanged = true;
			return positionChanged;
		}	
	}

	@Override
	public List<String> call() throws Exception {
		lista = new ArrayList<>();
		URL url = new URL(stringUrl);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
			String line = br.readLine();

			while (line != null) {
				if (line.length() == stringLength) {
					if (alpabeticalOrder(startString, endString)) {
						// Reverse order
						if (((int) line.charAt(0) >= (int) endString.charAt(0)
								&& (int) line.charAt(0) <= (int) startString.charAt(0))) {
							lista.add(line);
						}
					} else if (((int) line.charAt(0) >= (int) startString.charAt(0)
							&& (int) line.charAt(0) <= (int) endString.charAt(0))) {
						lista.add(line);
					}
				}
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

/*		if (alpabeticalOrder(startString, endString)) {
			Collections.sort(lista, Collections.reverseOrder());
			return lista;
		}*/

		return lista;
	}
}

package com.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ReadFromURL implements Callable<List<String>> {

    private String stringUrl;
    private Integer stringLength;
    private String startString;
    private String endString;
    List<String> lista;


    public ReadFromURL(String stringUrl, Integer stringLength, String startString, String endString) {
        this.stringUrl = stringUrl;
        this.stringLength = stringLength;
        this.startString = startString;
        this.endString = endString;
    }

    @Override
    public List<String> call() throws Exception {
        lista = new ArrayList<>();
        URL url = new URL(stringUrl);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line = br.readLine();

            while (line != null) {
                if (line.length() == stringLength) {
                		// Retrieve only data which match first char of start word and first character of end word.
                        if (((int)line.charAt(0) >= (int)startString.charAt(0) && (int) line.charAt(0) <= (int) endString.charAt(0))) {
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
        return lista;
    }
}

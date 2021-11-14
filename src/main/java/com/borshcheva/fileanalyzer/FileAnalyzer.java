package com.borshcheva.fileanalizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileAnalyzer {
    private static final Pattern SPLIT_SENTENCES_PATTERN = Pattern.compile("((?<=[.?!]))");

    public static void main(String[] args) throws IOException {

        String file = args[0];
        String word = args[1];

        String content = getContent(file);
        List<String> sentences = getSplitSentences(content);

        int count = getSearchWordCount(content, word);
        List<String > searchSentences = getSearchedSentences(sentences, word);

        System.out.println("Word " + word + " occurs " + count + " times");

        for (String sentence : searchSentences){
            System.out.println(sentence);
        }

    }

    static List<String> getSearchedSentences(List<String> sentences, String word) {
        List<String> searchedSentences = new ArrayList<>();
        for (String sentence : sentences) {
            if (sentence.contains(word)) {
                searchedSentences.add(sentence);
            }
        }
        return searchedSentences;
    }

    static String getContent(String path) throws IOException {
        File pathToFile = new File(path);
        if (pathToFile.exists()) {
            FileInputStream inputStream = new FileInputStream(pathToFile);
            int fileLength = (int) pathToFile.length();
            byte[] contentArray = new byte[fileLength];
            inputStream.read(contentArray);
            inputStream.close();
            return new String(contentArray);
        } else {
            throw new FileNotFoundException("File doesn`t exist!");
        }

    }

    static int getSearchWordCount(String content, String word) {
        Pattern pattern = Pattern.compile("(^|\\s+)"+ word +"(\\s+|[.!?,]|$)");
        Matcher matcher = pattern.matcher(content);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    static List<String> getSplitSentences(String content) {
        String[] sentences = SPLIT_SENTENCES_PATTERN.split(content);
        List<String> trimmedSentences = new ArrayList<>();

        for (String sentence : sentences) {
            trimmedSentences.add(sentence.trim());
        }

        return trimmedSentences;
    }
}


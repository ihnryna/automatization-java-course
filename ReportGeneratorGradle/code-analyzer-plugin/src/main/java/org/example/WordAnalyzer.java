package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

public class WordAnalyzer {
    private static final Pattern COMMENT_PATTERN = Pattern.compile("//.*");
    Map<String, Integer> wordsWithFreq = new HashMap<>();


    public List<String> getAllWords(File root) throws IOException {
        if (!root.getName().equals("clean-code-plugin")) {
            scanDirectory(root);
        }

        return makeText(wordsWithFreq);
    }

    private List<String> makeText(Map<String, Integer> wordsWithFreq) {
        List<String> text = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordsWithFreq.entrySet()) {
            String word = entry.getKey();
            int freq = entry.getValue();
            text.add(word + " : " + freq);
        }
        return text;
    }

    private void scanDirectory(File directory) {
        if (directory.exists() && directory.isDirectory() && !directory.getName().equals("clean-code-plugin")) {

            File[] files = directory.listFiles();
            if (files == null) return;


            for (File file : files) {
                if (file.isDirectory()) {
                    scanDirectory(file);
                } else if (file.getName().endsWith(".java")) {
                    try {
                        scanFile(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void scanFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        StringBuilder allCode = new StringBuilder();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            allCode.append(line).append("\n");
        }

        List<String> comments = new ArrayList<>(findMatches(String.valueOf(allCode)));

        for (String comment : comments) {
            String cleaned = comment.toLowerCase().replaceAll("[^a-z]", " ");
            for (String word : cleaned.split("\\s+")) {
                if(!word.isEmpty()){
                    wordsWithFreq.put(word, wordsWithFreq.getOrDefault(word, 0) + 1);
                }
            }
        }
        scanner.close();
    }


    private List<String> findMatches(String input) {
        List<String> matches = new ArrayList<>();
        Matcher matcher = WordAnalyzer.COMMENT_PATTERN.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }
}

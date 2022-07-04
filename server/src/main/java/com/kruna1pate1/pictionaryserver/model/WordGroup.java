package com.kruna1pate1.pictionaryserver.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by KRUNAL on 14-05-2022
 */
@Data
public class WordGroup {

    private String[] wordArray;

    private String selectedWord;

    @Setter(AccessLevel.PRIVATE)
    private String hint;

    public WordGroup(List<String> wordList) {

        this.wordArray = new String[3];
        Random r = new Random();

        for (int i = 0; i < 3; i++) {
            wordArray[i] = wordList.get(r.nextInt(wordArray.length));
        }
    }

    public String setSelectedWord(int pos) {
        this.selectedWord = wordArray[pos];

        int len = (int) (selectedWord.length() / 3);
        Set<Integer> ranSet = new HashSet<>(len);
        StringBuilder sb = new StringBuilder();
        Random r = new Random();

        while (ranSet.size() < len) {
            ranSet.add(r.nextInt(selectedWord.length()));
        }

        for (int i = 0; i < selectedWord.length(); i++) {
            if (ranSet.contains(i)) {
                sb.append(selectedWord.charAt(i));
            } else {
                sb.append("_");
            }
        }

        this.hint = sb.toString();
        return selectedWord;
    }
}

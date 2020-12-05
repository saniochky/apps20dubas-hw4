package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int counter = 0;

        for (String string: strings) {
            String[] words = string.split(" ");

            for (String word: words) {
                if (word.length() > 1) {
                    trie.add(new Tuple(word, word.length()));
                    counter++;
                }
            }
        }

        return counter;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() > 1) {
            return trie.wordsWithPrefix(pref);
        }

        return null;
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() > 1) {
            Iterable<String> words = trie.wordsWithPrefix(pref);
            ArrayList<String> arrList = new ArrayList<>();
            ArrayList<String> finalList = new ArrayList<>();

            for (String word: words) {
                arrList.add(word);
            }

            arrList.sort(Comparator.comparingInt(String::length));
            int k_copy = 0;
            int lastWordSize = 2;

            if (arrList.size() != 0) {
                lastWordSize = arrList.get(0).length();
            }

            for (String word: arrList) {
                if (lastWordSize != word.length()) {
                    lastWordSize = word.length();
                    k_copy++;
                }

                if (k_copy == k) {
                    break;
                }

                finalList.add(word);
            }

            return finalList;
        }

        return null;
    }

    public int size() {
        return trie.size();
    }
}

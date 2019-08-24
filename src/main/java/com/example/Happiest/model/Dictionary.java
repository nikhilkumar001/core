/*
 * Class: Dictionary
 *
 * Created on 24-08-2019
 *
 * (c) Copyright Lam Research Corporation, unpublished work, created 2019
 * All use, disclosure, and/or reproduction of this material is prohibited
 * unless authorized in writing.  All Rights Reserved.
 * Rights in this program belong to:
 * Lam Research Corporation
 * 4000 N. First Street
 * San Jose, CA
 */
package com.example.Happiest.model;

import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Log4j
public class Dictionary {
    public class TrieNode {
        private Map<Character, TrieNode> children;
        boolean isEnd;

        private TrieNode() {
            children = new HashMap<>();
            isEnd = false;
        }
    }

    private void addWord(String s) {
        TrieNode current = root;
        for (int i = 0; i < s.length(); i++) {
            current.children.putIfAbsent(s.charAt(i), new TrieNode());
            current = current.children.get(s.charAt(i));
            current.isEnd = true;
        }
    }

    private boolean searchWord(String s) {
        TrieNode current = root;
        for (int i = 0; i < s.length(); i++) {
            current = current.children.get(s.charAt(i));
            if (current == null)
                return false;
        }
        return current.isEnd;
    }

    private void buildDictionary(BufferedReader br) {
            br.
                    lines()
                    .map(line -> line.split("\\s+"))
                    .flatMap(Arrays::stream)
                    .forEach(this::addWord);

    }

    private final TrieNode root;

    private Dictionary() {
        root = new TrieNode();
    }

    public static void main(String[] args) throws IOException {
        Dictionary dictionary = new Dictionary();

        try ( BufferedReader bufferedReader = new BufferedReader(new FileReader(Objects.requireNonNull(dictionary.getClass().getClassLoader().getResource("words.txt")).getFile()))) {
            dictionary.buildDictionary(bufferedReader);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Assert.assertTrue(dictionary.searchWord("india"));
        Assert.assertTrue(dictionary.searchWord("apple"));
        Assert.assertTrue(dictionary.searchWord("shine"));
        Assert.assertFalse(dictionary.searchWord("12457"));

        dictionary.addWord("12457");

        Assert.assertTrue(dictionary.searchWord("12457"));
    }

}


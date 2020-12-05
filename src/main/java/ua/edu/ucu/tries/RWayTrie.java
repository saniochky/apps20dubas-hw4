package ua.edu.ucu.tries;


import queue.Queue;

import java.util.ArrayList;

public class RWayTrie implements Trie {

    private static final int R = 256;
    private Node root = null;

    private static class Node {
        public Object value;
        public Node[] next = new Node[R];
    }

    @Override
    public void add(Tuple t) {
        if (!this.contains(t.term)) {
            root = add(root, t.term, t.weight, 0);
        }
    }

    private Node add(Node x, String key, int val, int d) {
        if (x == null) {
            x = new Node();
        }

        if (d == key.length()) {
            x.value = val;
            return x;
        }

        char c = key.charAt(d);
        x.next[c] = add(x.next[c], key, val, ++d);
        return x;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }

        if (d == key.length()) {
            return x;
        }

        char c = key.charAt(d);
        return get(x.next[c], key, ++d);
    }

    @Override
    public boolean contains(String word) {
        if (word.length() > 0 && root != null) {
            Node curNode = root;

            for (char letter: word.toCharArray()) {
                if (curNode.next[letter] == null) {
                    return false;
                }

                curNode = curNode.next[letter];
            }

            return curNode.value != null;
        }

        return false;
    }

    @Override
    public boolean delete(String word) {
        if (this.contains(word)) {
            root = delete(root, word, 0);

            return true;
        }

        return false;
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) {
            return null;
        }

        if (d == key.length()) {
            x.value = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, ++d);
        }

        if (x.value != null) {
            return x;
        }

        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) {
                return x;
            }
        }

        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        ArrayList<String> list = new ArrayList<>();
        collect(get(root, s, 0), s, q);

        for (int i = 0; i < size(); i++) {
            list.add((String) q.dequeue());
        }

        return list;
    }

    private void collect(Node x, String s, Queue q) {
        if (x == null) {
            return;
        }

        if (x.value != null) {
            q.enqueue(s);
        }

        for (char c = 0; c < R; c++) {
            collect(x.next[c], s + c, q);
        }
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }

        int size = 0;

        if (x.value != null) {
            size += 1;
        }

        for (char c = 0; c < R; c++) {
            size += size(x.next[c]);
        }

        return size;
    }

}

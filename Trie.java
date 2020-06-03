import java.util.*;

public class Trie {
    Node root;

    public Trie() {
        root = new Node('$');
    }

    void delete(String word) {
        if (!search(word))
            return;

        char[] letters = word.toCharArray();
        Node curr = root;

        for (int i = 0; i < letters.length; i++) {
            char c = letters[i];
            curr = curr.children[c - 'a'];
        }

        if (curr.lastLetterCount > 0) {
            curr.lastLetterCount--;
        }

        for (int i = letters.length; i >= 0; i--) {
            char c = letters[i];
            curr.children[c - 'a'].count--;
            curr = curr.parent;
        }
    }

    boolean search(String word) {
        Node curr = root;
        char[] letters = word.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            char c = letters[i];
            if (curr.children[c - 'a'] == null || curr.children[c - 'a'].count == 0)
                return false;
            curr = curr.children[c - 'a'];
        }
        if (curr.lastLetterCount == 0)
            return false;
        return true;
    }

    boolean startsWith(String word) {
        Node curr = root;
        char[] letters = word.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            char c = letters[i];
            if (curr.children[c - 'a'] == null || curr.children[c - 'a'].count == 0)
                return false;
            curr = curr.children[c - 'a'];
        }
        return true;
    }

    void insert(String word) {
        Node curr = root;
        char[] letters = word.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            char c = letters[i];
            if (curr.children[c - 'a'] != null) {
                curr = curr.children[c - 'a'];
                curr.count++;
            } else {
                Node n = new Node(c);
                curr.children[c - 'a'] = n;
                n.parent = curr;
                curr = n;
            }
        }
        curr.lastLetterCount++;
    }

}

class Node {
    char c;
    Node parent;
    int lastLetterCount;
    int count = 1;
    Node[] children;

    public Node(char c) {
        this.children = new Node[26];
        this.c = c;
        this.lastLetterCount = 0;
    }
}
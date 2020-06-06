
import java.util.*;

public class KMP {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String s = input.next();
        String pat = input.next();
        KMPObj kmp = new KMPObj(pat);
        System.out.println(kmp.match(s).size());

    }
}

class KMPObj {
    int[] failureFunction;
    char[] pattern;

    public KMPObj(String pattern) {
        this.pattern = pattern.toCharArray();
        buildFailureFunction();
    }

    public void buildFailureFunction() {
        failureFunction = new int[pattern.length + 1];
        int j = 0;
        int i = 1;
        while (i < pattern.length) {
            if (pattern[j] == pattern[i]) {
                failureFunction[i] = j + 1;
                i++;
                j++;
            } else if (j > 0)
                j = failureFunction[j - 1];
            else
                i++;

        }
    }

    public List<Integer> match(String text) {
        List<Integer> list = new ArrayList<>();
        char[] letters = text.toCharArray();

        int j = 0;
        for (int i = 0; i < letters.length; i++) {
            while (j > 0 && pattern[j] != letters[i]) {
                j = failureFunction[j - 1];
            }
            if (letters[i] == pattern[j])
                j++;
            if (j == pattern.length) {
                list.add(i - j + 1);
                j = failureFunction[j - 1];
            }
        }
        return list;
    }
}
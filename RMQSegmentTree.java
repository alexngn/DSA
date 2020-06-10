import java.util.*;
import java.io.*;

public class RMQSegmentTree {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        new Solver().solve(in, out);
        out.close();
    }
}

class SegmentTree {
    long[] tree;
    int n;
    static int identity = 0;

    SegmentTree(int[] nums) {
        this.n = nums.length;
        tree = new long[4 * n];
        build(nums, 1, 0, n - 1);
    }

    long build(int[] nums, int index, int leftRange, int rightRange) {

        if (leftRange == rightRange) {
            tree[index] = nums[leftRange];
            return tree[index];
        }
        int mid = leftRange + (rightRange - leftRange) / 2;
        long left = build(nums, index * 2, leftRange, mid);
        long right = build(nums, index * 2 + 1, mid + 1, rightRange);
        long val = combine(left, right);
        tree[index] = val;
        return tree[index];
    }

    long query(int left, int right) {
        return query(left, right, 1, 0, n - 1);
    }

    long query(int left, int right, int node, int nodeLeft, int nodeRight) {
        if (left <= nodeLeft && right >= nodeRight)
            // If current node is within target interval, return node
            return tree[node];
        if (left > nodeRight || right < nodeLeft)
            // If current node is outside target interval, return none
            return identity;
        // Else, return left and right values
        int mid = nodeLeft + (nodeRight - nodeLeft) / 2;
        long leftVal = query(left, right, node * 2, nodeLeft, mid);
        long rightVal = query(left, right, node * 2 + 1, mid + 1, nodeRight);
        long val = combine(leftVal, rightVal);
        return val;
    }

    long combine(long left, long right) {
        return left + right;
    }
}

class Solver {

    public void solve(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int q = in.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++)
            nums[i] = in.nextInt();
        SegmentTree segTree = new SegmentTree(nums);
        for (int i = 0; i < q; i++) {
            int left = in.nextInt() - 1;
            int right = in.nextInt() - 1;
            out.println(segTree.query(left, right));
        }
    }
}

class InputReader {
    public BufferedReader reader;
    public StringTokenizer tokenizer;

    public InputReader(InputStream stream) {
        reader = new BufferedReader(new InputStreamReader(stream), 32768);
        tokenizer = null;
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    long nextLong() {
        return Long.parseLong(next());
    }

    double nextDouble() {
        return Double.parseDouble(next());
    }

    String nextLine() {
        String s = "";
        try {
            s = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
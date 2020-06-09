import java.io.*;
import java.util.*;

class SparseTable {
    int[][] table;
    int[] nums;

    SparseTable(int[] nums) {
        this.nums = nums;
        // Find Largest Power of 2 equal to or less than length of nums
        int rows = 1;
        int count = 1;
        while ((rows << 1) < nums.length) {
            count++;
            rows = rows << 1;
        }
        table = new int[count + 1][nums.length];
        buildTable();
    }

    void buildTable() {
        for (int i = 0; i < nums.length; i++)
            table[0][i] = nums[i];

        // Build table with 2^row offset
        for (int i = 1; i < table.length; i++) {
            for (int j = 0, k = 1 << (i - 1); k < table[i].length; j++, k++) {
                // Scan linearly for min between both values
                table[i][j] = Math.min(table[i - 1][j], table[i - 1][k]);
            }
        }
    }

    // Left Inclusive Right Exclusive
    int query(int left, int right) {
        int pow = 1;
        int row = 0;
        while ((pow << 1) <= right - left) {
            pow = pow << 1;
            row++;
        }
        int rightIndex = right - pow;
        return Math.min(table[row][left], table[row][rightIndex]);
    }
}

public class RMQ {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        new Solver().solve(in, out);
        out.close();
    }

}

class Solver {

    public void solve(InputReader in, PrintWriter out) {
        int n = in.nextInt();
        int q = in.nextInt();
        int[] nums = new int[n];

        for (int i = 0; i < n; i++)
            nums[i] = in.nextInt();

        SparseTable table = new SparseTable(nums);

        for (int i = 0; i < q; i++) {
            int left = in.nextInt() - 1;
            int right = in.nextInt() - 1;
            out.println(table.query(left, right + 1));
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
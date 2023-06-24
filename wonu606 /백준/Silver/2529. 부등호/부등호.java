import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static final TokenizerReader reader = new TokenizerReader(System.in);
    private static final BuilderWriter writer = new BuilderWriter(System.out);

    public static void main(String[] args) {

        int count = reader.nextInt();
        String[] signs = getInequalitySigns(count);

        List<String> results = new ArrayList<>();
        String[] digits = new String[count + 1];
        for (int n = 9; n >= 0; n--) {
            boolean[] visited = new boolean[10];
            visited[n] = true;
            digits[0] = String.valueOf(n);

            dfs(0, signs, digits, visited, results);
        }

        printResult(results);
        close();
    }

    private static String[] getInequalitySigns(int size) {
        String[] signs = new String[size];
        for (int i = 0; i < size; i++) {
            signs[i] = reader.next();
        }
        return signs;
    }

    private static void dfs(int signIndex, String[] signs, String[] digits, boolean[] visited, List<String> results) {
        if (signs.length == signIndex) {
            results.add(String.join("", digits));
            return;
        }

        for (int n = 9; n >= 0; n--) {
            String addNumber = String.valueOf(n);

            if (visited[n] || !valid(signs[signIndex], digits[signIndex], addNumber)) {
                continue;
            }

            visited[n] = true;

            int nextSignIndex = signIndex + 1;
            digits[nextSignIndex] = addNumber;
            dfs(nextSignIndex, signs, digits, visited, results);

            visited[n] = false;
        }
    }

    private static boolean valid(String sign, String prev, String next) {
        if (sign.equals(">")) {
            return prev.compareTo(next) > 0;
        }
        return prev.compareTo(next) < 0;
    }

    private static void printResult(List<String> results) {
        writer.println(results.get(0));
        writer.println(results.get(results.size() - 1));
    }

    private static void close() {
        reader.close();
        writer.close();
    }
}

class TokenizerReader {

    private final BufferedReader reader;
    private StringTokenizer tokenizer;

    public TokenizerReader(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
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

    public Integer nextInt() {
        return Integer.parseInt(next());
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class BuilderWriter {

    private final BufferedWriter writer;
    private final StringBuilder builder;

    public BuilderWriter(OutputStream outputStream) {
        this.writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        builder = new StringBuilder();
    }

    public void print(String str) {
        builder.append(str);
    }

    public void println(String str) {
        print(str + "\n");
    }

    public void close() {
        try {
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
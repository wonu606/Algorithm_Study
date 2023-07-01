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

    private static int result = Integer.MAX_VALUE;

    public static void main(String[] args) {

        // 줄 입력
        int size = reader.nextInt();

        // 테이블 생성
        int[][] table = createTable(size);

        // dfs
        dfs(0, 0, new boolean[size], table);

        // 결과 출력
        printResult();

        close();
    }

    private static void dfs(int count, int startIndex, boolean[] visited, int[][] table) {
        // 종료 조건
        if (result == 0) {
            return;
        }
        if (count * 2 == table.length) {
            result = Math.min(result, diff(table, visited));
            return;
        }

        // 재귀
        for (int index = startIndex; index < table.length; index++) {
            visited[index] = true;

            dfs(count + 1, index + 1, visited, table);

            visited[index] = false;
        }
    }

    private static int diff(int[][] table, boolean[] visited) {
        int trueTeamSum = 0;
        int falseTeamSum = 0;

        for (int first = 0; first < visited.length; first++) {
            for (int second = first + 1; second < visited.length; second++) {
                if (visited[first] && visited[second]) {
                    trueTeamSum += table[first][second];
                    trueTeamSum += table[second][first];
                } else if (!visited[first] && !visited[second]) {
                    falseTeamSum += table[first][second];
                    falseTeamSum += table[second][first];
                }
            }
        }
        return Math.abs(trueTeamSum - falseTeamSum);
    }

    private static int[][] createTable(int size) {
        int[][] table = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                table[row][col] = reader.nextInt();
            }
        }
        return table;
    }

    private static void printResult() {
        writer.println(String.valueOf(result));
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
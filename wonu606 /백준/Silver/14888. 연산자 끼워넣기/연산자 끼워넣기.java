import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    // ABCDE
    private static final TokenizerReader reader = new TokenizerReader(System.in);
    private static final BuilderWriter writer = new BuilderWriter(System.out);

    private static long minResult = Long.MAX_VALUE;
    private static long maxResult = Long.MIN_VALUE;

    public static void main(String[] args) {

        // 수를 개수를 받는다.
        int numberCount = reader.nextInt();

        // 수를 입력 받는다.
        int[] numbers = generateNumbers(numberCount);

        // + - * / 순으로 개수를 받는다.
        int[] operatorCounts = generateOperatorCount();

        // dfs를 통해 최소값과 최대값을 구한다.
        dfs(1, numbers, operatorCounts, numbers[0]);

        // 결과 출력
        printResult();

        close();
    }

    private static int[] generateNumbers(int numberCount) {
        int[] numbers = new int[numberCount];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = reader.nextInt();
        }
        return numbers;
    }

    private static int[] generateOperatorCount() {
        int[] operatorCounts = new int[4];
        for (int i = 0; i < operatorCounts.length; i++) {
            operatorCounts[i] = reader.nextInt();
        }
        return operatorCounts;
    }

    private static void dfs(int numbersIndex, int[] numbers, int[] operatorCounts, long currentResult) {
        // 종료 조건
        if (numbersIndex == numbers.length) {
            maxResult = Math.max(maxResult, currentResult);
            minResult = Math.min(minResult, currentResult);
            return;
        }

        // 재귀
        for (int i = 0; i < operatorCounts.length; i++) {
            if (operatorCounts[i] == 0) {
                continue;
            }

            operatorCounts[i]--;
            long nextResult = calculate(currentResult, numbers[numbersIndex], i);
            dfs(numbersIndex + 1, numbers, operatorCounts, nextResult);
            operatorCounts[i]++;
        }
    }

    private static long calculate(long currentResult, int number, int i) {
        // + - * / 순
        switch (i) {
            case 0:
                return currentResult + number;
            case 1:
                return currentResult - number;
            case 2:
                return currentResult * number;
            case 3:
                return currentResult / number;
            default:
                throw new IndexOutOfBoundsException("존재하면 안되는 인덱스");
        }
    }

    private static void printResult() {
        writer.println(String.valueOf(maxResult));
        writer.println(String.valueOf(minResult));
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
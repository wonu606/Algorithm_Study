import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    private static final TokenizerReader reader = new TokenizerReader(System.in);
    private static final BuilderWriter writer = new BuilderWriter(System.out);

    private static int result;

    public static void main(String[] args) {

        // 총 입력 개수를 받는다.
        int wordsCount = reader.nextInt();

        // 단어를 입력 받는다.
        String[] words = createWords(wordsCount);

        // 알파벳을 추출한다.
        Character[] ExistingAlphabets = extractAlphabets(words);

        // dfs를 돌며 각 알파벳을 1 ~ 9까지의 수로 치환한다.
        Map<Character, Integer> alphabetValues = new HashMap<>();
        boolean[] visitedValues = new boolean[10];
        dfs(0, ExistingAlphabets, visitedValues, alphabetValues, words);

        // 결과를 출력한다.
        printResult();

        close();
    }

    private static String[] createWords(int wordsCount) {
        List<String> words = new ArrayList<>();
        for (int i = 0; i < wordsCount; i++) {
            words.add(reader.next());
        }
        return words.toArray(new String[0]);
    }

    private static Character[] extractAlphabets(String[] words) {
        Set<Character> alphabets = new HashSet<>();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                alphabets.add(word.charAt(i));
            }
        }
        return alphabets.toArray(Character[]::new);
    }

    private static void dfs(int currentIndex, Character[] existingAlphabets, boolean[] visitedValues, Map<Character, Integer> alphabetValues, String[] words) {
        // 종료 조건
        if (currentIndex >= existingAlphabets.length) {
            result = Math.max(sumWordsValue(words, alphabetValues), result);
            return;
        }

        // 0-9까지 순회
        for (int value = 0; value < 10; value++) {
            if (visitedValues[value]) {
                continue;
            }

            visitedValues[value] = true;
            Character currentCharacter = existingAlphabets[currentIndex];
            alphabetValues.put(currentCharacter, value);
            dfs(currentIndex + 1, existingAlphabets, visitedValues, alphabetValues, words);
            alphabetValues.remove(currentCharacter);
            visitedValues[value] = false;
        }
    }

    private static int sumWordsValue(String[] words, Map<Character, Integer> alphabetValues) {
        int sumResult = 0;
        for (String word : words) {
            sumResult += convertWordToNumber(word, alphabetValues);
        }
        return sumResult;
    }

    private static int convertWordToNumber(String word, Map<Character, Integer> alphabetValues) {
        int resultNumber = 0;
        for (int i = 0; i < word.length(); i++) {
            resultNumber = resultNumber * 10 + alphabetValues.get(word.charAt(i));
        }
        return resultNumber;
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
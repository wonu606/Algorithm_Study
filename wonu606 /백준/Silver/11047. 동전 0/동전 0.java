import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class Main {

    private static final TokenizerReader reader = new TokenizerReader(System.in);
    private static final BuilderWriter writer = new BuilderWriter(System.out);

    public static void main(String[] args) {
        int coinTypesCount = reader.nextInt();
        int value = reader.nextInt();
        int[] coinType = IntStream.range(0, coinTypesCount)
                .map(i -> reader.nextInt())
                .toArray();

        int minCoinsCount = 0;
        for (int i = coinType.length - 1; i >= 0; i--) {
            minCoinsCount += value / coinType[i];
            value %= coinType[i];
        }

        writer.println(String.valueOf(minCoinsCount));
        close();
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
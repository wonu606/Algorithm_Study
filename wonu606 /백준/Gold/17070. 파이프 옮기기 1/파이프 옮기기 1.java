import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {

    int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int size = Integer.parseInt(reader.readLine());
        Integer[][] map = new Integer[size][size];
        for (int i = 0; i < size; i++) {
            map[i] = Arrays.stream(reader.readLine().split(" "))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
        }

        int[][][] dp = new int[size][size][3];
        dp[0][1][0] = 1;
        for (int col = 2; col < size; col++) {
            if (map[0][col] == 1) {
                continue;
            }
            dp[0][col][0] += dp[0][col - 1][0];
        }

        for (int row = 1; row < size; row++) {
            for (int col = 1; col < size; col++) {
                if (map[row][col] == 1) {
                    continue;
                }

                dp[row][col][0] += dp[row][col - 1][0] + dp[row][col - 1][1];

                if (map[row][col - 1] == 0 && map[row - 1][col] == 0) {
                    dp[row][col][1] += dp[row - 1][col - 1][0] + dp[row - 1][col - 1][1] + dp[row - 1][col - 1][2];
                }

                dp[row][col][2] += dp[row - 1][col][1] + dp[row - 1][col][2];
            }
        }

        int result = Arrays.stream(dp[size - 1][size - 1]).sum();
        writer.write(String.valueOf(result));

        reader.close();
        writer.close();
    }
}

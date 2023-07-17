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

        // 0: 가로, 1: 대각, 2: 세로
        int[][][] dp = new int[size][size][3];
        dp[0][0][0] = 1;
        for (int col = 1; col < size; col++) {
            if (map[0][col] == 1) {
                continue;
            }
            dp[0][col][0] += dp[0][col - 1][0];
            dp[0][col][1] += dp[0][col - 1][0];
        }

        for (int row = 1; row < size; row++) {
            for (int col = 1; col < size; col++) {
                if (map[row][col] == 1) {
                    continue;
                }

                /*
                다음 가로: 이전이 가로이거나, 대각일 경우에만 가능
                다음 대각: 이전이 가로, 대각, 세로
                다음 세로: 이전 대각, 세로
                 */

                dp[row][col][0] += dp[row][col - 1][0];
                dp[row][col][1] += dp[row][col - 1][0];

                if (map[row][col - 1] == 0 && map[row - 1][col] == 0) {
                    dp[row][col][0] += dp[row - 1][col - 1][1];
                    dp[row][col][1] += dp[row - 1][col - 1][1];
                    dp[row][col][2] += dp[row - 1][col - 1][1];
                }

                dp[row][col][1] += dp[row - 1][col][2];
                dp[row][col][2] += dp[row - 1][col][2];
            }
        }

//        for (int row = 0; row < size; row++) {
//            System.out.print('[');
//            for (int col = 0; col < size; col++) {
//                System.out.print(map[row][col] + ", ");
//            }
//            System.out.print("]\n");
//        }
//
//        for (int row = 0; row < size; row++) {
//            for (int col = 0; col < size; col++) {
//                System.out.print("[");
//                Arrays.stream(dp[row][col])
//                        .forEach(e -> System.out.print(e + ","));
//                System.out.print("]\t");
//            }
//            System.out.println();
//        }

        int result = 0;
        result += dp[size - 1][size - 2][0];
        if (map[size - 1][size - 2] == 0 && map[size - 2][size - 1] == 0) {
            result += dp[size - 2][size - 2][1];
        }
        result += dp[size - 2][size - 1][2];

        if (map[size - 1][size - 1] == 1) {
            result = 0;
        }

        writer.write(String.valueOf(result));

        reader.close();
        writer.close();
    }
}

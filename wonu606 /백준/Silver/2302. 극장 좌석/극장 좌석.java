import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int[] dp = new int[41];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i < dp.length; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        // 입력
        int seatSize = Integer.parseInt(reader.readLine());
        int fixedSize = Integer.parseInt(reader.readLine());
        int[] fixedSeats = reader.lines()
                .limit(fixedSize)
                .mapToInt(Integer::parseInt)
                .toArray();

        int startSeat = 0;
        int result = 1;
        for (int fixedSeat : fixedSeats) {
            result = result * dp[fixedSeat - startSeat - 1];
            startSeat = fixedSeat;
        }
        result = result * dp[seatSize - startSeat];

        writer.write(String.valueOf(result));

        reader.close();
        writer.close();
    }
}

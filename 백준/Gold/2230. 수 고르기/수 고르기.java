import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        String[] firstLine = reader.readLine().split(" ");
        int totalNumbers = Integer.parseInt(firstLine[0]);
        int minDifference = Integer.parseInt(firstLine[1]);

        // 배열 할당
        int[] numbers = new int[totalNumbers];
        for (int i = 0; i < totalNumbers; i++) {
            numbers[i] = Integer.parseInt(reader.readLine());
        }

        // 정렬
        Arrays.sort(numbers);

        // 최소 차이
        int result = Integer.MAX_VALUE;
        int left = 0;
        int right = 1;
        while (right < numbers.length) {
            int difference = numbers[right] - numbers[left];
            if (minDifference > difference) {
                right++;
                continue;
            }

            result = Math.min(result, difference);
            if (left + 1 == right) {
                left++;
                right++;
            }
            else {
                left++;
            }
        }

        writer.write(String.valueOf(result));

        reader.close();
        writer.close();
    }
}

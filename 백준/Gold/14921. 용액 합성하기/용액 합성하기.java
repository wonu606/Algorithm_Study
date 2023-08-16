import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        // 입출력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        int inputsSize = Integer.parseInt(reader.readLine());
        int[] inputs = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        // 0에 가까운 값 찾기(최대 NlogN까지 가능)
        int left = 0;
        int right = inputsSize - 1;
        int answer = Integer.MAX_VALUE;
        while (left < right) {
            int sum = inputs[left] + inputs[right];
            if (Math.abs(sum) < Math.abs(answer)) {
                answer = sum;
            }

            if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }

        // 결과 출력
        writer.write(String.valueOf(answer));

        // 입출력 해제
        reader.close();
        writer.close();
    }
}

/*
-3 -1 5 93
start: 0 end: 3 sum: -3 + 93 = 90(양수)
start: 0 end: 2 sum: -3 + 5 = 2(양수)
start: 0 end: 1 sum: -3 + -1 = -4(음수)
start: 1 end: 1 검사X 종료
 */

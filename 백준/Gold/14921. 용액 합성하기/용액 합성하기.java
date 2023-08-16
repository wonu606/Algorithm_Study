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

        // 이진 탐색을 통해 0에 가까운 값 찾기(N)
        int start = 0;
        int end = inputsSize - 1;
        int answer = Integer.MAX_VALUE;
        while (start < end) {
            int sum = inputs[start] + inputs[end];
            if (Math.abs(sum) < Math.abs(answer)) {
                answer = sum;
            }

            if (sum < 0) {
                start++;
            } else {
                end--;
            }
        }

        // 결과 출력
        writer.write(String.valueOf(answer));

        // 입출력 해제
        reader.close();
        writer.close();
    }
}

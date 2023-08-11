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
        int firstArraySize = Integer.parseInt(reader.readLine());
        int[] firstArray = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int secondArraySize = Integer.parseInt(reader.readLine());
        int[] secondArray = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        // 첫번째 배열 정렬
        Arrays.sort(firstArray);

        // 결과 생성
        int[] results = new int[secondArraySize];

        // 수 찾기
        for (int index = 0; index < secondArraySize; index++) {
            int value = secondArray[index];
            results[index] = find(firstArray, value);
        }

        // 결과 출력
        Arrays.stream(results)
                .forEach(System.out::println);

        // 입출력 해제
        reader.close();
        writer.close();
    }

    private static int find(int[] firstArray, int value) {
        int start = 0;
        int end = firstArray.length - 1;

        while (start <= end) {
            int mid = (start + end) / 2;
            if (firstArray[mid] < value) {
                start = mid + 1;
            } else if (firstArray[mid] > value) {
                end = mid - 1;
            } else {
                return 1;
            }
        }
        return 0;
    }
}

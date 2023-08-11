import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // 두번째 배열 맵 생성(key: value, value: index)
        Map<Integer, List<Integer>> secondArrayValues = new HashMap<>();
        for (int index = 0; index < secondArraySize; index++) {
            int key = secondArray[index];
            secondArrayValues.putIfAbsent(key, new ArrayList<>());
            secondArrayValues.get(key).add(index);
        }

        // 결과 인덱스 생성
        int[] results = new int[secondArraySize];

        // 첫번째 배열의 값이 두번째 배열 맵에 존재한다면 결과에 반영
        for (int expectedKey : firstArray) {
            if (!secondArrayValues.containsKey(expectedKey)) {
                continue;
            }

            List<Integer> indexes = secondArrayValues.get(expectedKey);
            for (Integer index : indexes) {
                if (results[index] == 1) {
                    break;
                }
                results[index] = 1;
            }
        }

        // 결과 출력
        Arrays.stream(results)
                .forEach(System.out::println);

        // 입출력 해제
        reader.close();
        writer.close();
    }
}

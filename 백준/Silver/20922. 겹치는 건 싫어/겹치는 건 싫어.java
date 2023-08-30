import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Main {

    public static void main(String[] args) throws Exception {
        // 입출력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        int[] infos = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int numbersSize = infos[0];
        int maxCount = infos[1];

        int[] numbers = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        // 최장 연속 부분 수열의 길이 구하기(투 포인터)
        int left = 0;
        int right = 0;
        int maxLength = 0;
        Map<Integer, Integer> coutMap = new HashMap<>();
        while (right < numbersSize) {
            Integer rightCount = coutMap.getOrDefault(numbers[right], 0);
            if (rightCount < maxCount) {
                coutMap.put(numbers[right], rightCount + 1);
                maxLength = Math.max(maxLength, right - left + 1);
                right++;
                continue;
            }

            coutMap.put(numbers[left], coutMap.get(numbers[left]) - 1);
            left++;
        }

        // 결과
        writer.write(String.valueOf(maxLength));

        // 입출력 해제
        reader.close();
        writer.close();
    }

}

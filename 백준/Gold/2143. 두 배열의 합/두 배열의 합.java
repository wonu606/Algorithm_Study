import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        // 입출력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        long target = Long.parseLong(reader.readLine());
        int AListSize = Integer.parseInt(reader.readLine());
        List<Long> AList = Arrays.stream(reader.readLine().split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        int BListSize = Integer.parseInt(reader.readLine());
        List<Long> BList = Arrays.stream(reader.readLine().split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        // A 부분 합 key: value, value: count
        Map<Long, Long> Asum = new HashMap<>();
        for (int i = 0; i < AListSize; i++) {
            long sum = 0;
            for (int j = i; j < AListSize; j++) {
                sum += AList.get(j);
                Asum.put(sum, Asum.getOrDefault(sum, 0L) + 1);
            }
        }

        // B 부분 합을 이어가며 결과 케이스 추가
        long targetCaseCount = 0;
        for (int i = 0; i < BListSize; i++) {
            long sum = 0;
            for (int j = i; j < BListSize; j++) {
                sum += BList.get(j);
                Long count = Asum.getOrDefault(target - sum, 0L);
                targetCaseCount += count;
            }
        }

        // 결과 출력
        writer.write(String.valueOf(targetCaseCount));

        // 입출력 해제
        reader.close();
        writer.close();
    }
}

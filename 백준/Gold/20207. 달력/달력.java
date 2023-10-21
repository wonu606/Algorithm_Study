import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class Main {

    private static final int MIN_DAY = 1;
    private static final int MAX_DAY = 365;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Map<Integer, Integer> calendar = new HashMap<>();
        for (int day = MIN_DAY; day <= MAX_DAY; day++) {
            calendar.put(day, 0);
        }

        // 입력
        int inputSize = Integer.parseInt(reader.readLine());
        int[][] lines = new int[inputSize][];
        for (int i = 0; i < inputSize; i++) {
            String[] line = reader.readLine().split(" ");
            int startDay = Integer.parseInt(line[0]);
            int endDay = Integer.parseInt(line[1]);

            lines[i] = new int[]{startDay, endDay};
        }

        // 일정 추가
        for (int[] line : lines) {
            int startDay = line[0];
            int endDay = line[1];
            for (int dayToAdd = startDay; dayToAdd <= endDay; dayToAdd++) {
                calendar.put(dayToAdd, calendar.get(dayToAdd) + 1);
            }
        }

        // 크기 계산
        int sum = 0;
        int consecutiveDays = 0;
        int maxAccumulation = 0;
        for (int day = MIN_DAY; day <= MAX_DAY; day++) {
            int currentAccumulation = calendar.get(day);
            if (currentAccumulation == 0) {
                sum += consecutiveDays * maxAccumulation;
                consecutiveDays = 0;
                maxAccumulation = 0;
                continue;
            }

            consecutiveDays++;
            maxAccumulation = Math.max(currentAccumulation, maxAccumulation);
        }
        // 마지막 누락 해결
        sum += consecutiveDays * maxAccumulation;

        // 출력
        System.out.println(sum);

        // 종료
        reader.close();
    }
}

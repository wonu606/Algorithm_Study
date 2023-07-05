import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 시작 시간, 끝낸 시간, 종료 시간 입력
        String[] timeLine = reader.readLine().split(" ");
        String startTime = timeLine[0];
        String finishedTime = timeLine[1];
        String endTime = timeLine[2];

        // 채팅 기록 실시간으로 받아오기
        Set<String> attendance = new HashSet<>();
        int resultCount = 0;
        while (true) {
            String chattingLine = reader.readLine();
            if (chattingLine == null || chattingLine.isEmpty()) {
                break;
            }

            String[] splitChattingLine = chattingLine.split(" ");

            String currentTime = splitChattingLine[0];
            String nickname = splitChattingLine[1];

            if (isBefore(startTime, currentTime)) {
                attendance.add(nickname);
            }

            if (isBetween(finishedTime, endTime, currentTime) &&
                    attendance.contains(nickname)) {
                resultCount++;
                attendance.remove(nickname);
            }
        }

        // 결과 출력
        writer.write(String.valueOf(resultCount));

        reader.close();
        writer.close();
    }

    private static boolean isBefore(String destinationTime, String sourceTime) {
        return sourceTime.compareTo(destinationTime) <= 0;
    }

    private static boolean isAfter(String destinationTime, String sourceTime) {
        return sourceTime.compareTo(destinationTime) >= 0;
    }

    private static boolean isBetween(String startTime, String endTime, String currentTime) {
        return isAfter(startTime, currentTime) && isBefore(endTime, currentTime);
    }
}

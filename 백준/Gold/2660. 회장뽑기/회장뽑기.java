import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        int userSize = Integer.parseInt(reader.readLine());
        int[][] userRelationArray = new int[userSize + 1][userSize + 1];
        for (int i = 1; i < userRelationArray.length; i++) {
            Arrays.fill(userRelationArray[i], 50);
            userRelationArray[i][i] = 0;
        }

        while (true) {
            int[] friendshipArray = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            if (friendshipArray[0] == -1 || friendshipArray[1] == -1) {
                break;
            }
            userRelationArray[friendshipArray[0]][friendshipArray[1]] = 1;
            userRelationArray[friendshipArray[1]][friendshipArray[0]] = 1;
        }

        // 플로이드-워셜
        for (int intermediate = 1; intermediate <= userSize; intermediate++) {
            for (int fromMember = 1; fromMember <= userSize; fromMember++) {
                for (int toMember = 1; toMember <= userSize; toMember++) {
                    userRelationArray[fromMember][toMember] = Math.min(userRelationArray[fromMember][toMember],
                            userRelationArray[fromMember][intermediate] + userRelationArray[intermediate][toMember]);
                }
            }
        }

        /*
        A - C // B - C,D,E // C - B,D // D - A,B,C,D
        intermediate = 1
        (1) A -> B: INF, C: 1, D: INF, E: INF
        (2) B -> A: INF, C: 1, D: 1, E: 1
        (3) C -> A: INF, B: 1, D: 1, E: INF
        (4) D -> A: 1, B: 1, C: 1

        intermediate = 2
        (1) A -> B: INF, C: 1, D: INF, E: INF
        (2) B -> A: INF, C: 1, D: 1, E: 1
        (3) C -> A: INF, B: 1, D: 1, E: [[2]]
        (4) D -> A: 1, B: 1, C: 1
         */

        // 각 유저의 점수 구하기
        int[] userScoreArray = new int[userSize + 1];
        for (int i = 1; i <= userSize; i++) {
            int userMaxScore = 0;
            for (int j = 1; j <= userSize; j++) {
                userMaxScore = Math.max(userMaxScore, userRelationArray[i][j]);
            }

            userScoreArray[i] = userMaxScore;
        }

        // 가장 작은 유저 점수 구하기
        int candidateScore = 50;
        for (int i = 1; i <= userSize; i++) {
            candidateScore = Math.min(candidateScore, userScoreArray[i]);
        }

        // 후보 추출
        List<Integer> candidateList = new ArrayList<>();
        for (int i = 1; i <= userSize; i++) {
            if (userScoreArray[i] == candidateScore) {
                candidateList.add(i);
            }
        }

        // 출력
        writer.write(String.format("%d %d\n", candidateScore, candidateList.size()));
        for (Integer candidate : candidateList) {
            writer.write(candidate + " ");
        }
        writer.newLine();

        reader.close();
        writer.close();
    }
}

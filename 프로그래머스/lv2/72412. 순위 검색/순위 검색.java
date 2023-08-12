import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

class Solution {

    public int[] solution(String[] info, String[] query) {
        // 전처리
        Map<String, List<Integer>> scoresMap = buildScoresMap(info);

        // query에 따른 결과 구하기
        return Stream.of(query)
                .mapToInt(q -> count(scoresMap, q))
                .toArray();
    }

    private Map<String, List<Integer>> buildScoresMap(String[] info) {
        Map<String, List<Integer>> scoresMap = new HashMap<>();

        for (String str : info) {
            String[] tokens = str.split(" ");
            int score = Integer.parseInt(tokens[tokens.length - 1]);
            forEachKey(0, "", tokens, key -> {
                scoresMap.putIfAbsent(key, new ArrayList<>());
                scoresMap.get(key).add(score);
            });
        }

        for (List<Integer> scores : scoresMap.values()) {
            Collections.sort(scores);
        }
        return scoresMap;
    }

    private void forEachKey(int index, String prefix, String[] tokens,
            Consumer<String> action) {
        if (index == tokens.length - 1) {
            action.accept(prefix);
            return;
        }

        forEachKey(index + 1, prefix + tokens[index], tokens, action);
        forEachKey(index + 1, prefix + "-", tokens, action);
    }

    private int count(Map<String, List<Integer>> scoresMap, String query) {
        String[] tokens = query.split(" (and)?");
        String key = String.join("", Arrays.copyOf(tokens, tokens.length - 1));

        if (!scoresMap.containsKey(key)) {
            return 0;
        }
        List<Integer> scores = scoresMap.get(key);
        int minScore = Integer.parseInt(tokens[tokens.length - 1]);
        return scores.size() - binarySearch(scores, minScore);
    }

    private int binarySearch(List<Integer> scores, int minScore) {
        int start = 0;
        int end = scores.size();

        while (end > start) {
            int mid = (start + end) / 2;
            if (scores.get(mid) >= minScore) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }

    public static void main(String[] args) {
        String[] info = {
                "java backend junior pizza 150", "python frontend senior chicken 210",
                "python frontend senior chicken 150", "cpp backend senior pizza 260",
                "java backend junior chicken 80", "python backend senior chicken 50"
        };
        String[] query = {
                "java and backend and junior and pizza 100",
                "python and frontend and senior and chicken 200",
                "cpp and - and senior and pizza 250", "- and backend and senior and - 150",
                "- and - and - and chicken 100", "- and - and - and - 150"
        };
        Solution solution = new Solution();
        int[] answer = solution.solution(info, query);
        Arrays.stream(answer)
                .forEach(System.out::println);
    }
}

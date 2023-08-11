import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public int[] solution(String[] gems) {
        Set<String> totalGems = new HashSet<>(Arrays.asList(gems));

        HashMap<String, Integer> currentGems = new HashMap<>();
        int currentStart = 0, currentEnd = 0;
        int resultStart = currentStart, resultEnd = currentEnd;
        int shortest = gems.length + 1;

        while (currentEnd < gems.length) {
            currentGems.put(gems[currentEnd], currentGems.getOrDefault(gems[currentEnd], 1) + 1);
            currentEnd++;

            while (currentGems.size() == totalGems.size()) {
                if (currentEnd - currentStart < shortest) {
                    shortest = currentEnd - currentStart;
                    resultStart = currentStart + 1;
                    resultEnd = currentEnd;
                }

                if (currentGems.get(gems[currentStart]) == 2) {
                    currentGems.remove(gems[currentStart]);
                } else {
                    currentGems.put(gems[currentStart], currentGems.get(gems[currentStart]) - 1);
                }
                currentStart++;
            }
        }

        return new int[]{resultStart, resultEnd};
    }
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        // 입력
        reader.readLine();
        List<Integer> numbers = Arrays.stream(reader.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        IndexAndCount[] indexAndCounts = new IndexAndCount[numbers.size()];
        for (int index = 0; index < indexAndCounts.length; index++) {
            indexAndCounts[index] = new IndexAndCount(-1, 1);
        }

        for (int currentIndex = 0; currentIndex < numbers.size(); currentIndex++) {
            int currentMaxCount = 0;
            int maxCountIndex = -1;
            for (int prevIndex = 0; prevIndex < currentIndex; prevIndex++) {
                if (numbers.get(prevIndex) < numbers.get(currentIndex)
                        && currentMaxCount < indexAndCounts[prevIndex].count) {
                    currentMaxCount = indexAndCounts[prevIndex].count;
                    maxCountIndex = prevIndex;
                }
            }
            if (maxCountIndex == -1) {
                continue;
            }
            
            indexAndCounts[currentIndex].count = currentMaxCount + 1;
            indexAndCounts[currentIndex].index = maxCountIndex;
        }

        int maxCount = 0;
        int lastIndex = -1;
        for (int index = 0; index < indexAndCounts.length; index++) {
            if (maxCount < indexAndCounts[index].count) {
                lastIndex = index;
                maxCount = indexAndCounts[index].count;
            }
        }

        writer.write(indexAndCounts[lastIndex].count + "\n");
        printResult(numbers, indexAndCounts, lastIndex);

        reader.close();
        writer.close();
    }

    private static void printResult(List<Integer> numbers, IndexAndCount[] indexAndCounts, int currentIndex)
            throws IOException {
        if (currentIndex == -1) {
            return;
        }

        printResult(numbers, indexAndCounts, indexAndCounts[currentIndex].index);
        writer.write(numbers.get(currentIndex) + " ");
    }

    static class IndexAndCount {

        public int index;
        public int count;

        public IndexAndCount(int index, int count) {
            this.index = index;
            this.count = count;
        }
    }
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        int relationListSize = Integer.parseInt(reader.readLine());
        List<Integer>[] relationList = new List[relationListSize + 1];
        for (int i = 0; i < relationList.length; i++) {
            relationList[i] = new ArrayList<Integer>();
        }

        int relationCount = Integer.parseInt(reader.readLine());
        for (int i = 0; i < relationCount; i++) {
            List<Integer> line = Arrays.stream(reader.readLine().split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            int index1 = line.get(0);
            int index2 = line.get(1);

            relationList[index1].add(index2);
            relationList[index2].add(index1);
        }

        int result = bfs(relationList, 1);

        writer.write(String.valueOf(result));

        reader.close();
        writer.close();
    }

    private static int bfs(List<Integer>[] relationList, int startIndex) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startIndex, 0});
        boolean[] visited = new boolean[relationList.length];
        visited[startIndex] = true;

        while (!queue.isEmpty()) {
            int[] element = queue.poll();
            int currentIndex = element[0];
            int currentDepth = element[1];
            if (currentDepth > 1) {
                continue;
            }

            for (Integer value : relationList[currentIndex]) {
                if (visited[value]) {
                    continue;
                }
                visited[value] = true;
                queue.add(new int[]{value, currentDepth + 1});
            }
        }

        int count = 0;
        for (int i = 2; i < visited.length; i++) {
            if (visited[i]) {
                count++;
            }
        }
        return count;
    }
}

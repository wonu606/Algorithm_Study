import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class Main {

    private static final int[][] DIRECTS = new int[][]{
            new int[]{-1, 0},
            new int[]{0, 1},
            new int[]{1, 0},
            new int[]{0, -1},
    };

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        List<Integer> resultList = new ArrayList<>();
        while (true) {
            int mapSize = Integer.parseInt(reader.readLine());
            if (mapSize == 0) {
                break;
            }

            int[][] map = new int[mapSize][];
            for (int row = 0; row < mapSize; row++) {
                map[row] = Arrays.stream(reader.readLine().split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }
            resultList.add(move(map, new int[]{0, 0}, new int[]{mapSize - 1, mapSize - 1}));
        }

        // 출력
        for (int i = 0; i < resultList.size(); i++) {
            writer.write("Problem " + (i + 1) + ": " + resultList.get(i) + "\n");
        }
        reader.close();
        writer.close();
    }

    private static Integer move(int[][] map, int[] start, int[] end) {
        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(key -> key[2]));
        pq.add(new int[]{start[0], start[1], map[start[0]][start[1]]});

        int[][] dist = new int[map.length][map.length];
        for (int row = 0; row < dist.length; row++) {
            Arrays.fill(dist[row], Integer.MAX_VALUE);
        }

        dist[start[0]][start[1]] = map[start[0]][start[1]];

        while (!pq.isEmpty() && dist[end[0]][end[1]] == Integer.MAX_VALUE) {
            int[] current = pq.poll();

            for (int[] direct : DIRECTS) {
                int[] nextPoint = new int[]{current[0] + direct[0], current[1] + direct[1]};
                if (!isIndexValid(map, nextPoint)) {
                    continue;
                }

                int nextCost = current[2] + map[nextPoint[0]][nextPoint[1]];
                if (nextCost >= dist[nextPoint[0]][nextPoint[1]]) {
                    continue;
                }
                dist[nextPoint[0]][nextPoint[1]] = nextCost;
                pq.offer(new int[]{nextPoint[0], nextPoint[1], nextCost});
            }
        }
        return dist[end[0]][end[1]];
    }

    private static boolean isIndexValid(int[][] map, int[] point) {
        if (point[0] < 0 || map.length <= point[0]) {
            return false;
        }
        return point[1] >= 0 && map[point[0]].length > point[1];
    }

}

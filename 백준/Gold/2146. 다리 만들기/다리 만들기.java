import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    private static int mapSize;
    private static int[][] map;
    private static Point[] DIRECTS = new Point[]{
            new Point(-1, 0),
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1)
    };

    public static void main(String[] args) throws IOException {
        // 입출력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        mapSize = Integer.parseInt(reader.readLine());

        map = new int[mapSize][];
        for (int row = 0; row < mapSize; row++) {
            map[row] = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        // 같은 육지끼리 묶어주기
        int label = 2;
        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                if (map[row][col] != 1) {
                    continue;
                }
                bind(row, col, label);
                label++;
            }
        }

        // 최소 연결 값 구하기
        int minBridgeLength = Integer.MAX_VALUE;
        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                if (map[row][col] == 0) {
                    continue;
                }
                minBridgeLength = Math.min(getMinBridgeLength(row, col), minBridgeLength);
            }
        }

        // 출력
        writer.write(String.valueOf(minBridgeLength));

        // 입출력 해제
        reader.close();
        writer.close();
    }

    private static void bind(int startRow, int startCol, int label) {
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[mapSize][mapSize];

        visited[startRow][startCol] = true;
        queue.add(new Point(startRow, startCol));

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            map[current.row][current.col] = label;

            for (Point direct : DIRECTS) {
                int nextRow = current.row + direct.row;
                int nextCol = current.col + direct.col;

                if (checkInvalidRange(nextRow, nextCol)) {
                    continue;
                }
                if (visited[nextRow][nextCol] || map[nextRow][nextCol] == 0) {
                    continue;
                }
                visited[nextRow][nextCol] = true;

                queue.offer(new Point(nextRow, nextCol));
            }
        }
    }

    private static boolean checkInvalidRange(int nextRow, int nextCol) {
        if (nextRow < 0 || mapSize <= nextRow) {
            return true;
        }
        return nextCol < 0 || mapSize <= nextCol;
    }

    private static int getMinBridgeLength(int startRow, int startCol) {
        Queue<Object[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[mapSize][mapSize];

        queue.offer(new Object[]{new Point(startRow, startCol), 0});
        int startLabel = map[startRow][startCol];

        while (!queue.isEmpty()) {
            Object[] element = queue.poll();
            Point current = (Point) element[0];
            int currentLength = (Integer) element[1];

            int currenLabel = map[current.row][current.col];
            if (currenLabel != 0 && startLabel != currenLabel) {
                return currentLength  - 1;
            }

            for (Point direct : DIRECTS) {
                int nextRow = current.row + direct.row;
                int nextCol = current.col + direct.col;

                if (checkInvalidRange(nextRow, nextCol)) {
                    continue;
                }
                if (visited[nextRow][nextCol] || map[nextRow][nextCol] == startLabel) {
                    continue;
                }
                visited[nextRow][nextCol] = true;

                queue.offer(new Object[]{new Point(nextRow, nextCol), currentLength + 1});
            }
        }
        return Integer.MAX_VALUE;
    }

    private static class Point {

        int row;
        int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}

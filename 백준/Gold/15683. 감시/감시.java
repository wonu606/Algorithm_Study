import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

    private static final Point[] DIRECTS = new Point[]{
            new Point(-1, 0),
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1)
    };

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        String[] firstLine = reader.readLine().split(" ");
        int rowSize = Integer.parseInt(firstLine[0]);
        int colSize = Integer.parseInt(firstLine[1]);

        int[][] map = new int[rowSize][];
        for (int row = 0; row < rowSize; row++) {
            map[row] = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        // CCTV 동작 정의
        Map<Integer, int[]> cctvActionMap = new HashMap<>();
        cctvActionMap.put(1, new int[]{1, 0, 0, 0});
        cctvActionMap.put(2, new int[]{1, 0, 1, 0});
        cctvActionMap.put(3, new int[]{1, 0, 0, 1});
        cctvActionMap.put(4, new int[]{1, 0, 1, 1});
        cctvActionMap.put(5, new int[]{1, 1, 1, 1});

        // CCTV 얻기
        List<Point> cctvList = new ArrayList<>();
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (map[row][col] == 0 || map[row][col] == 6) {
                    continue;
                }
                cctvList.add(new Point(row, col));
            }
        }

        // 최대값 구하기
        int[][] wallMap = new int[rowSize][colSize];
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (map[row][col] != 6) {
                    continue;
                }

                wallMap[row][col] = 6;
            }
        }
        int maxValue = findMax(map, wallMap,
                cctvActionMap, cctvList, 0);

        // 출력
        writer.write(String.valueOf(rowSize * colSize - maxValue));

        reader.close();
        writer.close();
    }

    private static int findMax(int[][] map, int[][] currentMap,
            Map<Integer, int[]> cctvActionMap,
            List<Point> cctvList, int cctvListIndex) {
        if (cctvList.size() == cctvListIndex) {
            return count(currentMap);
        }

        int[][] copyMap = new int[currentMap.length][];
        Point point = cctvList.get(cctvListIndex);
        int[] currentAction = cctvActionMap.get(map[point.row][point.col]);
        int maxValue = 0;

        for (int directionIndex = 0; directionIndex < DIRECTS.length; directionIndex++) {
            for (int row = 0; row < copyMap.length; row++) {
                copyMap[row] = Arrays.copyOf(currentMap[row], currentMap[row].length);
            }
            copyMap[point.row][point.col] = -1;

            fill(copyMap, point, directionIndex, currentAction);
            maxValue = Math.max(maxValue,
                    findMax(map, copyMap, cctvActionMap, cctvList, cctvListIndex + 1));
        }
        return maxValue;
    }

    private static int count(int[][] map) {
        int result = 0;

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == 0) {
                    continue;
                }
                result++;
            }
        }
        return result;
    }

    private static void fill(int[][] map, Point point, int directIndex, int[] action) {
        for (int actionIndex = 0; actionIndex < action.length; actionIndex++) {
            if (action[actionIndex] == 0) {
                continue;
            }

            int fillDirectionIndex = (directIndex + actionIndex) % DIRECTS.length;
            Point direct = DIRECTS[fillDirectionIndex];
            fill(map, point, direct);
        }
    }

    private static void fill(int[][] map, Point point, Point direct) {
        Point currentPoint = point;

        while (isWithinRange(map, currentPoint)) {
            if (map[currentPoint.row][currentPoint.col] == 6) {
                break;
            }

            map[currentPoint.row][currentPoint.col] = -1;

            currentPoint = new Point(currentPoint.row + direct.row, currentPoint.col + direct.col);
        }
    }

    private static boolean isWithinRange(int[][] map, Point point) {
        if (point.row < 0 || map.length <= point.row) {
            return false;
        }
        return point.col >= 0 && map[point.row].length > point.col;
    }

    private static class Point {

        final int row;
        final int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}

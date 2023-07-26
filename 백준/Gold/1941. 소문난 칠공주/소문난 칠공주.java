import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    private static final int SIZE = 5;
    private static char[][] map = new char[SIZE][];
    private static int answer = 0;

    private static final int[][] DIRECTS = new int[][]{
            new int[]{0, 1}, new int[]{0, -1}, new int[]{1, 0}, new int[]{-1, 0}
    };

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int i = 0; i < SIZE; i++) {
            map[i] = reader.readLine().toCharArray();
        }

        combination(new int[7], 0, 0, 7);

        writer.write(String.valueOf(answer));
        reader.close();
        writer.close();
    }

    private static void combination(int[] array, int index, int depth, int left) {
        if (left == 0) {
            submitSeven(array);
            return;
        }
        if (depth == SIZE * SIZE) {
            return;
        }

        array[index] = depth;
        combination(array, index + 1, depth + 1, left - 1);
        combination(array, index, depth + 1, left);
    }

    private static void submitSeven(int[] array) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[7];

        visited[0] = true;
        queue.offer(array[0]);
        int count = 1;
        int leeTeamCount = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int y = current / SIZE;
            int x = current % SIZE;
            if (map[y][x] == 'S') {
                leeTeamCount++;
            }

            for (int[] direct : DIRECTS) {
                int dy = direct[0];
                int dx = direct[1];
                for (int next = 0; next < 7; next++) {
                    int nextY = array[next] / SIZE;
                    int nextX = array[next] % SIZE;
                    if (!visited[next] && y + dy == nextY && x + dx == nextX) {
                        visited[next] = true;
                        queue.add(array[next]);
                        count++;
                    }
                }
            }
        }

        if (count == 7 && leeTeamCount >= 4) {
            answer++;
        }
    }
}

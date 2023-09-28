import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

class Solution {

    public int[][] solution(int[][] rc, String[] operations) {

        // 데크 생성
        Deque<Integer> firstDeque = new ArrayDeque<>();
        Deque<Deque<Integer>> midDeque = new ArrayDeque<>();
        Deque<Integer> lastDeque = new ArrayDeque<>();

        for (int[] row : rc) {
            firstDeque.addLast(row[0]);

            midDeque.addLast(new ArrayDeque<>());
            for (int col = 1; col < row.length - 1; col++) {
                midDeque.getLast().addLast(row[col]);
            }

            lastDeque.addLast(row[row.length - 1]);
        }

        // 연산
        for (String operation : operations) {
            switch (operation) {
                case "ShiftRow" -> shiftRow(firstDeque, midDeque, lastDeque);
                case "Rotate" -> rotate(firstDeque, midDeque, lastDeque);
                default -> throw new IllegalArgumentException("수행할 수 없는 연산입니다.");
            }
        }

        // Deque -> int array
        int[][] result = new int[rc.length][];
        for (int row = 0; row < result.length; row++) {
            result[row] = new int[rc[row].length];

            result[row][0] = firstDeque.removeFirst();

            Deque<Integer> mid = midDeque.removeFirst();
            for (int col = 1; col < rc[row].length - 1; col++) {
                result[row][col] = mid.removeFirst();
            }

            result[row][rc[row].length - 1] = lastDeque.removeFirst();
        }
        return result;
    }

    private void shiftRow(Deque<Integer> firstDeque, Deque<Deque<Integer>> midDeque, Deque<Integer> lastDeque) {
        firstDeque.addFirst(firstDeque.removeLast());
        midDeque.addFirst(midDeque.removeLast());
        lastDeque.addFirst(lastDeque.removeLast());
    }

    private void rotate(Deque<Integer> firstDeque, Deque<Deque<Integer>> midDeque, Deque<Integer> lastDeque) {
        midDeque.getFirst().addFirst(firstDeque.removeFirst());
        lastDeque.addFirst(midDeque.getFirst().removeLast());
        midDeque.getLast().addLast(lastDeque.removeLast());
        firstDeque.addLast(midDeque.getLast().removeFirst());
    }

    public static void main(String[] args) {
        int[][] rc = new int[][]{
                new int[]{1, 2, 3},
                new int[]{4, 5, 6},
                new int[]{7, 8, 9}
        };
        String[] operations = new String[]{
                "Rotate", "ShiftRow"
        };

        Solution solution = new Solution();
        int[][] result = solution.solution(rc, operations);

        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }
}

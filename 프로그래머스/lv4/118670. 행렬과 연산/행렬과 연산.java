import java.util.ArrayDeque;
import java.util.Deque;

/**
 * `ShiftRow`와 `Rotate`를 구현하고 각 방법을 적용해볼까? 행과 열의 길이 50_000 * 50_000 operations의 길이 100_000 최대 50_000 * 50_000 * 100_000
 * 연산이 일어날 수 있어 무리가 있을 것 같다. (10억번의 연산 안에 풀어야 함)
 * <p>
 * operations은 줄일 수 없고 1번씩 수행해야 할테고, 25억개의 원소가 들어있는 맵을 전부 들여보는 형태가 아닌 다른 방법을 찾아봐야겠다.
 */
class Solution {

    public int[][] solution(int[][] rc, String[] operations) {

        // 데크 생성
        Deque<Integer>[] dequeArray = new Deque[rc.length];
        for (int row = 0; row < rc.length; row++) {
            dequeArray[row] = new ArrayDeque<>();
            for (int col = 0; col < rc[row].length; col++) {
                dequeArray[row].addLast(rc[row][col]);
            }
        }

        // 연산 수행
        for (String operation : operations) {
            switch (operation) {
                case "ShiftRow":
                    shiftRow(dequeArray);
                    break;
                case "Rotate":
                    rotate(dequeArray);
                    break;
                default:
                    throw new IllegalArgumentException("정의되지 않은 연산입니다.");
            }
        }

        // 결과 리턴
        int[][] resultRc = new int[dequeArray.length][];
        for (int row = 0; row < dequeArray.length; row++) {
            resultRc[row] = new int[dequeArray[row].size()];
            for (int col = 0; col < resultRc[row].length; col++) {
                resultRc[row][col] = dequeArray[row].removeFirst();
            }
        }
        return resultRc;
    }

    // 최대 100_000 연산
    private void shiftRow(Deque<Integer>[] dequeArray) {
        Deque<Integer> lastDeque = new ArrayDeque<>(dequeArray[dequeArray.length - 1]);

        for (int row = dequeArray.length - 1; row > 0; row--) {
            dequeArray[row] = dequeArray[row - 1];
        }
        dequeArray[0] = lastDeque;
    }

    // 최대 100_000 연산
    private void rotate(Deque<Integer>[] dequeArray) {
        for (int row = dequeArray.length - 1; row > 0; row--) {
            Integer last = dequeArray[row - 1].removeLast();
            dequeArray[row].addLast(last);
        }

        for (int row = 0; row + 1 < dequeArray.length; row++) {
            Integer first = dequeArray[row + 1].removeFirst();
            dequeArray[row].addFirst(first);
        }
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
        solution.solution(rc, operations);
    }
}

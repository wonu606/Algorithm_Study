import java.util.Arrays;
import java.util.Objects;

class Solution {

    private final Point[] DIRECTS = new Point[]{
            new Point(-1, 0),
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1)
    };

    public int solution(int[][] board) {
        Point startPoint = new Point(0, 0);
        Point endPoint = new Point(board.length - 1, board[board.length - 1].length - 1);

        int[][] boardValueMap = new int[board.length][board[board.length - 1].length];
        for (int[] boardValueRow : boardValueMap) {
            Arrays.fill(boardValueRow, Integer.MAX_VALUE);
        }

        buildRoad(board, boardValueMap, startPoint, endPoint, -1, 0);
        return boardValueMap[endPoint.row][endPoint.col];
    }

    private void buildRoad(final int[][] board, final int[][] boardValueMap,
            final Point currentPoint, final Point endPoint,
            final int prevIndex, final int currentValue) {
        if (Objects.equals(currentPoint, endPoint)) {
            boardValueMap[endPoint.row][endPoint.col] = Math.min(
                    boardValueMap[endPoint.row][endPoint.col], currentValue
            );
            return;
        }
        if (currentValue > boardValueMap[currentPoint.row][currentPoint.col]) {
            return;
        }
        boardValueMap[currentPoint.row][currentPoint.col] = currentValue;

        for (int selectedIndex = 0; selectedIndex < DIRECTS.length; selectedIndex++) {
            Point direct = DIRECTS[selectedIndex];
            Point nextPoint = new Point(
                    direct.row + currentPoint.row,
                    direct.col + currentPoint.col);
            if (checkInvalidBounds(nextPoint, endPoint)) {
                continue;
            }
            if (board[nextPoint.row][nextPoint.col] == 1) {
                continue;
            }

            int nextValue = currentValue + 100;
            if (prevIndex != -1 && prevIndex != selectedIndex) {
                nextValue += 500;
            }

            buildRoad(board, boardValueMap, nextPoint, endPoint, selectedIndex, nextValue);
        }
    }

    private boolean checkInvalidBounds(Point pointToCheck, Point maxPoint) {
        int rowToCheck = pointToCheck.row;
        int colToCheck = pointToCheck.col;
        int maxRow = maxPoint.row;
        int maxCol = maxPoint.col;
        if (rowToCheck < 0 || maxRow < rowToCheck) {
            return true;
        }
        return colToCheck < 0 || maxCol < colToCheck;
    }

    private static class Point {

        final int row;
        final int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return row == point.row && col == point.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

//        int[][] board1 = new int[][]{
//                {0, 0, 0},
//                {0, 0, 0},
//                {0, 0, 0}
//        };
//        System.out.println(solution.solution(board1));

//        int[][] board2 = new int[][]{
//                {0, 0, 0, 0, 0, 0, 0, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 1, 0, 0},
//                {0, 0, 0, 0, 1, 0, 0, 0},
//                {0, 0, 0, 1, 0, 0, 0, 1},
//                {0, 0, 1, 0, 0, 0, 1, 0},
//                {0, 1, 0, 0, 0, 1, 0, 0},
//                {1, 0, 0, 0, 0, 0, 0, 0}
//        };
//        System.out.println(solution.solution(board2));

        int[][] board3 = new int[][]{
                {0, 0, 1, 0},
                {0, 0, 0, 0},
                {0, 1, 0, 1},
                {1, 0, 0, 0}
        };
        System.out.println(solution.solution(board3));
    }
}

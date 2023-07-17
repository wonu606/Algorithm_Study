import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {

    int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int size = Integer.parseInt(reader.readLine());
        Integer[][] map = new Integer[size][size];
        for (int i = 0; i < size; i++) {
            map[i] = Arrays.stream(reader.readLine().split(" "))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
        }

        List<Pipe> pipes = new ArrayList<>();

        // 0
        Pipe rightDirectionPipe = new Pipe();
        rightDirectionPipe.nextCoordinate = new Coordinate(0, 1);
        rightDirectionPipe.addRequiredCoordinates(
                new Coordinate(0, 1)
        );
        pipes.add(rightDirectionPipe);

        // 1
        Pipe rightDownDirectionPipe = new Pipe();
        rightDownDirectionPipe.nextCoordinate = new Coordinate(1, 1);
        rightDownDirectionPipe.addRequiredCoordinates(
                new Coordinate(0, 1), new Coordinate(1, 1),
                new Coordinate(1, 0)
        );
        pipes.add(rightDownDirectionPipe);

        // 2
        Pipe downDirectionPipe = new Pipe();
        downDirectionPipe.nextCoordinate = new Coordinate(1, 0);
        downDirectionPipe.addRequiredCoordinates(
                new Coordinate(1, 0)
        );
        pipes.add(downDirectionPipe);

        Coordinate startCoordinate = new Coordinate(0, 1);
        Coordinate endCoordinate = new Coordinate(size - 1, size - 1);
        int result = dfs(map, pipes, 0, startCoordinate, endCoordinate, 0);

        writer.write(String.valueOf(result));

        reader.close();
        writer.close();
    }

    private static Integer dfs(Integer[][] map, List<Pipe> pipes, int prevPipeIndex, Coordinate currentCoordinate, Coordinate endCoordinate, int result) {
        if (Objects.equals(currentCoordinate, endCoordinate)) {
            result++;
            return result;
        }

        Integer nextResult = result;
        for (int currentPipeIndex = 0; currentPipeIndex < pipes.size(); currentPipeIndex++) {
            if (prevPipeIndex == 0 && currentPipeIndex == 2) {
                continue;
            }
            if (prevPipeIndex == 2 && currentPipeIndex == 0) {
                continue;
            }
            Pipe currentPipe = pipes.get(currentPipeIndex);

            if (!validate(map, currentCoordinate, currentPipe)) {
                continue;
            }
            Coordinate nextCoordinate =
                    new Coordinate(currentCoordinate.row + currentPipe.nextCoordinate.row,
                            currentCoordinate.col + currentPipe.nextCoordinate.col);
            nextResult = dfs(map, pipes, currentPipeIndex, nextCoordinate, endCoordinate, nextResult);
        }
        return nextResult;
    }

    private static boolean validate(Integer[][] map, Coordinate currentCoordinate, Pipe selectedPipe) {
        for (Coordinate requiredCoordinate : selectedPipe.requiredCoordinates) {
            Coordinate coordinate = new Coordinate(currentCoordinate.row + requiredCoordinate.row,
                    currentCoordinate.col + requiredCoordinate.col);
            if (!validateRange(map, coordinate)) {
                return false;
            }
            if (map[coordinate.row][coordinate.col] == 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateRange(Integer[][] map, Coordinate coordinate) {
        if (coordinate.row < 0 || map.length <= coordinate.row) {
            return false;
        }
        if (coordinate.col < 0 || map[coordinate.row].length <= coordinate.col) {
            return false;
        }
        return true;
    }

    static class Pipe {

        Coordinate nextCoordinate;
        List<Coordinate> requiredCoordinates = new ArrayList<>();

        void addRequiredCoordinates(Coordinate... coordinates) {
            requiredCoordinates.addAll(Arrays.asList(coordinates));
        }
    }

    static class Coordinate {
        int row;
        int col;

        public Coordinate(int row, int col) {
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
            Coordinate that = (Coordinate) o;
            return row == that.row && col == that.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
}

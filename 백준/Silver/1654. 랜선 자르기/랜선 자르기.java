import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        List<Integer> firstLineList = Arrays.stream(reader.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        int inputLineCount = firstLineList.get(0);
        int minCountOfPiece = firstLineList.get(1);

        List<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < inputLineCount; i++) {
            int inputNumber = Integer.parseInt(reader.readLine());
            numberList.add(inputNumber);
        }

        // 최댓값 구하기
        int maxLength = cutNumbers(numberList, minCountOfPiece);

        // 출력
        writer.write(String.valueOf(maxLength));

        reader.close();
        writer.close();
    }

    private static int cutNumbers(List<Integer> numberList, int minCountOfPiece) {
        long maxLength = numberList.stream()
                .max(Integer::compareTo)
                .orElseThrow();
        long minLength = 1;

        long cuttableMaxLength = maxLength;
        long cuttableMinLength = minLength;
        long resultCuttingLength = 0;
        while (cuttableMinLength <= cuttableMaxLength) {
            long currentCuttingLength = (cuttableMaxLength + cuttableMinLength) / 2;
            long cuttedCount = 0;
            for (Integer number : numberList) {
                cuttedCount += number / currentCuttingLength;
            }

            if (minCountOfPiece <= cuttedCount) {
                cuttableMinLength = currentCuttingLength + 1;
                resultCuttingLength = currentCuttingLength;
            } else {
                cuttableMaxLength = currentCuttingLength - 1;
            }
        }

        return (int) resultCuttingLength;
    }
}

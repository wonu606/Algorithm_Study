import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        String target = reader.readLine();
        String source = reader.readLine();

        // 마지막에서 제외하며 확인
        /*
        A
        BAA

        BA
        BAA
         */
//        StringBuilder current = new StringBuilder(source);
//        while (source.length() < current.length()) {
//            char first = current.charAt(0);
//            if (first == 'B') {
//                current.deleteCharAt(0);
//                current.reverse();
//                continue;
//            }
//            current.deleteCharAt(current.length() - 1);
//        }
        int result = isChangeable(source, target);

        // 출력
        writer.write(String.valueOf(result));

        reader.close();
        writer.close();
    }

    private static int isChangeable(String current, String target) {
        int result = 0;

        if (current.length() == target.length()) {
            if (current.equals(target)) {
                return 1;
            }
            return 0;
        }

        if (current.charAt(0) == 'B') {
            result = isChangeable(new StringBuilder(current.substring(1)).reverse().toString(),
                    target);
        }
        if (result == 1) {
            return result;
        }
        if (current.charAt(current.length() - 1) == 'A') {
            return isChangeable(current.substring(0, current.length() - 1), target);
        }
        return result;
    }
}

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

        boolean result = isChangeable(source, target);

        // 출력
        writer.write(String.valueOf(result ? 1 : 0));
        reader.close();
        writer.close();
    }

    private static boolean isChangeable(String current, String target) {
        if (current.equals(target)) {
            return true;
        }

        if (current.length() == target.length()) {
            return false;
        }

        return performActionIfFirstB(current, target) || performActionIfLastA(current, target);
    }

    private static boolean performActionIfFirstB(String current, String target) {
        if (current.charAt(0) != 'B') {
            return false;
        }
        String actionedString = new StringBuilder(current.substring(1)).reverse().toString();
        return isChangeable(actionedString, target);
    }

    private static boolean performActionIfLastA(String current, String target) {
        if (current.charAt(current.length() - 1) != 'A') {
            return false;
        }
        String actionedString = current.substring(0, current.length() - 1);
        return isChangeable(actionedString, target);
    }
}

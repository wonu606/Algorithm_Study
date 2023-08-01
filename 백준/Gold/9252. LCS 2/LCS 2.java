import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 두개의 문자열 입력
        char[] firstCharArray = reader.readLine().toCharArray();
        char[] secondCharArray = reader.readLine().toCharArray();

        // DP를 이용하여 문자열 비교
        int[][] dp = new int[firstCharArray.length + 1][secondCharArray.length + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                if (firstCharArray[i - 1] == secondCharArray[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        writer.write(dp[dp.length - 1][dp[0].length - 1] + "\n");

        // 문자열 구하기
        String lcsString = getLcsString(firstCharArray, secondCharArray, dp);
        if (!lcsString.isEmpty()) {
            writer.write(lcsString);
        }

        reader.close();
        writer.close();
    }

    private static String getLcsString(char[] firstCharArray, char[] secondCharArray, int[][] dp) {
        if (dp[dp.length - 1][dp[0].length - 1] == 0) {
            return "";
        }


        Stack<Character> lcsStack = new Stack<>();
        int dpIndex1 = dp.length - 1;
        int dpIndex2 = dp[0].length - 1;
        while (dpIndex1 > 0 && dpIndex2 > 0) {
            if ((dp[dpIndex1 - 1][dpIndex2] == dp[dpIndex1][dpIndex2 - 1])
                    && dp[dpIndex1][dpIndex2] > dp[dpIndex1 - 1][dpIndex2]) {
                lcsStack.push(firstCharArray[dpIndex1 - 1]);
                dpIndex1--;
                dpIndex2--;
            } else if (dp[dpIndex1 - 1][dpIndex2] == dp[dpIndex1][dpIndex2]) {
                dpIndex1--;
            } else if (dp[dpIndex1][dpIndex2 - 1] == dp[dpIndex1][dpIndex2]){
                dpIndex2--;
            }
        }

        StringBuilder lcsBuilder = new StringBuilder();
        while (!lcsStack.isEmpty()) {
            lcsBuilder.append(lcsStack.pop());
        }
        return lcsBuilder.toString();
    }
}

import java.lang.Math;

class Solution {
    
    public int solution(int sticker[]) {
        int size = sticker.length;
        if (size == 1) {
            return sticker[0];
        }
        int max1 = calculateMax(sticker, 0, size - 2);
        int max2 = calculateMax(sticker, 1, size - 1);
        
        return Math.max(max1, max2);
    }
    
    private int calculateMax(int sticker[], int minIndex, int maxIndex) {
        if (minIndex + 1 >= sticker.length) {
            return sticker[minIndex];
        }
        int[] dp = new int[sticker.length];
        dp[minIndex] = sticker[minIndex];
        dp[minIndex + 1] = Math.max(sticker[minIndex], sticker[minIndex + 1]);
        
        if (minIndex + 2 >= sticker.length) {
            return Math.max(dp[minIndex], dp[minIndex + 1]);
        }
        for (int i = minIndex + 2; i <= maxIndex; i++) {
            dp[i] = Math.max(dp[i - 2] + sticker[i], dp[i - 1]);
        }
        
        return dp[maxIndex];
    }
}
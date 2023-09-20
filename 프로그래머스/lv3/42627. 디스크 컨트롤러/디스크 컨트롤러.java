import java.util.*;

class Solution {
    public int solution(int[][] jobs) {
        Arrays.sort(jobs, (a, b) -> Integer.compare(a[0], b[0]));
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        
        int currentTime = 0;
        int totalProcessingTime = 0;
        int jobIndex = 0;
        
        while (jobIndex < jobs.length || !pq.isEmpty()) {
            while (jobIndex < jobs.length && jobs[jobIndex][0] <= currentTime) {
                pq.offer(jobs[jobIndex]);
                jobIndex++;
            }
            
            if (!pq.isEmpty()) {
                int[] job = pq.poll();
                currentTime += job[1];
                totalProcessingTime += currentTime - job[0];
            } else {
                currentTime = jobs[jobIndex][0];
            }
        }
        
        return totalProcessingTime / jobs.length;
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        int[][] jobs = new int[][]{
            {0, 3},
            {1, 9},
            {2, 6}};
        System.out.println(sol.solution(jobs));
    }
}
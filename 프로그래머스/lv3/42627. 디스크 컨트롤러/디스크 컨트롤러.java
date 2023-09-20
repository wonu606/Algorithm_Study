class Solution {

    public int solution(int[][] jobs) {
        boolean[] visited = new boolean[jobs.length];
        int currentTime = 0;
        int processingTimes = 0;

        for (int count = 0; count < jobs.length; count++) {
            int currentIndex = -1;
            int minWorkingTime = Integer.MAX_VALUE;

            for (int jobsIndex = 0; jobsIndex < jobs.length; jobsIndex++) {
                if (visited[jobsIndex]) {
                    continue;
                }

                if (currentTime < jobs[jobsIndex][0]) {
                    continue;
                }

                if (minWorkingTime > jobs[jobsIndex][1]) {
                    currentIndex = jobsIndex;
                    minWorkingTime = jobs[jobsIndex][1];
                }
            }

            if (currentIndex == -1) {
                int fastArrivalTime = Integer.MAX_VALUE;
                for (int jobsIndex = 0; jobsIndex < jobs.length; jobsIndex++) {
                    if (visited[jobsIndex]) {
                        continue;
                    }

                    if (fastArrivalTime > jobs[jobsIndex][0]) {
                        currentIndex = jobsIndex;
                        fastArrivalTime = jobs[jobsIndex][0];
                    } else if (fastArrivalTime == jobs[jobsIndex][0]
                            && jobs[currentIndex][1] > jobs[jobsIndex][1]) {
                        currentIndex = jobsIndex;
                    }
                }
            }

            visited[currentIndex] = true;

            currentTime = Math.max(currentTime, jobs[currentIndex][0]);
            int nextTime = currentTime + jobs[currentIndex][1];
            processingTimes += nextTime - jobs[currentIndex][0];

            currentTime = nextTime;
        }

        return processingTimes / jobs.length;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[][] jobs = new int[][]{
                new int[]{0, 3},
                new int[]{10, 1}};

        System.out.println(sol.solution(jobs));
    }
}

class Solution {
    static final int MOD = 1000000007;

    public int numOfWays(int[] nums) {
        int n = nums.length;
        long[][] c = new long[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            c[i][0] = c[i][i] = 1;
            for (int j = 1; j < i; j++) {
                c[i][j] = (c[i - 1][j - 1] + c[i - 1][j]) % MOD;
            }
        }

        return (int) ((solve(nums, c) - 1 + MOD) % MOD);
    }

    private long solve(int[] nums, long[][] c) {
        int n = nums.length;

        if (n <= 2) {
            return 1;
        }

        java.util.List<Integer> left = new java.util.ArrayList<>();
        java.util.List<Integer> right = new java.util.ArrayList<>();

        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[0]) {
                left.add(nums[i]);
            } else {
                right.add(nums[i]);
            }
        }

        int[] l = left.stream().mapToInt(Integer::intValue).toArray();
        int[] r = right.stream().mapToInt(Integer::intValue).toArray();

        long ways = c[n - 1][left.size()];
        ways = ways * solve(l, c) % MOD;
        ways = ways * solve(r, c) % MOD;

        return ways;
    }
}
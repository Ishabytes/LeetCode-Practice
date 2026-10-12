class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];
        int max = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            max = Math.max(max, diff[i]);
        }

        int k = k1 + k2;
        int[] freq = new int[max + 1];

        for (int d : diff) {
            freq[d]++;
        }

        for (int i = max; i > 0 && k > 0; i--) {
            int moves = Math.min(k, freq[i]);
            freq[i] -= moves;
            freq[i - 1] += moves;
            k -= moves;
        }

        long sum = 0;

        for (int i = 1; i <= max; i++) {
            sum += (long) i * i * freq[i];
        }

        return sum;
    }
}
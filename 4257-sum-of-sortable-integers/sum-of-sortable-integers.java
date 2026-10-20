class Solution {
    public int sortableIntegers(int[] nums) {

        int n = nums.length;

        // prefMax[i] = maximum element in nums[0 ... i-1]
        int[] prefMax = new int[n + 1];

        for (int i = 0; i < n; i++) {
            prefMax[i + 1] = Math.max(prefMax[i], nums[i]);
        }

        // suffMin[i] = minimum element in nums[i ... n-1]
        int[] suffMin = new int[n + 1];

        suffMin[n] = Integer.MAX_VALUE;

        for (int i = n - 1; i >= 0; i--) {
            suffMin[i] = Math.min(nums[i], suffMin[i + 1]);
        }

        // prefDrop[i] = number of decreasing pairs
        // from index 0 to i
        int[] prefDrop = new int[n];

        for (int i = 1; i < n; i++) {

            prefDrop[i] = prefDrop[i - 1];

            if (nums[i - 1] > nums[i]) {
                prefDrop[i]++;
            }
        }

        int answer = 0;

        // Check only divisors of n
        for (int k = 1; k <= n / k; k++) {

            if (n % k == 0) {

                if (check(nums, prefMax, suffMin, prefDrop, k)) {
                    answer += k;
                }

                int other = n / k;

                if (other != k) {

                    if (check(nums, prefMax, suffMin, prefDrop, other)) {
                        answer += other;
                    }
                }
            }
        }

        return answer;
    }


    private boolean check(
        int[] nums,
        int[] prefMax,
        int[] suffMin,
        int[] prefDrop,
        int k
    ) {

        int n = nums.length;

        for (int start = 0; start < n; start += k) {

            int end = start + k - 1;

            // Number of decreasing pairs inside this block
            int drops = prefDrop[end] - prefDrop[start];

            // Because the block is cyclic,
            // also check the last element -> first element
            if (nums[end] > nums[start]) {
                drops++;
            }

            // A cyclic rotation of a sorted block
            // can have at most one drop.
            if (drops > 1) {
                return false;
            }

            // Everything before this block must be <=
            // everything from this block onward.
            if (prefMax[start] > suffMin[start]) {
                return false;
            }
        }

        return true;
    }
}
package org.afecioru.leetcode.twosum;

import java.util.*;


public class Solution_02 {
    private final Map<Integer, Set<Integer>> index = new HashMap<>();

    private void buildIndex(int[] nums) {
        for (int i = 0; i < nums.length; ++i) {
            final int j = i;
            var indices = index.computeIfAbsent(nums[i], value -> {
                var idxSet = new HashSet<Integer>();
                idxSet.add(j);
                return idxSet;
            });

            indices.add(i);
        }
    }

    public int[] twoSum(int[] nums, int target) {

        buildIndex(nums);

        for (int i = 0; i < nums.length; ++i) {
            var complement = target - nums[i];
            if (index.containsKey(complement)) {
                var indices = index.get(complement);
                indices.remove(i);
                if (!indices.isEmpty()) {
                    return new int[]{i, indices.iterator().next()};
                }
            }
        }

        throw new IllegalArgumentException("No solution available.");
    }
}

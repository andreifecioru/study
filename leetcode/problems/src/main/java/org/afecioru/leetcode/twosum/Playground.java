package org.afecioru.leetcode.twosum;

public class Playground {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5 ,6 ,7 ,8 ,9};

        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j ++) {
                System.out.printf("[%d ,%d]", i, j - i);
            }
        }
    }
}

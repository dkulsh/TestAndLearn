package Coding;

import kotlin.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class NextGreaterElement {

    public static void main(String[] args) {

//        nextGreaterElement(new int[]{ 2, 4 }, new int[]{ 1, 2, 3, 4 });
        nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2});
    }

    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack<Integer> stack = new Stack<>();
        int M = nums1.length, N = nums2.length;

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = N - 1; i >= 0; i--){

            if (!stack.isEmpty() && stack.peek() < nums2[i]) {
                stack.pop();
            }

            map.put(nums2[i], stack.isEmpty() ? -1: stack.peek());
            stack.push(nums2[i]);
        }

        System.out.println(Arrays.toString(Arrays.stream(nums1).map(map::get).toArray()));
        return null;
    }

//    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {

//        int[] ans = new int[nums1.length];
//
//        Map<Integer, Integer> numToIndexMap = new HashMap<>();
//        for (int i = 0; i < nums2.length; i++){
//            numToIndexMap.put(nums2[i], i);
//        }
//
//        outer:
//        for (int i = 0; i < nums1.length ;i++) {
//
//            Integer index = numToIndexMap.get(nums1[i]);
//            for (int j = index + 1; j < nums2.length ;j++) {
//
//                if (nums2[j] > nums1[i]) {
//                    ans[i] = nums2[j];
//                    continue outer;
//                }
//            }
//
//            ans[i] = -1;
//
//        }
//
//        System.out.println(Arrays.toString(ans));
//        return ans;
//    }
}

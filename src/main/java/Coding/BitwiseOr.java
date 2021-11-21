package Coding;

import java.util.TreeSet;

public class BitwiseOr {

    static TreeSet<Integer> treeSet = new TreeSet<>();
    static int num = 41352;

    public static void main(String[] args) {

        permutation("", String.valueOf(num));

        if (treeSet.isEmpty()) {
            System.out.println("-1");
        }

        System.out.println("Smallest bigger :: " + treeSet.first());

    }

    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) {
            System.out.println(prefix);

            int prefixInt = Integer.valueOf(prefix);
            if (prefixInt > num) {
                treeSet.add(Integer.valueOf(prefixInt));
            }
        }
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
    }

}

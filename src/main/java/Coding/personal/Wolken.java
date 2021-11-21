package Coding.personal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Wolken {

    public static void main(String[] args) {

//        String s = "a";
//        String s = "babad";
//        String s = "cbbd";
        String s = "aacabdkacaa";
//        String s = "tattarrattat";

        List<String> list = new ArrayList<>();
        StringBuilder palindrome = new StringBuilder();
        int highestLength = 0;

        for (int i = 0; i < s.length(); i++ ) {

            for (int offset = 0; (i - offset) >= 0 && (i + offset) < s.length(); offset++){

                if (s.substring(i - offset, i + offset + 1).length() >= highestLength
                        && s.charAt(i - offset) == s.charAt(i + offset) ) {
//                        && s.substring(i - offset, i + offset + 1)
//                        .equals((new StringBuilder(s.substring(i - offset, i + offset + 1))).reverse().toString())) {

                    palindrome.setLength(0);
                    palindrome.append(s.substring(i - offset, i + offset + 1));
                }
            }

            if (palindrome.length() > highestLength) {
                list.add(palindrome.toString());
                highestLength = palindrome.length();
                palindrome.setLength(0);
            }

            if (i != s.length() - 1 && s.charAt(i) == s.charAt(i + 1)) {

                int j = i + 1;

                for (int offset = 0; (i - offset) >= 0 && (j + offset) < s.length(); offset++) {

                    if (s.substring(i - offset, j + offset + 1).length() >= highestLength &&
                        s.substring(i - offset, j + offset + 1)
                            .equals((new StringBuilder(s.substring(i - offset, j + offset + 1))).reverse().toString())) {

                        palindrome.setLength(0);
                        palindrome.append(s.substring(i - offset, j + offset + 1));
                    }
                }
            }

            if (palindrome.length() > highestLength) {
                list.add(palindrome.toString());
                highestLength = palindrome.length();
                palindrome.setLength(0);
            }

        }

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });
        System.out.println(list);
    }

//    public static void main(String[] args) {
//
//        String str = "aacabdkacaa";
//
////        "dabab"
//        StringBuilder builder = new StringBuilder(str);
//        String reverse = builder.reverse().toString();
//
//        List<String> list = new ArrayList<>();
//        StringBuilder palindrome = new StringBuilder();
//
//        for (int loops = 0; loops < str.length() ; loops++) {
//
//            for (int start = 0, end = loops; start < str.length() && end < str.length(); start++) {
//
//                if (str.charAt(start) == reverse.charAt(end)) {
//                    palindrome.append(str.charAt(start));
//                    end++;
//                    continue;
//                }
//
//                if (palindrome.length() > 0) {
//                    list.add(palindrome.toString());
//                    palindrome.setLength(0);
//                }
//            }
//
//            if (palindrome.length() > 0) {
//                list.add(palindrome.toString());
//                palindrome.setLength(0);
//            }
//        }
//
//
//        Collections.sort(list, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o2.length() - o1.length();
//            }
//        });
//        System.out.println(list);
//    }
}
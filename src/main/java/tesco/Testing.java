//package tesco;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class Testing {
//
//    public static void main(String[] args) {
//
//        // Without changing tthe order of the chars in the string, you need to figure out the longest palindrome substring
//
//        // Queue and a Stack
//
//
//
//
//        String[] strArray = { "deep", "deed", "deepak", "kappak", "kapxpak", "kapixjpak", "kapxxxpak"};
//
//        // With available chars, can a palindrome be created
//        Map<Character, Integer> map = new HashMap<>();
//
//        // Map of chars to their numbers in the string
//        for (int i = 0; i < str.length(); i++) {
//            char c = str.charAt(i);
//
//            if (map.containsKey(c)) {
//                int number = map.get(c);
//                map.put(c, ++number);
//            } else {
//                map.put(c, 1);
//            }
//        }
//
//        if (str.length()%2 == 0) {
//            System.out.println(map.keySet().stream().allMatch(c -> map.get(c)%2 == 0));
//        } else {
////            List<Character> evenOccuranceCharsList = map.keySet().stream().filter(c -> map.get(c)/2 == 0).collect(Collectors.toList());
//            List<Character> oddOccuranceCharsList = map.keySet().stream().filter(c -> map.get(c)%2 != 0).collect(Collectors.toList());
//
//            if (oddOccuranceCharsList.size() > 1){
//                System.out.println(false);
//            } else {
//                System.out.println(true);
//            }
//        }
//        // whether numer of chars are even or odd
//            // even - each char should have even copies
//            // odd - one should have 1 copy / others should have even copies
//
//    }
//
//    List<String> list = new ArrayList<>();
//
//    private boolean search(String str, int startIndex, int endIndex){
//
//        String str = "deed";
//        String strReverse = "zdeedcb";
//
//        if (str.charAt(startIndex) == str.charAt(endIndex)){
//
//            if (search(str, ++startIndex, --endIndex)) {
//                list.add(str.substring(++startIndex, --endIndex));
//                return true;
//            }
//        } else {
//
//            if (search(str, ++startIndex, endIndex)) {
//
//            }
//
//            if (search(str, startIndex, ++endIndex));
//        }
//
//        // Compare first and last chars ( b, z )
//        // Match
//        // Move both indexes ( starting by increasing 1, ending by decreasing 1 ) + Save indexes
//        // No match
//        // Move the first char index forward ( c, z )
//        // Call method again
//        // Move last char index backward ( b, d )
//        // Call method again
//
//        // Compare first and last chars ( c, z )
//        // Match
//        // No match
//        // Move the first char index forward ( c, z )
//        // Call method again
//        // Move last char index backward ( b, d )
//        // Call method again
//
//    }
//}

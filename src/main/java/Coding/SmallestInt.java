package Coding;

import java.util.*;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SmallestInt {

    public static void main(String[] args) {

//        int[] arr = { 2, 4, 1, 6, 5, 9, 7 };
//        int[] arr = { 4, 3, 2, 6, 1 };
//        int[] arr = { 2, 1, 6, 4, 3, 7 };

        int[] arr = { 2, 1, 6, 4, 3, 7, 14, 32, 61, 91, 8, 23 };

        int splits = findSplit(arr);

        System.out.println("Total pieces = " + splits);

//        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
//
////        System.out.println(list.indexOf(1));
////        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, 0, 3)));
////        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, 3, arr.length)));
//
//        int smallestIntInArr = 0;
//        int idexSmallest = 0;
//        int split = 0;
//
//        while(arr.length != 0) {
//
//            for (int i = 0; i < arr.length; i++ ) {
//
//                if (i == 0) {
//                    smallestIntInArr = arr[i];
//                    idexSmallest = 0;
//                }
//
//                smallestIntInArr = smallestIntInArr < arr[i] ? smallestIntInArr: arr[i];
//                idexSmallest = smallestIntInArr < arr[i] ? idexSmallest: i;
//
//            }
//
//            split++;
//            arr = Arrays.copyOfRange(arr, idexSmallest + 1, arr.length);
//            System.out.println(Arrays.toString(arr));
////            list = Arrays.stream(arr).boxed().collect(Collectors.toList());
//
//        }
//
//        System.out.println(split);
    }

    static int findSplit(int[] input){

        int smallestIntInArr = 0;
        int smallestIntIndex = 0;
        int split = 0;
        int pieces = 0;

        if(input.length == 0) { return 0; }

        for (int i = 0; i < input.length; i++ ) {

            if (i == 0) {
                smallestIntInArr = input[i];
                smallestIntIndex = 0;
            }

            smallestIntInArr = smallestIntInArr < input[i] ? smallestIntInArr: input[i];
            smallestIntIndex = smallestIntInArr < input[i] ? smallestIntIndex: i;
        }

        if (smallestIntIndex == (input.length - 1)) { return 1; }

        split++;
        pieces = split + 1;

        int leftSplits = findSplit(Arrays.copyOfRange(input, 0, smallestIntIndex + 1));
        if (leftSplits > 0) { leftSplits--; }
        System.out.println("Left Split " + Arrays.toString(Arrays.copyOfRange(input, 0, smallestIntIndex + 1)) + ":: is " + leftSplits);

        int rightSplits = findSplit(Arrays.copyOfRange(input, smallestIntIndex + 1, input.length));
        if (rightSplits > 0) { rightSplits--; }
        System.out.println("Right Split " + Arrays.toString(Arrays.copyOfRange(input, smallestIntIndex + 1, input.length)) + " :: is " + rightSplits);

        return pieces + leftSplits + rightSplits;
    }
}

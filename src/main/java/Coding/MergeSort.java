package Coding;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MergeSort {

    static long count = 0;

    public static void main(String[] args) throws FileNotFoundException {

//        Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(new File("/Users/deep.kulshreshtha/Downloads/merge_sort.txt"));

        int size = scanner.nextInt();
        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = scanner.nextInt();
        }

        findCount(arr);
    }

    private static void findCount(int[] arr) {

        merge_sort(arr, 0, arr.length - 1);
        System.out.println(count);
    }

    private static void merge_sort(int[] arr, int start, int end) {

        if (start < end) {

            int mid = (start + end) / 2;

            merge_sort(arr, start, mid);

            merge_sort(arr, mid + 1, end);

            merge(arr, start, mid, end);
        }
    }

    private static void merge(int[] arr, int start, int mid, int end) {

        int firstPartIndex = start;
        int secondPartIndex = mid + 1;

        int[] temp = new int[end - start + 1];
        int tempArrIndex = 0;

        while(firstPartIndex <= mid && secondPartIndex <= end){

            if (arr[firstPartIndex] > arr[secondPartIndex]) {
//                    for (int j = firstPartIndex; j <= mid; j++) {
//                        count++;
//                    }

                count = count + (mid - firstPartIndex + 1);
                temp[tempArrIndex++] = arr[secondPartIndex++];
            } else {
                temp[tempArrIndex++] = arr[firstPartIndex++];
            }
        }

        while (firstPartIndex <= mid){
            temp[tempArrIndex++] = arr[firstPartIndex++];
        }

        while (secondPartIndex <= end){
            temp[tempArrIndex++] = arr[secondPartIndex++];
        }

//        for (int i = start; i <= end; i++) {
//
//            if (firstPartIndex > mid) {
//                // Reached end of part 1
//                temp[tempArrIndex++] = arr[secondPartIndex++];
//            } else if (secondPartIndex > end) {
//                temp[tempArrIndex++] = arr[firstPartIndex++];
//            } else if (arr[firstPartIndex] < arr[secondPartIndex]) {
//                temp[tempArrIndex++] = arr[firstPartIndex++];
//            } else {
//                if ((arr[firstPartIndex] > arr[secondPartIndex])) {
////                    for (int j = firstPartIndex; j <= mid; j++) {
////                        count++;
////                    }
//                    count = count + (mid - firstPartIndex + 1);
//                }
//                temp[tempArrIndex++] = arr[secondPartIndex++];
//            }
//        }

        for (int j = 0; j < tempArrIndex; j++) {
            arr[start++] = temp[j];
        }
    }
}

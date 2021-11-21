package Coding;

import java.util.Arrays;
import java.util.Scanner;

public class BinarySearchTest {

    public static void main(String[] args) {

        BinarySearchTest binarySrch = new BinarySearchTest();
        binarySrch.readData();
    }

    private void readData() {

        Scanner scanner = new Scanner(System.in);

        int size = scanner.nextInt();
        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = scanner.nextInt();
        }
        Arrays.sort(arr);

        int numQueries = scanner.nextInt();
        for (int i = 0; i < numQueries; i++) {
            binarySearch(scanner.nextInt(), arr);
        }
    }

    private void binarySearch(int key, int[] arr) {

        int low = 0, high = arr.length;
        while (low <= high){

            int mid = (low + high)/ 2;

            if (arr[mid] == key) {
                System.out.println(mid + 1);
                return;
            }

            if (arr[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
    }

}

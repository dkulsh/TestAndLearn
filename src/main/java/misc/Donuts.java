package misc;

import java.util.Scanner;

public class Donuts {

    public static void main(String[] args) {
        // Write your code here
        Scanner sc  = new Scanner(System.in) ;

        int n = sc.nextInt() ;
        int input[] = new int[n] ;
        int i = 0 ;
        while(i < n)
        {
            input[i] = sc.nextInt() ;
            i++ ;
        }
        insertionSort(input) ;
        System.out.println(findMinWeight(input)) ;
    }


    static long findMinWeight(int input[])
    {
        int factorValue = input.length - 1 ;
        long w = 0 ;
        while(factorValue >= 0)
        {
            w = w + (((long)Math.pow(2, factorValue)) * input[factorValue] );
            factorValue-- ;
        }
        return w ;
    }

    static void insertionSort(int input[]){

        for(int i = 1 ; i < input.length; i++)
        {
            int j = i - 1 ;
            int k = input[i] ;
            while(j >= 0)
            {
                if(input[j] <= k)
                {
                    int temp = input[j] ;
                    input[j] = input[j + 1] ;
                    input[j + 1] = temp ;
                }
                j--  ;
            }

        }



    }

}

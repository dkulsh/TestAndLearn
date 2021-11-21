package Coding;

import java.util.Scanner;

public class MergeSort_WorkingSolution {

    public static long merge_sort(int ar[],int l,int r){
        if(l<r){
            int mid=(l+r)/2;
            long a=merge_sort(ar,l,mid);
            long b=merge_sort(ar,mid+1,r);
            long c=merge(ar,l,mid,r);
            return (a+b+c);
        }
        return 0;
    }
    public static long merge(int ar[],int l,int mid,int r){
        int arr[]=new int[r-l+1];
        int k=0;
        long cnt=0;
        int c1=l, c2 = mid+1;

        while(c1<=mid && c2<=r){
            if(ar[c1] > ar[c2]){
                arr[k++]= ar[c2++];
                cnt+=(mid-c1+1);
            }else arr[k++]= ar[c1++];
        }

        while(c1<=mid) arr[k++]=ar[c1++];
        while(c2<=r) arr[k++]=ar[c2++];
        int tt=0;
        for(int i=l;i<=r;i++) ar[i]=arr[tt++];
        return cnt;
    }
    public static void main(String args[] ) throws Exception {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int ar[]=new int[n];
        for(int i=0;i<n;i++) ar[i]=sc.nextInt();
        long s=merge_sort(ar,0,n-1);
        System.out.println(s);

    }
}

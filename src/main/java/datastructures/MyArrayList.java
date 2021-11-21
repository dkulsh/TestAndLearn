package datastructures;

import java.util.ArrayList;
import java.util.Arrays;

public class MyArrayList<T> {

    public static void main(String[] args) {
        MyArrayList list = new MyArrayList();
        for (int i = 0; i < 15; i++) {
            list.add(i);
        }

        list.add(4, 0);
    }

    private Object[] i = new Object[16];

    private int length = 0;

    public T get(int index){
        return (T) i[index];
    }

    public void add(T input) {

        if (length > i.length -1) {
            Object[] newArr = new Object[i.length * 2];
            System.arraycopy(i, 0, newArr, 0, i.length);
            System.out.println(Arrays.toString(newArr));
            i = newArr;
        }

        i[length] = input;
        length++;
        System.out.println(Arrays.toString(i));
    }

    public void add(int index, T input){

        if (length < index) { throw new IndexOutOfBoundsException(); }

        if (length > i.length -1) {
            Object[] newArr = new Object[i.length * 2];
            System.arraycopy(i, 0, newArr, 0, i.length);
            System.out.println(Arrays.toString(newArr));
            i = newArr;
        }

        int[] temp = new int[i.length];
        System.arraycopy(i, index, temp, 0, length - index);
        i[index] = input;
        System.arraycopy(temp, 0, i, index + 1, length - index);
        length++;
        System.out.println(Arrays.toString(i));
    }

    public int length(){
        return length;
    }
}

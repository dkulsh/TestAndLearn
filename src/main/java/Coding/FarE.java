package Coding;

import java.util.LinkedList;
import java.util.List;

public class FarE {

    public static void main(String[] args) {

        List<Integer> firstNum = new LinkedList<>();
        firstNum.add(8);
        firstNum.add(6);
        firstNum.add(9);
        firstNum.add(8);

        List<Integer> secondNum = new LinkedList<>();
        secondNum.add(4);
        secondNum.add(7);
        secondNum.add(3);
        secondNum.add(4);

        List<Integer> result = new LinkedList<>();
        int carry = 0;

        int j = secondNum.size() -1;
        for (int i = firstNum.size() - 1; i >= 0 || j >= 0; i--, j--) {

            int total = firstNum.get(i) + secondNum.get(j) + carry;
            int remain = total % 10;
            carry = total / 10;

            result.add(remain);
        }
        if (carry > 0) { result.add(carry); }

        for (int i = result.size() - 1; i >= 0; i--){
            System.out.print(result.get(i) + " ");
        }

    }
}

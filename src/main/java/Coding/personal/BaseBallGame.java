package Coding.personal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BaseBallGame {

    public static void main(String[] args) {

        String input = "())()()";
        int maxLength = 0;

        Stack<Character> stac = new Stack<>();

        char open = '(';
        char close = ')';

        for (int i = 0; i < input.length(); i++) {
            stac.push(input.charAt(i));
        }

        int loopLength = 0;

        while (! stac.isEmpty()) {

            Character c = stac.pop();
            if (c.equals(close) && !stac.isEmpty() && stac.peek().equals(open)) {
                stac.pop();
                loopLength = loopLength + 2;
            } else {
                loopLength = 0;
            }

            maxLength = Math.max(maxLength, loopLength);

        }

        System.out.println("Max Length = " + maxLength);

    }
}
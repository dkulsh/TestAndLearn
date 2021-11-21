package Coding.access.other;

import Coding.access.Parent;

public class ChildOutsidePackage extends Parent {
    public void testIt(){
        System.out.println("x is : " + x);
    }
}

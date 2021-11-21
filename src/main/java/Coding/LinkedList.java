package Coding;

public class LinkedList {

    public static void main(String[] args) {

        Node six = new Node(6, null);
        Node five = new Node(5, six);
        Node four = new Node(4, five);
        Node three = new Node(3, four);
        Node two = new Node(2, three);
        Node one = new Node(1, two);

        System.out.println(one);
        partition(one, 3);
    }

    static void partition(Node n, int i){

        Node head = n;

        while(n.next != null){ // ??

//            Swap
            for (int a = 1; a < i; a++ ) {

                if (head.data == n.data) { continue; }

                n.next = head;

//                Node next = n.next;
//                n.next = next.next;
//                next.next = n;

            }
        }

    }
}

class Node{

    public Node(int data, Node next) {
        this.data = data;
        this.next = next;
    }

    int data;
    Node next;

    @Override
    public String toString() {

        if (next == null) {
            return String.valueOf(data);
        }
        return data + "-" + next.toString();
    }
}
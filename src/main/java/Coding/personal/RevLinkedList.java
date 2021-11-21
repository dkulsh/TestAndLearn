package Coding.personal;

public class RevLinkedList {

    public static void main(String[] args) {

        Node six = new Node(6, null);
        Node five = new Node(5, six);
        Node four = new Node(4, five);
        Node three = new Node(3, four);
        Node two = new Node(2, three);
        Node one = new Node(1, two);

        System.out.println(one);
        revert(one);
    }

    private static void revert(Node current) {

        Node head = current;
        Node previous, next;

        while (current != null){

            next = current.next;

            current.next = null;
            next.next = current;
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

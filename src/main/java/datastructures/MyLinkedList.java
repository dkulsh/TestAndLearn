package datastructures;

public class MyLinkedList {

    int length;
    Node head, tail;

    public static void main(String[] args) {
        MyLinkedList myLinkedList = new MyLinkedList();

        for (int i = 0; i < 10; i++) {
            myLinkedList.add(i);
        }
        myLinkedList.print();

        myLinkedList.add(11, 9);
        myLinkedList.print();
    }

    public void print(){

        for (Node p = head; p != null; p = p.next) {
            System.out.print(p.data + "->" );
        }
        System.out.println();
    }

    public void add(int data) {
        Node newNode = new Node(data);

        if (length == 0){
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }

        length++;
    }

    public void add(int data, int index){

        Node newNode = new Node(data);

        if (index == 0){
            newNode.next = head;
            head.previous = newNode;
            head = newNode;

        } else if (index == length - 1) {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;

        } else {

            Node existingNode = head;

            for (int i = 0; i < index; i++) {
                existingNode = existingNode.next;
            }

            Node oneBefore = existingNode.previous;

            oneBefore.next = newNode;
            newNode.previous = oneBefore;

            newNode.next = existingNode;
            existingNode.previous = newNode;
        }

        length++;
    }
}

class Node {
    int data;
    Node previous, next;

    public Node(int data) {
        this.data = data;
    }
}

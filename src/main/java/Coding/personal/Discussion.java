package Coding.personal;

import java.util.*;
import java.util.stream.Collectors;

public class Discussion {

    public static void main(String[] args) {

        int[][] connections = new int[][]{{1, 2, 3}, {2, 3}, {3}, {}};
//        int[][] connections = new int[][]{ {1}, {2}, {3, 4}, {4}, {0}};

        System.out.println(hasDeadlock(connections));
    }

    static boolean hasDeadlock(int[][] connections) {

        Map<Integer, NodeLocal> numToObjectMap = new HashMap<>();
        NodeLocal head = null;

//        Created the node graph
        for (int i = 0; i < connections.length; i++) {

            System.out.println(Arrays.toString(connections[i]));

            List<NodeLocal> nextList = Arrays.stream(connections[i]).mapToObj(item -> {
                if (numToObjectMap.containsKey(item)) { return numToObjectMap.get(item); }
                return new NodeLocal(item);
            }).collect(Collectors.toList());

            nextList.forEach(n -> numToObjectMap.put(n.data, n));
            System.out.println(nextList);

            if (numToObjectMap.containsKey(i)) {
                numToObjectMap.get(i).nextElements = nextList;
                continue;
            }

            NodeLocal currentNode = new NodeLocal(i, nextList);
            numToObjectMap.put(i, currentNode);
            if (i == 0) { head = currentNode;}
        }

//        Iterating graph to find the behaviour
        Queue<NodeLocal> queue = new LinkedList<>();
        queue.add(head);

        while (! queue.isEmpty()) {

            NodeLocal mynode = queue.poll();
            if (mynode.traversed == true) { return true; }

            mynode.nextElements.stream().filter(n -> !n.added).forEach(n -> {
                n.added = true;
                queue.add(n);
            });
            mynode.traversed = true;
        }

        return false;
    }
}

class NodeLocal {

    public NodeLocal(int data) {
        this.data = data;
    }

    public NodeLocal(int data, List<NodeLocal> nextElements) {
        this.data = data;
        this.nextElements = nextElements;
    }

    int data;
    List<NodeLocal> nextElements;
    boolean traversed = false;
    boolean added = false;

    @Override
    public String toString() {
        return "NodeLocal{" +
                "data=" + data +
                '}';
    }
}
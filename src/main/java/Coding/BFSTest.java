package Coding;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class BFSTest {

    boolean[] visited;
    int[] levels;
    int level;

    LinkedList<Integer>[] edeges;

    LinkedList<Integer> queue = new LinkedList<>();

    public static void main(String[] args) {

        BFSTest bfsTest = new BFSTest();

        bfsTest.readData();

        bfsTest.bfs();
    }

    private void bfs() {

        queue.add(1);
        visited[1] = true;
        levels[1] = 1;

        while(! queue.isEmpty()) {

            int node = queue.pop();
            for (int i = 0; i < edeges[node].size() ; i++) {

                int childNode = edeges[node].get(i);
                if (!visited[childNode]){
                    visited[childNode] = true;
                    levels[childNode] = levels[node] + 1;
                    queue.add(childNode);
                }
            }
        }

//        System.out.println(Arrays.toString(levels));
        System.out.println(Arrays.stream(levels).filter(l -> l == level).count());
    }

    private int readData() {
        Scanner scanner = new Scanner(System.in);
        int numNodes = scanner.nextInt();

        init(numNodes);

        for (int i = 0; i < numNodes - 1; i++) {
            int edgeOne = scanner.nextInt();
            int edgeTwo = scanner.nextInt();

//            System.out.println(edgeOne + " : " + edgeTwo);
            edeges[edgeOne].add(edgeTwo);
//            System.out.println(Arrays.toString(edeges));
            edeges[edgeTwo].add(edgeOne);
        }

//        System.out.println(Arrays.toString(edeges));
        level = scanner.nextInt();

        return numNodes;
    }

    private void init(int numNodes) {

        visited = new boolean[numNodes + 1];
        levels = new int[numNodes + 1];
        edeges = new LinkedList[numNodes + 1];

        for (int i = 0; i < edeges.length; i++) {
            edeges[i] = new LinkedList<>();
        }
    }
}

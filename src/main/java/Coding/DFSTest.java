package Coding;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class DFSTest {

    static int numNodes, numEdeges, head, count;
    static LinkedList<Integer>[] edeges;
    static boolean[] visited;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        numNodes = scanner.nextInt();
        numEdeges = scanner.nextInt();

        edeges = new LinkedList[numNodes + 1];
        visited = new boolean[numNodes + 1];

        init();

        for (int i = 0; i < numEdeges; i++) {
            int edgeOne = scanner.nextInt();
            int edgeTwo = scanner.nextInt();

//            System.out.println(edgeOne + " : " + edgeTwo);
            edeges[edgeOne].add(edgeTwo);
            edeges[edgeTwo].add(edgeOne);
        }

        head = scanner.nextInt();

//        System.out.println(numNodes + " : " + numEdeges);

        DFS(head);

        for (int i = 1; i < visited.length; i++) {
            if (! visited[i]) { count++; }
        }
        System.out.println(count);
    }

    private static void DFS(int head) {

        visited[head] = true;

        for (int i = 0; i < edeges[head].size(); i++) {

            int node = edeges[head].get(i);
            if (! visited[node]) {
                DFS(node);
            }
        }
    }

    private static void init() {

        for (int i = 0; i < edeges.length; i++) {
            edeges[i] = new LinkedList<>();
        }

        Arrays.fill(visited, false);
    }
}

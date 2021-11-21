import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Walmart {

    static int[][] arr = new int[][]
            {{1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1},
            {1, 0, 0, 1, 1},
            {0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1}};

    static int islandCount;
    static List<Set<Island>> islands = new ArrayList<>();

//    {
//        {0,0}, {0,1} {1 1}
//        {4 0}
//
//    }
//


    public static void main(String[] args) {

        for (int row = 0; row < arr.length; row++){

            for (int column = 0; column < arr.length; column++) {

                if (arr[row][column] != 1) { continue; }

                Island node = new Island(row, column);
                Set<Island> neighbours = findConnectedNeighbours(node);

                if (neighbours.isEmpty()){
                    islandCount++;

                    node.isTravelled = true;
                }
            }
        }


//        Is 1 -> Yes -> find connected 1 (8 neighbours)
//            Not found -> IS ISLAND | Add node to List<Set<Island>> | IslandCount++

//            Found -> 1 or more neighbours. Mark current node = Travelled
//            Add all to Set<Island>
//            Add all NOT TRAVELLED a Queue
//              Pop queue - loop back.
//              Travelled = True

//            Add the Set to List<Set<>>
//            IslandCount++
    }

    private static Set<Island> findConnectedNeighbours(Island node) {

        Set<Island> neighbours = new HashSet<>();

//        Left
        if (node.column == 0) {
//            No left neighbour
        } else {
            if (arr[node.row][node.column-1] == 1) {

                neighbours.add(new Island(node.row, node.column-1));
            }
        }

//        Right
        if (node.column == arr.length) {
//            No Right neighbour
        } else {
            if (arr[node.row][node.column+1] == 1) {

                neighbours.add(new Island(node.row, node.column+1));
            }
        }

//        Above
        if (node.row == 0) {
//            No Above neighbour
        } else {
            if (arr[node.row-1][node.column] == 1) {

                neighbours.add(new Island(node.row-1, node.column));
            }
        }

//        Below
        if (node.row == arr.length) {
//            No Below neighbour
        } else {
            if (arr[node.row+1][node.column] == 1) {

                neighbours.add(new Island(node.row+1, node.column));
            }
        }

        return neighbours;
    }
}

class Island{

    int row;
    int column;
    boolean isTravelled = false;

    public Island(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
package Coding;

import kotlin.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class DeliveryDriver {

    public static void main(String[] args) {

        Location aman = new Location("Aman");
        Location R1 = new Location("Restaurant_1");
        R1.setWaitTime(4);
        Location R2 = new Location("Restaurant_2");
        R2.setWaitTime(8);

        Location C1 = new Location("Customer_1");
        C1.isTravelable = false;
        Location C2 = new Location("Customer_1");
        C2.isTravelable = false;

        aman.nodes.add(new Pair<>(R1, 4));
        aman.nodes.add(new Pair<>(R2, 8));

        R1.nodes.add(new Pair<>(R2, 9));
        R1.nodes.add(new Pair<>(C1, 3));
        R1.nodes.add(new Pair<>(C2, 6));

        R2.nodes.add(new Pair<>(R1, 9));
        R2.nodes.add(new Pair<>(C1, 3));
        R2.nodes.add(new Pair<>(C2, 5));

        C1.nodes.add(new Pair<>(R1, 3));
        C1.nodes.add(new Pair<>(R2, 3));
        C1.nodes.add(new Pair<>(C2, 4));

        C2.nodes.add(new Pair<>(R1, 6));
        C2.nodes.add(new Pair<>(R2, 5));
        C2.nodes.add(new Pair<>(R1, 4));

        List<Location> customers = new ArrayList<>();
        customers.add(C1);
        customers.add(C2);

        Map<Location, Location> restaurantToCustomerMap = new HashMap<>();
        restaurantToCustomerMap.put(R1, C1);
        restaurantToCustomerMap.put(R2, C2);

        Location startLocation = aman;

        printShortestParth(startLocation, customers, restaurantToCustomerMap);
    }

    private static void printShortestParth(Location startLocation, List<Location> customers, Map<Location, Location> restaurantToCustomerMap) {

//        while (true) {
//
//            TravelledLoc travel = new TravelledLoc(startLocation.getSecond(), startLocation.getFirst().name);
//
//            List<Pair<Location, Integer>> travelableNodes = startLocation.getFirst().nodes.stream()
//                    .filter(n -> n.getFirst().isTravelable)
//                    .collect(Collectors.toList());
//
//            travelableNodes.forEach((n) -> {
//                TravelledLoc newTravelNode = new TravelledLoc(travel.totalCost + startLocation.getSecond(), startLocation.getFirst().name);
//                travel.nodes.add(newTravelNode);
//            });
//        }

        while (! customers.isEmpty()) { // until all customers are delivered

//            Find the shortest distance
            Collections.sort(startLocation.nodes, new MinDistanceSort());
            Pair<Location, Integer> closestNode = startLocation.nodes.get(0);
            Location closestLocation = closestNode.getFirst();

            System.out.println("Go to :: " + closestLocation + ". Cost of reaching :: " + Math.max(closestLocation.getWaitTime(), closestNode.getSecond()));

            closestLocation.isTravelable = false;
            restaurantToCustomerMap.computeIfPresent(closestLocation,
                    (K, V) -> { V.isTravelable = true;
                                return V;
                            });

            if (customers.contains(closestLocation)){
                customers.remove(closestLocation);
            }

            startLocation = closestLocation;

        }

    }
}

class MinDistanceSort implements Comparator<Pair<Location, Integer>> {

    @Override
    public int compare(Pair<Location, Integer> o1, Pair<Location, Integer> o2) {

        if (o1.getFirst().isTravelable == o2.getFirst().isTravelable)

            return Math.max(o1.getFirst().getWaitTime(), o1.getSecond()) - Math.max(o2.getFirst().getWaitTime(), o2.getSecond());

        if (o1.getFirst().isTravelable)
            return -1;

        return 1;
    }
}

class Location {

    String name;
    int waitTime;
    boolean isTravelable = true; // location not travelable until the restaurant has delivered
    List<Pair<Location, Integer>> nodes = new ArrayList<>();

    public Location(String name) {
        this.name = name;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return name.equals(location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

class TravelledLoc{

    int totalCost;
    String name;
    List<TravelledLoc> nodes = new ArrayList<>();

    public TravelledLoc(int totalCost, String name) {
        this.totalCost = totalCost;
        this.name = name;
    }
}
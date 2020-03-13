package algorithms.search;

import java.util.PriorityQueue;

/**
 * A BestFirstSearch objects will implement a searching algorithm that implements BFS algorithm with
 * a shortest path with the smallest cost. this class contains a priority queue.
 */

public class BestFirstSearch extends BreadthFirstSearch {

    public BestFirstSearch() {
        super.q = new PriorityQueue<>(new AStateComparator());
    }

    @Override
    public String getName() {
        return "Best First Search";
    }
}

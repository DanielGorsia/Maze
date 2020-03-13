package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A BreadthFirstSearch objects will be implements a searching algorithm that search the shortest path.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {

    Queue<AState> q;

    public BreadthFirstSearch() {
        q = new LinkedList<>();
    }

    @Override
    public Solution solve(ISearchable iSearch) {
        if(iSearch== null){
            return new Solution();
        }

        HashMap<String, AState> states = BFS(iSearch);
        AState goal = iSearch.getGoalState();
        AState currState = states.get(goal.toString());

        Solution sol = new Solution();
        while(currState.prev != null){
            sol.addState(currState);
            currState= currState.prev;
        }
        sol.addState(currState);
        this.sol = sol;

        return sol;
    }

    @Override
    public String getName() {
        return "Breadth First Search";
    }

    /**
     * A BFS method implements the BFS algorithm .
     * @param search - the object that we need to run the algorithm on it.
     * @return all the states we visited and belong the solution.
     */
    private HashMap<String, AState> BFS(ISearchable search){
       AState start = search.getStartState();

        q.add(start);
        AState temp;
        HashMap<String, AState> statesVisited = new HashMap<>();

        while(!q.isEmpty()){
            temp = q.remove();
            ArrayList<AState> neighbors = search.getAllPossibleStates(temp);
            for(int i=0; i<neighbors.size(); i++){
                if(!statesVisited.containsKey(neighbors.get(i).toString())) {
                    neighbors.get(i).prev = temp;
                    neighbors.get(i).cost = neighbors.get(i).cost+ temp.cost;
                    statesVisited.put(neighbors.get(i).toString(), neighbors.get(i));
                    q.add(neighbors.get(i));
                }
            }
        }
        return statesVisited;
    }
}

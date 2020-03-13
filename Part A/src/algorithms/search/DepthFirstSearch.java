package algorithms.search;

import java.util.*;

/**
 * A DepthFirstSearch objects will be implements a searching algorithm.
 */

public class DepthFirstSearch extends ASearchingAlgorithm {
    HashMap<String, AState> statesVisited ;

    public DepthFirstSearch() {
        this.statesVisited = new HashMap<>();
    }

    @Override
    public Solution solve(ISearchable iSearch) {
        DFS(iSearch, iSearch.getStartState());
        Solution sol = new Solution ();
        AState goal = iSearch.getGoalState();
        AState currState = statesVisited.get(goal.toString());
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
        return "Depth First Search";
    }

    /**
     * A BFS method implements the BFS algorithm .
     * @param search - the object that we need to run the algorithm on it.
     * @param currState - a node that we will start the search
     */
    private void DFS(ISearchable search, AState currState) {
        if(search==null || currState == null){
            return;
        }
        ArrayList<AState> neighbors ;
        Stack<AState> s = new Stack<>();
        s.push(currState);
        while (!s.empty()){
            currState = s.pop();
            if (!statesVisited.containsKey(currState.toString())) {
                statesVisited.put(currState.toString(), currState);
                neighbors = search.getAllPossibleStates(currState);
                for(int i=0; i<neighbors.size(); i++){
                    if (!statesVisited.containsKey(neighbors.get(i).toString())){
                        neighbors.get(i).prev = currState;
                        s.push(neighbors.get(i));
                    }

                }
            }
        }

    }
}

package algorithms.search;

import java.util.ArrayList;

/**
 * The ISearchable interface is used to represent a problem that can be solved by iSearchableAlgorithms.
 */

public interface ISearchable {

    /**
     * @return the start state of the problem
     */
    public AState getStartState();
    /**
     * @return the goal state of the problem
     */
    public AState getGoalState();

    /**
     * The method finds all the states we can reach from the state s.
     * @param s - the current state.
     * @return a list of all of the states we can move from s.
     */
    public ArrayList<AState> getAllPossibleStates(AState s);
}

package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A Solution objects will be a list of AState objects that represents a solution.
 */
public class Solution implements Serializable {

    private ArrayList<AState> solution;

    public Solution() {
        this.solution = new ArrayList<>();
    }

    /**
     * getSolutionPath is a method that return the solution path.
     * @return the solution path.
     */
    public ArrayList<AState> getSolutionPath (){
        return solution;
    }

    /**
     * An addState method adds an AState object to the solution.
     * @param s
     */
    public void addState (AState s){
        if(s == null){
            return;
        }
        solution.add(0,s);
    }
}

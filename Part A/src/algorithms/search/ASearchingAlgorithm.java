package algorithms.search;

/**
 * An ASearchingAlgorithm objects will be the objects that implements a searching algorithm.
 */

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    protected Solution sol;

    /**
     * solve is the method that solves with searching algorithm.
     * @param iSearch - the object that we need to solve.
     * @return solution object
     */
    @Override
    public abstract Solution solve(ISearchable iSearch);

    /**
     * getName is the method that return the name of algorithm
     * @return name of the algorithm we use.
     */
    @Override
    public abstract String getName();

    /**
     * getNumberOfNodesEvaluated is the method that evaluate the number of nodes in the solution
     * @return number of nodes in the solution
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return sol.getSolutionPath().size();
    }
}

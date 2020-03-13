package algorithms.search;

/**
 * The ISearchingAlgorithm interface is used to represent a searching algorithm.
 */
public interface ISearchingAlgorithm {

    /**
     * solve is the method that solves with searching algorithm.
     * @param iSearch - the object that we need to solve.
     * @return solution object
     */
    public Solution solve (ISearchable iSearch);
    public String getName();
    public int getNumberOfNodesEvaluated();
}

package algorithms.search;

import java.util.Comparator;

/**
 * An AStateComparator objects will be compare between two AState objects.
 */
public class AStateComparator implements Comparator<AState> {
    @Override
    public int compare(AState o1, AState o2) {
        return o1.cost-o2.cost;
    }
}

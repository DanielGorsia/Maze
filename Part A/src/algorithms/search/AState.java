package algorithms.search;

import java.io.Serializable;

/**
 * An AState object presents a node with his cost and his the previous node.
 */
public abstract class AState implements Serializable {

    protected AState prev;
    protected Object curr;
    protected int cost;

    public AState(Object o, int cost) {
        this.prev = null;
        this.curr = o;
        this.cost = cost;
    }

    /**
     * getData is a method that return the current node
     * @return the current node
     */
    public Object getData (){
        return curr;
    }

    @Override
    public String toString() {
        return curr.toString();
    }
}

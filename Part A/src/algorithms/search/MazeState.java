package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.io.Serializable;

public class MazeState extends AState implements Serializable {

    public MazeState(Position p, int cost ) {
        super(p, cost);
    }

}

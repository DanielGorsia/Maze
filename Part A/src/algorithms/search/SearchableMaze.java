package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The SearchableMaze is used to convert the Maze into a solvable problem which we can solve with ISearchingAlgorithms.
 */

public class SearchableMaze implements ISearchable, Serializable {

    private Maze maze;

    public SearchableMaze(Maze m) {
        maze = m;
    }

    @Override
    public AState getStartState() {
        return new MazeState(maze.getStartPosition(),2);
    }

    @Override
    public AState getGoalState() {
        return new MazeState(maze.getGoalPosition(),2);
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        ArrayList <AState> possibleStates = new ArrayList<>();
        if(s == null){
            return possibleStates;
        }

        Position p = (Position)s.getData();
        Position pTemp = new Position(p.getRowIndex() + 1, p.getColumnIndex());
        boolean down = addNeighbor(possibleStates, pTemp,  2);

        pTemp = new Position(p.getRowIndex() - 1, p.getColumnIndex());
        boolean up = addNeighbor(possibleStates, pTemp, 2);

        pTemp = new Position(p.getRowIndex() , p.getColumnIndex()+1);
        boolean right = addNeighbor(possibleStates, pTemp,  2);

        pTemp = new Position(p.getRowIndex(), p.getColumnIndex()-1);
        boolean left = addNeighbor(possibleStates, pTemp,  2);

        pTemp = new Position(p.getRowIndex()-1,p.getColumnIndex()+1);

        if(up || right){
            addNeighbor(possibleStates, pTemp,  3);
        }
        pTemp =new Position (p.getRowIndex()+1, p.getColumnIndex()+1);
        if(down|| right){
            addNeighbor(possibleStates, pTemp,  3);
        }
        pTemp =new Position (p.getRowIndex()+1, p.getColumnIndex()-1);
        if(down|| left){
            addNeighbor(possibleStates, pTemp,  3);
        }
        pTemp =new Position (p.getRowIndex()-1, p.getColumnIndex()-1);
        if(up|| left){
            addNeighbor(possibleStates, pTemp,  3);
        }

        return possibleStates;
    }

    private boolean isInTheRange (Position p){
        if(p.getRowIndex()<0 || p.getRowIndex()>maze.getRows()-1 || p.getColumnIndex()<0 || p.getColumnIndex()>maze.getColumns()-1){
            return false;
        }
        return true;
    }

    private boolean addNeighbor (ArrayList <AState> neighbors, Position p, int cost){
        if(isInTheRange(p)&& maze.getCellValue(p)==0){
            neighbors.add(new MazeState(p, cost));
            return true;
        }
        return false;
    }

}

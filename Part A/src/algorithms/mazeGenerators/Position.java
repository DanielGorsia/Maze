package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * The Position object represents a specific Position in the maze.
 * Every Position is a complex of row number and a column number. 
 */

public class Position implements Serializable {

    private int row, col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRowIndex (){
        return row;
    }

    public int getColumnIndex(){
        return col;
    }

    @Override
    public String toString() {
        return "{" + row + "," + col + '}';
    }
}

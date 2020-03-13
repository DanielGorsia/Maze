package algorithms.mazeGenerators;

import java.util.ArrayList;

/**
 * MyMazeGenerator class makes complicated mazes.
 * The creation of the maze is by "Prim Algorithm".
 */

public class MyMazeGenerator extends AMazeGenerator {

    /**
     * The method creates the phase of the maze.
     * @param rows, columns - the size of the maze
     * @return a complicated Maze which built by "Prim Algorithm"
     */
    @Override
    public Maze generate(int rows, int columns) {
        Maze myMaze;
        if(rows<=0 || columns<=0){// in case of illegal input, the size of default maze will be 1X1
            myMaze= new Maze (1,1);
        }
        else{
            myMaze = new Maze(rows, columns);
        }
        ArrayList<Position> neighbors = new ArrayList<>();
        Position p = myMaze.getGoalPosition();
        myMaze.setCell(p.getRowIndex(),p.getColumnIndex(),0);
        addNeighbors(myMaze, neighbors, p);

        while (neighbors.size() > 0){
            int index = (int) (Math.random () * neighbors.size());
            p = neighbors.get(index);
            if (numOfZeroNeighbors(myMaze, p)==1){ // only the cells with exactly one "0" neighbor is a cell which splits between a cell in the maze and a cell outside of the maze.
                myMaze.setCell(p.getRowIndex(), p.getColumnIndex(), 0); // break a wall
                addNeighbors(myMaze, neighbors, p);
            }
            neighbors.remove(index);
        }

        for(int i=0; i<myMaze.getColumns(); i++){ //choose the start position.
            if(myMaze.getCellValue(0,i)==0){
                myMaze.setStartPosition(0,i);
                break;
            }
        }

        return myMaze;
    }

    /**
     * the method adds all the neighbors of a position in a maze which are walls (are not in the maze).
     * @param maze - the maze
     * @param list - the list which we add the neighbours to.
     * @param p - the position
     */
    private void addNeighbors (Maze maze, ArrayList<Position> list, Position p){
        if (p.getRowIndex()+1<maze.getRows() && maze.getCellValue(p.getRowIndex()+1, p.getColumnIndex())==1){
            Position n  = new Position (p.getRowIndex()+1, p.getColumnIndex());
            if(numOfZeroNeighbors(maze, n)==1)
                list.add(n);
        }
        if (p.getRowIndex()-1>=0 && maze.getCellValue(p.getRowIndex()-1, p.getColumnIndex())==1){
            Position n  = new Position (p.getRowIndex()-1, p.getColumnIndex());
            if(numOfZeroNeighbors(maze, n)==1)
                list.add(n);
        }
        if (p.getColumnIndex()+1<maze.getColumns() && maze.getCellValue(p.getRowIndex(), p.getColumnIndex()+1)==1){
            Position n  = new Position (p.getRowIndex(), p.getColumnIndex()+1);
            if(numOfZeroNeighbors(maze, n)==1)
                list.add(n);
        }
        if (p.getColumnIndex()-1>=0 && maze.getCellValue(p.getRowIndex(), p.getColumnIndex()-1)==1){
            Position n  = new Position (p.getRowIndex(), p.getColumnIndex()-1);
            if(numOfZeroNeighbors(maze, n)==1)
                list.add(n);
        }
    }

    /**
     * The method counts how many neighbors with the value "0" a specific position a specific maze has.
     * @param maze - the maze
     * @param p - the specific position
     * @return how many neighbors with the value "0" p has.
     */
    private int numOfZeroNeighbors (Maze maze, Position p){
        int counter = 0;
        if (p.getRowIndex()+1<maze.getRows() && maze.getCellValue(p.getRowIndex()+1, p.getColumnIndex())==0){
            counter++;
        }
        if (p.getRowIndex()-1>=0 && maze.getCellValue(p.getRowIndex()-1, p.getColumnIndex())==0){
            counter++;
        }
        if (p.getColumnIndex()+1<maze.getColumns() && maze.getCellValue(p.getRowIndex(), p.getColumnIndex()+1)==0){
            counter++;
        }
        if (p.getColumnIndex()-1>=0 && maze.getCellValue(p.getRowIndex(), p.getColumnIndex()-1)==0){
            counter++;
        }
        return counter;
    }

}

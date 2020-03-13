package algorithms.mazeGenerators;

/**
 * the class EmptyMazeGenerator creates phases of empty mazes.
 * an empty maze is a maze without walls.
 */

public class EmptyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int rows, int columns) {
        Maze eMaze;
        if(rows<=0 || columns<=0){// in case of illegal input, the size of default maze will be 1X1
             eMaze= new Maze (1,1);
        }
        else{
             eMaze = new Maze(rows, columns);
        }
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                eMaze.setCell(i, j, 0);
            }
        }
        return eMaze;
    }


}

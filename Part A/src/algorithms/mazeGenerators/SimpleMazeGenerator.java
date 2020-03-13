package algorithms.mazeGenerators;

/**
 *  the class SimpleMazeGenerator creates phases of a random Maze with a simple solution.
 */

public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     * The method creates the phase of the simple Maze.
     * @param rows, columns - the size of the maze
     * @return a simple random maze.
     */
    @Override
    public Maze generate(int rows, int columns) {
        Maze sMaze ;
        if(rows<=0 || columns<=0){// in case of illegal input, the size of default maze will be 1X1
            sMaze= new Maze (1,1);
        }
        else{
            sMaze = new Maze(rows, columns);
        }
        double rand;
        int currRow = sMaze.getStartPosition().getRowIndex();
        int currCol = sMaze.getStartPosition().getColumnIndex();
        sMaze.setCell(currRow,currCol, 0);

        while(currRow < sMaze.getGoalPosition().getRowIndex()|| currCol < sMaze.getGoalPosition().getColumnIndex())
        { //while we are still before the goal position
            rand = Math.random();
            if (rand < 0.5 && currRow < sMaze.getGoalPosition().getRowIndex()){
                currRow++;
                sMaze.setCell(currRow,currCol, 0);
            }
            else if(currCol < sMaze.getGoalPosition().getColumnIndex()) {
                currCol++;
                sMaze.setCell(currRow,currCol, 0);
            }
        }
        randomWalls(sMaze);
        return sMaze;
    }

    /**
     * the method spreads random walls in the maze.
     * @param m - the maze we want to spread the walls in.
     */
    private void randomWalls (Maze m){
        for (int i=0; i<m.getRows(); i++){
            for (int j=0; j<m.getColumns(); j++){
                if (m.getCellValue(i,j) == 1){
                    double rand = Math.random();
                    if (rand <0.5){
                        m.setCell(i, j, 0);
                    }
                    else {
                        m.setCell(i, j, 1);
                    }
                }
            }
        }
    }
}


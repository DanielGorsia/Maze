package algorithms.mazeGenerators;

/**
 * An AMazeGenerator objects will be the objects that create mazes.
 */

public abstract class AMazeGenerator implements IMazeGenerator {
    /**
     * generate is the method that creates the maze.
     * @param rows, columns - the size of the maze we want to create
     * @return a Maze object, created by the spesific terms of the specific generator.
     */
    @Override
    public abstract Maze generate(int rows, int columns);

    /**
     * the method measures the time it takes to generate a specific maze.
     * @param rows, columns- the size of the maze
     * @return the time it took to create the maze in milliseconds.
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        generate(rows, columns);
        endTime = System.currentTimeMillis();
        return endTime-startTime;
    }


}

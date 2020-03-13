package algorithms.mazeGenerators;

/**
 * the IMazeGenerator Interface helps us to control the creation of the Maze phrases.
 */

public interface IMazeGenerator {

    /**
     * generate is the method that creates the maze.
     * @param rows, columns - the size of the maze we want to create
     * @return a Maze object, created by the spesific terms of the specific generator.
     */
    Maze generate (int rows, int columns);

    /**
     * the method measures the time it takes to generate a specific maze.
     * @param rows, columns- the size of the maze
     * @return the time it took to create the maze in milliseconds.
     */
    long measureAlgorithmTimeMillis (int rows, int columns);

}

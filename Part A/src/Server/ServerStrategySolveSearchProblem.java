package Server;

import java.io.*;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import static Server.Server.Configuration.*;

public class ServerStrategySolveSearchProblem implements ISreverStrategy {
    @Override
    public String serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        Solution sol;

        try {
            ObjectInputStream inputO = new ObjectInputStream(inFromClient);
            ObjectOutputStream outputO = new ObjectOutputStream(outToClient);
            outputO.flush();
            Maze maze = (Maze) inputO.readObject();
            byte[] mazeByteArray = maze.toByteArray();
            String mazeName = mazeByteArray.toString();
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            File f = new File(tempDirectoryPath, mazeName);
            if(f.exists()){
                FileInputStream fIn = new FileInputStream (f);
                ObjectInputStream objIn = new ObjectInputStream(fIn);
                sol = (Solution) objIn.readObject();
                fIn.close();
                objIn.close();
            }
            else{
                f.createNewFile();
                ISearchable searchableMaze = new SearchableMaze(maze);
                ISearchingAlgorithm searchingAlgorithm = getSearchingAlgorithmProp();
                sol = searchingAlgorithm.solve(searchableMaze);
                FileOutputStream fOut = new FileOutputStream(f);
                ObjectOutputStream objOut = new ObjectOutputStream(fOut);
                objOut.writeObject(sol);
                fOut.flush();
                fOut.close();
                objOut.flush();
            }
            outputO.writeObject(sol);
            outputO.flush();
            return "Maze solve successfully by ServerStrategySearchProblem, with the searching algorithm: " + getSearchingAlgorithmPropString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

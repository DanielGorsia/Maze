package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static Server.Server.Configuration.getSizeOfThreadPoolProp;

public class Server {
    private int port;
    private int listeningInterval;
    private ISreverStrategy serverStrategy;
    private volatile boolean stop;
    private ExecutorService pool;

    private static final Logger LOG = LogManager.getLogger();


    public Server(int port, int listeningInterval, ISreverStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }

    public void start(){

        new Thread(()-> runServer()).start();
    }

    private void runServer() {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
            LOG.info(String.format("Server starter at %s!", serverSocket));
            LOG.info(String.format("Server's  strategy: %s", serverStrategy.getClass().getSimpleName()));
            LOG.info("Server is waiting for clients");
            this.pool = Executors.newFixedThreadPool(getSizeOfThreadPoolProp());
            while (!stop){
                try{
                    Socket clientSocket = serverSocket.accept();
                    LOG.info(String.format("Client accepted %s!", clientSocket));
                    pool.execute( new Thread (()-> {
                        handleClient(clientSocket);
                        LOG.info(String.format("Finished handle client: %s!", clientSocket));
                    }));
                }catch(SocketTimeoutException e){
                    LOG.debug("Socket timeout - no clients pending!");
                }
            }
            pool.shutdown();
            serverSocket.close();
        }catch(IOException e){
            LOG.error("IOException", e);
        }
    }

    private void handleClient(Socket clientSocket) {
        try{
            LOG.info(String.format("Handling client with socket: %s!", clientSocket.toString()));
            String message = serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            LOG.info(message);
            clientSocket.close();
        }catch (IOException e){
            LOG.error("IOException", e);
        }
    }

    public void stop(){
        LOG.info("Stopping server...");
        stop = true;
    }


    public static class Configuration {

        public static int getSizeOfThreadPoolProp() {
            int res = 0;
            try (InputStream input = Server.class.getClassLoader().getResourceAsStream("config.properties")) {
                Properties prop = new Properties();
                prop.load(input);
                res = Integer.parseInt(prop.getProperty("ThreadPoolSize"));

            } catch (Exception e) {
                e.printStackTrace();
            }

            return res;
        }

        public static AMazeGenerator getMazeGeneratorProp() {
            AMazeGenerator mazeGenerator = null;
            try (InputStream input = Server.class.getClassLoader().getResourceAsStream("config.properties")) {
                Properties prop = new Properties();
                prop.load(input);
                String mazeG = prop.getProperty("MazeGenerator");
                if (mazeG.equals("MyMazeGenerator")) {
                    mazeGenerator = new MyMazeGenerator();
                } else if (mazeG.equals("SimpleMazeGenerator")) {
                    mazeGenerator = new SimpleMazeGenerator();
                } else if (mazeG.equals("EmptyMazeGenerator")) {
                    mazeGenerator = new EmptyMazeGenerator();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mazeGenerator;
        }

        public static ISearchingAlgorithm getSearchingAlgorithmProp() {
            ISearchingAlgorithm searchA = null;
            try (InputStream input = Server.class.getClassLoader().getResourceAsStream("config.properties")) {
                Properties prop = new Properties();
                prop.load(input);

                String search = prop.getProperty("SearchingAlgorithm");
                if (search.equals("DepthFirstSearch")) {
                    searchA = new DepthFirstSearch();
                } else if (search.equals("BreadthFirstSearch")) {
                    searchA = new BreadthFirstSearch();
                } else if (search.equals("BestFirstSearch")) {
                    searchA = new BestFirstSearch();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return searchA;
        }

        public static String getGeneratorPropString() {
            String mazeG = "";
            try (InputStream input = Server.class.getClassLoader().getResourceAsStream("config.properties")) {
                Properties prop = new Properties();
                prop.load(input);
                mazeG = prop.getProperty("MazeGenerator");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mazeG;
        }

        public static String getSearchingAlgorithmPropString() {
            String searchA = "";
            try (InputStream input = Server.class.getClassLoader().getResourceAsStream("config.properties")) {
                Properties prop = new Properties();
                prop.load(input);
                searchA = prop.getProperty("SearchingAlgorithm");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return searchA;
        }

    }
}


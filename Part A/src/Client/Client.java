package Client;

import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy clientStrategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.clientStrategy = clientStrategy;
    }

    public void start(){
        try{
            Socket theServer = new Socket(serverIP, serverPort);
            clientStrategy.clientStrategy(theServer.getInputStream(), theServer.getOutputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void communicateWithServer(){
        start();
    }
}

package Server;

import java.io.InputStream;
import java.io.OutputStream;

public interface ISreverStrategy {
    String serverStrategy(InputStream inFromClient, OutputStream outToClient);

}

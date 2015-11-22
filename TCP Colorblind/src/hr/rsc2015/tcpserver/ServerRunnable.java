package hr.rsc2015.tcpserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by nicba on 21/11/2015.
 */
public class ServerRunnable implements Runnable {
    private ServerSocket serverSocket;
    ArrayList<Client> clients = new ArrayList<Client>();

    public ServerRunnable(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            Socket connectionSocket = null;
            try {
                connectionSocket = serverSocket.accept();
                clients.add(new Client(connectionSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

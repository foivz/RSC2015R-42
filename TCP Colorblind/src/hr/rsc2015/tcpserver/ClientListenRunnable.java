package hr.rsc2015.tcpserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by nicba on 21/11/2015.
 */
public class ClientListenRunnable implements Runnable {
    BufferedReader inFromClient;
    ListenInterface listenInterface;
    Client client;

    public ClientListenRunnable(BufferedReader inFromClient, Client client) {
        this.inFromClient = inFromClient;
        listenInterface = client;
        this.client = client;
    }

    @Override
    public void run() {
        System.out.print(client.socket == null);
        while (!client.socket.isClosed()) {
            try {
                listenInterface.received(inFromClient.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface ListenInterface {
        public void received(String msg);
    }
}

package hr.rsc2015.tcpserver;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by nicba on 21/11/2015.
 */
public class Client implements ClientListenRunnable.ListenInterface {
    public Socket socket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    ClientListenRunnable clientListenRunnable;
    Thread clientListenThread;
    public ArrayList<String> dispatchQueue = new ArrayList<String>();
    Player p;

    public Client(Socket socket) {
        System.out.println("Eto novog dubreta");
        this.socket = socket;
        try {
            clientListenRunnable = new ClientListenRunnable(new BufferedReader(new InputStreamReader(socket.getInputStream())), this);
            clientListenThread = new Thread(clientListenRunnable);
            clientListenThread.start();
            outToClient = new DataOutputStream(socket.getOutputStream());
            while (!socket.isClosed()) {
                if (dispatchQueue.size() > 0) {
                    System.out.println(dispatchQueue.get(0));
                    outToClient.write((dispatchQueue.get(0) + "\n").getBytes());
                    dispatchQueue.remove(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void received(String msg) {
        System.out.println(msg);
        switch (Integer.parseInt(msg.split(":")[0])) {
            case 0: {
                Gson gson = new Gson();
                dispatchQueue.add(gson.toJson(TcpServer.games));
                break;
            }
            case 1: {
                String gameId = msg.split(":")[1];
                Game game = null;
                for (Game g :
                        TcpServer.games) {
                    if (g.id.equalsIgnoreCase(gameId))
                        game = g;
                }
                if (game == null) {
                    dispatchQueue.add("404");
                    return;
                } else {
                    switch (Integer.parseInt(msg.split(":")[2])) {
                        case 1: {
                            p = new Player(this);
                            game.team1.add(p);
                            break;
                        }
                        case 2: {
                            p = new Player(this);
                            game.team2.add(p);
                            break;
                        }
                    }
                }
                break;
            }
            case 2: {
                if (p != null) {
                    switch (Integer.parseInt(msg.split(":")[1])) {
                        case 1: {
                            p.ready = true;
                            break;
                        }
                        case 2: {
                            p.ready = false;
                            break;
                        }
                    }
                } else {
                    System.out.println("P is null");
                }
                break;
            }
            case 3: {
                if (p != null) {
                    p.lat = Float.parseFloat(msg.split(":")[1].split(";")[0]);
                    p.lon = Float.parseFloat(msg.split(":")[1].split(";")[1]);
                }
                dispatchQueue.add("a" + TcpServer.getServerData());
                break;
            }
            case 4: {
                dispatchQueue.add(TcpServer.getServerData());
                break;
            }
            case 5: {
                Game game = new Game();
                TcpServer.games.add(game);
                dispatchQueue.add("b" + game.id);
                break;
            }
        }
    }
}

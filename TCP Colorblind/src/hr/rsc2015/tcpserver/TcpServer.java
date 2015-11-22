package hr.rsc2015.tcpserver;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.omg.CORBA.NameValuePair;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicba on 21/11/2015.
 */
public class TcpServer {
    ServerSocket serverSocket;
    public static int port = 31337;
    private Thread serverThread;
    public static ArrayList<Game> games = new ArrayList<Game>();
    Game g = new Game();

    public TcpServer() {
        String clientSentence;
        String capitalizedSentence;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.id = "ID";
        games.add(g);
        serverThread = new Thread(new ServerRunnable(serverSocket));
        serverThread.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!serverSocket.isClosed()) {
                    try {
                        CloseableHttpClient httpclient = HttpClients.createDefault();
                        HttpPost httppost = new HttpPost("http://192.168.10.10/API/location.php");
                        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(1);
                        params.add(new BasicNameValuePair("data", getServerData()));
                        System.out.println(getServerData());
                        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                        //httpclient.execute(httppost);
                        for (Player p :
                                g.team1) {
                            p.client.dispatchQueue.add("a" + getServerData());
                        }
                        for (Player p :
                                g.team2) {
                            p.client.dispatchQueue.add("a" + getServerData());
                        }
                        Thread.sleep(1000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static String getServerData() {
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        for (Game g : TcpServer.games) {
            sb.append(g.id + ";");
            for (Player p :
                    g.team1) {
                sb.append(p.toAString() + "€");
            }
            StringBuilder sb1 = new StringBuilder();
            sb1.append(sb.toString().substring(0, sb.toString().length() - 1));
            sb1.append(";");
            for (Player p :
                    g.team2) {
                sb.append(p.toAString() + "€");
            }
            sb2.append(sb1.toString().substring(0, sb1.toString().length() - 1));
            sb2.append("*");
        }
        return sb2.toString().substring(0, sb2.toString().length() - 1);
    }
}

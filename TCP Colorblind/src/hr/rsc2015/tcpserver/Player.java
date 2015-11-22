package hr.rsc2015.tcpserver;

/**
 * Created by nicba on 21/11/2015.
 */
public class Player {
    Client client;
    int kills = 0;
    int deaths = 0;
    boolean ready;
    float lat, lon;
    Game game;

    public Player(Client client) {
        this.client = client;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public boolean isReady() {
        return ready;
    }

    public String toAString() {
        StringBuilder sb = new StringBuilder();
        sb.append(client.socket.getInetAddress().toString());
        sb.append("$");
        sb.append(kills + "?" + deaths);
        sb.append("$");
        sb.append(lat + "?" + lon);
        sb.append("$" + (ready ? 1 : 0));
        return sb.toString();
    }
}

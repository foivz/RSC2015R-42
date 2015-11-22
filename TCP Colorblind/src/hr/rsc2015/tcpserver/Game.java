package hr.rsc2015.tcpserver;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nicba on 21/11/2015.
 */
public class Game {
    int maxPlayers = 6;
    int gameTime = 15 * 60 * 1000;
    int timeStart;
    String id = "AAA";
    ArrayList<Player> team1 = new ArrayList<Player>();
    ArrayList<Player> team2 = new ArrayList<Player>();
    boolean ended = false;

    public Game() {
        this.id = randomString(6);
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static Random rnd = new Random();

    String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public void start() {
        for (Player p : team1) {
            if (!p.isReady())
                return;
        }
        for (Player p : team2) {
            if (!p.isReady())
                return;
        }
        timeStart = (int) System.currentTimeMillis();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                ended = true;
            }
        }, gameTime);
    }
}

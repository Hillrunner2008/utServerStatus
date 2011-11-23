package utStatusCheck;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public final class Server {

    private Runnable timer;
    private Thread thread;
    private DatagramPacket outgoing;
    private DatagramPacket incoming;
    private DatagramSocket ds;
    private InetAddress ia;
    private final byte oob = (byte) 0xff;
    private String message = "";

    public Map getInfoMap(String ip, int port) {
        Map infoMap = getServerInfo(giveCommand(ip, port, "getinfo"));
        return infoMap;
    }

    public Map getStatusMap(String ip, int port) {
        Map statusMap = getServerInfo(giveCommand(ip, port, "getstatus"));
        return statusMap;
    }

    public String getRawResponse(String ip, int port, String command) {
        return giveCommand(ip, port, command);
    }

    public Map<String, Integer> getPlayerList() {
        Map<String, Integer> playerList = new HashMap<String, Integer>();
        ArrayList<String> playerData = new ArrayList<String>();
        char startPosition = '"';
        String ip = "173.236.38.242";
        int port = 27961;
        String[] lines = getRawResponse(ip, port, "getstatus").split("\\n");
        playerData.addAll(Arrays.asList(lines));
        playerData.remove(0);
        for (String s : playerData) {
            String ans = "";
            Scanner sc = new Scanner(s);
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == startPosition) {
                    for (int j = i + 1; j < s.length() - 1; j++) {
                        ans += s.charAt(j);
                    }
                    break;
                }
            }
            Integer score = sc.nextInt();
            playerList.put(ans, score);
        }
        return playerList;
    }

    public String giveCommand(String ip, int port, String command) {
        timer = new Timer();
        thread = new Thread(timer);
        try {
            ia = InetAddress.getByName(ip);
            ds = new DatagramSocket();
        } catch (SocketException e) {
            ds.close();
            return "Try Again";
        } catch (UnknownHostException e) {
            ds = null;
            return "Try Again";
        }
        String out = "xxxx" + command;
        byte[] buffer = out.getBytes();
        for (int i = 0; i < 4; i++) {
            buffer[i] = oob;
        }
        try {
            outgoing = new DatagramPacket(buffer, buffer.length, ia, port);
            buffer = new byte[65507];
            incoming = new DatagramPacket(buffer, buffer.length);
        } catch (IllegalArgumentException e) {
            ds.close();
            return "Try Again";
        }
        thread.start();
        try {
            ds.send(outgoing);
            ds.receive(incoming);
            message = new String(incoming.getData(), 0, 0, incoming.getLength());
        } catch (IOException e) {
            ds.close();
            return "Try Again";
        }
        ds.close();
        message = getstatus(message);
        message = stripPrintCommands(message);
        return message;
    }

    private class Timer implements Runnable {

        public void run() {
            try {
                thread.sleep(500);
            } catch (InterruptedException e) {
            }
            ds.close();
        }
    }

    public String getstatus(String resp) {
        Pattern r = Pattern.compile("....statusResponse\n");
        Matcher m = r.matcher(resp);
        resp = m.replaceAll("");
        return resp;
    }

    public String getScore(String resp) {
        Pattern r = Pattern.compile("....statusResponse\n");
        Matcher m = r.matcher(resp);
        resp = m.replaceAll("");
        return resp;
    }

    public Map getServerInfo(String resp) {
        if (!resp.equals("")) {
            Pattern r = Pattern.compile("....infoResponse\n");
            Matcher m = r.matcher(resp);
            resp = m.replaceAll("");
            resp = resp.substring(1);
            String[] foo = resp.split("\\\\");
            ArrayList<String> keys = new ArrayList();
            ArrayList<String> vals = new ArrayList();
            boolean direction = true;
            for (int i = 0; i < foo.length; i++) {
                if (direction) {
                    keys.add(foo[i]);
                } else {
                    vals.add(foo[i]);
                }
                direction = (direction) ? false : true;
            }
            Map<String, String> map = new HashMap();
            if (vals.size() > 0) {
                for (int i = 0; i < keys.size(); i++) {
                    String key = keys.get(i);
                    String val = vals.get(i);
                    map.put(key, val);
                }
            }
            return map;
        } else {
            return null;
        }
    }

    public SoundPlayer getPlayer() throws UnsupportedAudioFileException, IOException {
        URL url = this.getClass().getClassLoader().getResource("sounds/ding.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
        SoundPlayer player = new SoundPlayer(audioIn);
        return player;
    }

    private String stripPrintCommands(String input) {
        Pattern r = Pattern.compile("....print\n");
        Matcher m = r.matcher(input);
        return m.replaceAll("");
    }
}
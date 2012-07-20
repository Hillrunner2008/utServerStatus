package Server;

import Globals.Constants;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to query Q3 based games, like Urban Terror.
 *
 *
 * @author David Norris
 * @author Brandon Tanner
 * @author Philip Edelbrock (phil@studiogeologie.com)
 * <http://secure.netroedge.com/~phil/q3tool-1.0.0/q3tool.java>
 * @author http://opensource.org/licenses/gpl-license.php GNU Public License
 */
public class ServerQuery {

    private byte oob;
    private int port;
    private DatagramSocket ds;
    private DatagramPacket dp;
    private InetAddress ia;
    private boolean rawOutput;
    private String output;

    public ServerQuery() throws Exception {
        this.oob = (byte) 0xff;
        this.port = Constants.getPort();
        this.ds = new DatagramSocket();
        this.ia = InetAddress.getByName(Constants.getIP());
    }

    public void send(String data) {
        try {
            String out = "xxxx" + data;
            byte[] buff = out.getBytes();
            buff[0] = this.oob;
            buff[1] = this.oob;
            buff[2] = this.oob;
            buff[3] = this.oob;
            this.dp = new DatagramPacket(buff, buff.length, this.ia, this.port);
            this.ds.send(this.dp);
        } catch (Exception e) {
            System.out.println("Send method in BowserQuery Failed with: " + e.getMessage());
        }
    }

    public String getResponse() {
        DatagramPacket dpacket;
        byte[] buffer = new byte[2048];
        this.output = "";
        while (true) {
            try {
                dpacket = new DatagramPacket(buffer, buffer.length);
                // Decrease value speeds things up, increase slows things down.
                this.ds.setSoTimeout(100);
                this.ds.receive(dpacket);
                String packet = new String(dpacket.getData(), 0, dpacket.getLength());
                this.output += packet;
            } catch (IOException e) {
                if (this.rawOutput) {
                    return this.output;
                } else {
                    //todo: replace blank player name with "UnknownPlayer"
                    String purdy = this.output;
                    purdy = stripPrintCommands(purdy);
                    //purdy = stripColors(purdy);
                    return purdy;
                }
            }
        } // end while
    }

    public String stripPrintCommands(String input) {
        Pattern r = Pattern.compile("....print\n");
        Matcher m = r.matcher(input);
        return m.replaceAll("");
    }

    public String getStatus(String resp) {
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

    public Map getInfoMap() throws Exception {
        ServerQuery q = new ServerQuery();
        q.send("getinfo");
        String resp = q.getResponse();
        resp = q.stripPrintCommands(q.getStatus(resp));
        Map infoMap = getServerInfo(resp);
        return infoMap;
    }

    public Map getStatusMap(String resp) throws Exception {
        Map statusMap = getServerInfo(resp);
        return statusMap;
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
    
    public String getStatusString() throws Exception {
        ServerQuery q = new ServerQuery();
        q.send("getstatus");
        Thread.sleep(100);
        String resp = q.getResponse();
        resp = q.stripPrintCommands(q.getStatus(resp));
        return resp;
    }
}

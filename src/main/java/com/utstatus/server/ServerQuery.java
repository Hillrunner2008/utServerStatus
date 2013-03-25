package com.utstatus.server;

import com.utstatus.globals.Constants;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final byte oob = (byte) 0xff;
    private int port;
    private DatagramSocket ds;
    private DatagramPacket dp;
    private InetAddress ia;
    private String output;

    public ServerQuery() throws Exception {
        this.port = Constants.getPort();
        this.ds = new DatagramSocket();
        this.ia = InetAddress.getByName(Constants.getIP());
    }

    public void send(String data) {
        try {
            String out = "xxxx" + data;
            byte[] buff = out.getBytes();
            buff[0] = oob;
            buff[1] = oob;
            buff[2] = oob;
            buff[3] = oob;
            dp = new DatagramPacket(buff, buff.length, ia, port);
            ds.send(dp);
        } catch (Exception e) {
            System.out.println("Send method in BowserQuery Failed with: " + e.getMessage());
        }
    }

    public String getResponse() {
        DatagramPacket dpacket;
        byte[] buffer = new byte[2048];
        output = "";
        while (true) {
            try {
                dpacket = new DatagramPacket(buffer, buffer.length);
                ds.setSoTimeout(100);
                ds.receive(dpacket);
                String packet = new String(dpacket.getData(), 0, dpacket.getLength());
                output += packet;
            } catch (IOException e) {
                String serverResponse = output;
                serverResponse = stripPrintCommands(serverResponse);
                return serverResponse;
            }
        }
    }

    private static String stripPrintCommands(String input) {
        Pattern r = Pattern.compile("....print\n");
        Matcher m = r.matcher(input);
        return m.replaceAll("");
    }

    private static String parseStatusResponse(String resp) {
        Pattern r = Pattern.compile("....statusResponse\n");
        Matcher m = r.matcher(resp);
        resp = m.replaceAll("");
        return resp;
    }

    private static String parseInfoResponse(String resp) {
        Pattern r = Pattern.compile("....infoResponse\n");
        Matcher m = r.matcher(resp);
        resp = m.replaceAll("");
        return resp;
    }

    public static Map getInfoMap(String info) throws Exception {
        Map infoMap = getServerInfo(info);
        return infoMap;
    }

    public static Map getStatusMap(String status) throws Exception {
        Map statusMap = getServerInfo(status);
        return statusMap;
    }

    public static String getRawStatus() throws Exception {
        ServerQuery query = new ServerQuery();
        query.send("getstatus");
        String resp = query.getResponse();
        resp = stripPrintCommands(parseStatusResponse(resp));
        return resp;
    }

    public static String getServerInfo() throws Exception {
        ServerQuery query = new ServerQuery();
        query.send("getinfo");
        String serverInfo = query.getResponse();
        serverInfo = stripPrintCommands(parseStatusResponse(serverInfo));
        return serverInfo;
    }

    private static Map getServerInfo(String resp) {
        if (!resp.equals("")) {
            resp = parseInfoResponse(resp);
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
}

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
                return serverResponse;
            }
        }
    }





    
}

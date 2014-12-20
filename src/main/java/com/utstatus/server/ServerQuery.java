package com.utstatus.server;

import com.utstatus.model.Configuration;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
    private String response;

    public ServerQuery(Configuration config) throws Exception {
        this.port = config.getPort();
        this.ds = new DatagramSocket();
        this.ia = InetAddress.getByName(config.getIp());
    }

    public void send(String data) {
        try {
            String out = "xxxx" + data;
            byte[] buff = out.getBytes();
            for (int i = 0; i <= 3; i++) {
                buff[i] = oob;
            }
            dp = new DatagramPacket(buff, buff.length, ia, port);
            ds.send(dp);
        } catch (Exception e) {
            
        }
    }

    public String getResponse() {
        DatagramPacket dpacket;
        byte[] buffer = new byte[2048];
        response = "";
        while (true) {
            try {
                dpacket = new DatagramPacket(buffer, buffer.length);
                ds.setSoTimeout(200);
                ds.receive(dpacket);
                String packet = new String(dpacket.getData(), 0, dpacket.getLength());
                response += packet;
            } catch (IOException e) {
                return response;
            }
        }
    }
}

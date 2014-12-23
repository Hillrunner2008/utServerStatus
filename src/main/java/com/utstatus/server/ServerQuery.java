package com.utstatus.server;

import com.utstatus.model.Configuration;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import static java.net.InetAddress.getByName;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

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

    private static final Logger logger = getLogger(ServerQuery.class);

    private final byte oob = (byte) 0xff;
    private int port;
    private DatagramSocket ds;
    private DatagramPacket dp;
    private InetAddress ia;
    private StringBuilder response;

    public ServerQuery(Configuration config) {
        this.port = config.getPort();
        try {
            this.ds = new DatagramSocket();
            this.ia = getByName(config.getIp());
        } catch (UnknownHostException | SocketException ex) {
            logger.error("Could not connect to {}", config.getIp(), ex);
        }
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
        } catch (IOException ex) {
            logger.error("IOException thrown during communication with server", ex);
        }
    }

    public String getResponse() {
        DatagramPacket dpacket;
        byte[] buffer = new byte[2048];
        response = new StringBuilder();
        while (true) {
            try {
                dpacket = new DatagramPacket(buffer, buffer.length);
                ds.setSoTimeout(200);
                ds.receive(dpacket);
                String packet = new String(dpacket.getData(), 0, dpacket.getLength());
                response.append(packet);
            } catch (IOException e) {
                return response.toString();
            }
        }
    }

    public byte[] getRawResponse() {
        DatagramPacket dpacket;
        byte[] buffer = new byte[2048];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (true) {
            try {
                dpacket = new DatagramPacket(buffer, buffer.length);
                ds.setSoTimeout(200);
                ds.receive(dpacket);
                String packet = new String(dpacket.getData(), 0, dpacket.getLength());
                baos.write(dpacket.getData(), 0, dpacket.getLength());
            } catch (IOException e) {
                break;
            }
        }
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

}

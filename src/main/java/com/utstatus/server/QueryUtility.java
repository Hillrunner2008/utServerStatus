package com.utstatus.server;

import com.google.common.base.Optional;
import com.utstatus.model.Configuration;
import com.utstatus.model.UtServer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author dcnorris
 */
public class QueryUtility {

    private final Configuration config;

    public QueryUtility(Configuration config) {
        this.config = config;
    }

    private static byte[] getRawMasterResponse() {
        Configuration masterConfig = new Configuration();
        masterConfig.setIp("master.urbanterror.info");
        masterConfig.setPort(27900);
        ServerQuery query = new ServerQuery(masterConfig);
        query.send("getservers 68 full empty");
        return query.getRawResponse();
    }

    public static List<UtServer> getMasterList() {
        return parseMasterResponse(getRawMasterResponse());
    }

    public String getServerStatus() {
        ServerQuery query = new ServerQuery(config);
        query.send("getstatus");
        String response = query.getResponse();
        response = QueryParser.prepareParsedResponse(response);
        return response;
    }

    public String getServerInfo() {
        ServerQuery query = new ServerQuery(config);
        query.send("getinfo");
        String response = QueryParser.prepareParsedResponse(query.getResponse());
        return response;
    }

    private static List<UtServer> parseMasterResponse(byte[] bytes) {
        List<UtServer> serverList = new ArrayList<>();
        int next, start = ArrayUtils.indexOf(bytes, (byte) 92, 0); //this should always be 22
        byte b;
        //the implication is this loop will always enter with i on the indexOf a '/':
        Optional<UtServer> server = Optional.absent();
        for (int i = start; i < bytes.length; i++) {

            //this might be out of bounds at the end
            //hopefully its always the last one
            try {
                b = bytes[i];
            } catch (ArrayIndexOutOfBoundsException ex) {
                break;
            }

            //find the index of the next '/': (this should always be 7 more
            next = ArrayUtils.indexOf(bytes, (byte) 92, i + 1);

            try { //hax way to not worry about reaching the end
                server = parseServerIpHost(Arrays.copyOfRange(bytes, i + 1, next));
            } catch (IllegalArgumentException ex) {
                //do nothing
            }
            if (server.isPresent()) {
                serverList.add(server.get());
            }

            i = next - 1;
        }
        return serverList;
    }

    private static Optional<UtServer> parseServerIpHost(byte[] series) {
        if (series.length != 6) {
            return Optional.absent();
        }

        // the &0xff "turns" the signed byte into an unsigned (in essence)
        String ip
                = (series[0] & 0xff) + "."
                + (series[1] & 0xff) + "."
                + (series[2] & 0xff) + "."
                + (series[3] & 0xff);
        int port = (series[4] * 256) + series[5];
        return Optional.<UtServer>fromNullable(new UtServer(ip, port));
    }
}

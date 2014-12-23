package com.utstatus.server;

import com.google.common.base.Optional;
import static com.google.common.base.Optional.absent;
import com.utstatus.model.Configuration;
import com.utstatus.model.UtServer;
import static com.utstatus.server.QueryParser.prepareParsedResponse;
import static java.util.Arrays.copyOfRange;
import java.util.HashSet;
import java.util.Set;
import static org.apache.commons.lang3.ArrayUtils.indexOf;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

/**
 *
 * @author dcnorris
 */
public class QueryUtility {
    
    private static final Logger logger = getLogger(QueryUtility.class);
    private final Configuration config;
    
    public QueryUtility(Configuration config) {
        this.config = config;
    }
    
    private static byte[] getRawMasterResponse() {
        Configuration masterConfig = new Configuration();
        masterConfig.setIp("master.urbanterror.info");
        masterConfig.setPort(27900);
        ServerQuery query = new ServerQuery(masterConfig);
        query.send("getservers 68 full");
        return query.getRawResponse();
    }
    
    public static Set<UtServer> getMasterList() {
        return parseMasterResponse(getRawMasterResponse());
    }
    
    public String getServerStatus() {
        ServerQuery query = new ServerQuery(config);
        query.send("getstatus");
        String response = query.getResponse();
        response = prepareParsedResponse(response);
        return response;
    }
    
    public String getServerInfo() {
        ServerQuery query = new ServerQuery(config);
        query.send("getinfo");
        String response = prepareParsedResponse(query.getResponse());
        return response;
    }
    
    private static Set<UtServer> parseMasterResponse(byte[] bytes) {
        Set<UtServer> serverList = new HashSet<>();
        int next, start = indexOf(bytes, (byte) 92, 0); //this should always be 22
        byte b;
        //the implication is this loop will always enter with i on the indexOf a '/':
        Optional<UtServer> server = absent();
        for (int i = start; i < bytes.length; i++) {

            //this might be out of bounds at the end
            //hopefully its always the last one
            try {
                b = bytes[i];
            } catch (ArrayIndexOutOfBoundsException ex) {
                break;
            }

            //find the index of the next '/': (this should always be 7 more
            next = indexOf(bytes, (byte) 92, i + 1);
            
            try { //hax way to not worry about reaching the end
                server = parseServerIpHost(copyOfRange(bytes, i + 1, next));
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
            return absent();
        }

        // the &0xff "turns" the signed byte into an unsigned (in essence)
        String ip
                = (series[0] & 0xff) + "."
                + (series[1] & 0xff) + "."
                + (series[2] & 0xff) + "."
                + (series[3] & 0xff);
        int port = (series[4] * 256) + series[5];
        if (port <= 0) {
            return absent();
        }
        return Optional.<UtServer>fromNullable(new UtServer(ip, port));
    }
}

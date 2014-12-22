package com.utstatus.model;

import com.google.common.base.Splitter;
import static com.utstatus.model.ResponseConstants.GAMETYPE;
import com.utstatus.server.QueryUtility;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dcnorris
 */
public class UtServer {

    private static final Logger logger = LoggerFactory.getLogger(UtServer.class);

    private String ip;
    private int port;
    private int qty;
    private List<Player> players;
    private boolean isFull;
    private boolean isEmpty;
    //sv_hostname
    private String name;
    private String map;
    private String playerInfo;
    private ServerType type;
    private int ping;
    //sv_maxclients
    private int capacity;

    public UtServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
        init();
    }

    public void refreshServer() {
        init();
    }

    private void init() {
        Configuration serverConfig = new Configuration(ip, port);
        QueryUtility queryUtil = new QueryUtility(serverConfig);
        String infoResponse = queryUtil.getServerInfo();
        String statusResponse = queryUtil.getServerStatus();
        initServerInfo(infoResponse);
        initServerStatus(statusResponse);
    }

    private void initServerInfo(String infoResponse) {
        logger.info(infoResponse);
        for (String s : Splitter.on('\\').omitEmptyStrings().trimResults().split(infoResponse)) {
            if (s.equals(GAMETYPE)){}
        }
    }

    private void initServerStatus(String statusResponse) {
//        logger.info(statusResponse);
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public boolean isIsFull() {
        return isFull;
    }

    public boolean isIsEmpty() {
        return isEmpty;
    }

    public int getQty() {
        return qty;
    }

    public int getBotCount() {
        int botCount = 0;
        for (Player player : players) {
            if (player.isIsBot()) {
                botCount++;
            }
        }
        return botCount;
    }

    public String getName() {
        return name;
    }

    public String getMap() {
        return map;
    }

    public String getPlayerInfo() {
        return playerInfo;
    }

    public ServerType getType() {
        return type;
    }

    public int getPing() {
        return ping;
    }

    @Override
    public String toString() {
        return "UtServer{" + "ip=" + ip + ", port=" + port + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.ip);
        hash = 13 * hash + this.port;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UtServer other = (UtServer) obj;
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        return this.port == other.port;
    }
}

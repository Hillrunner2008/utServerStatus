package com.utstatus.model;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import static com.utstatus.model.ResponseConstants.*;
import static com.utstatus.server.QueryParser.stripColors;
import com.utstatus.server.QueryUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
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
    private List<Player> players;
    private boolean isFull;
    private boolean isEmpty;
    //sv_hostname
    private String name;
    private String map;
    private String capacityInfo;
    private ServerType type;
    private int ping;
    //sv_maxclients
    private int maxClients;
    private int clients;

    public UtServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void refreshServer() {
        init();
    }

    private void init() {
        Configuration serverConfig = new Configuration(ip, port);
        QueryUtility queryUtil = new QueryUtility(serverConfig);
        String infoResponse = queryUtil.getServerInfo();
//        String statusResponse = queryUtil.getServerStatus();
        initServerInfo(infoResponse);
//        initServerStatus(statusResponse);
    }

    private void initServerInfo(String infoResponse) {
        logger.debug(infoResponse);
        List<String> responseContent = Splitter.on('\\').omitEmptyStrings().trimResults().splitToList(infoResponse);
        responseContent = responseContent.subList(1, responseContent.size());
        ListIterator<String> iter = responseContent.listIterator();
        Map<String, String> serverInfoMap = new HashMap<>();
        while (iter.hasNext()) {
            serverInfoMap.put(iter.next(), iter.next());
        }
        name = stripColors(serverInfoMap.get(HOSTNAME));
        map = serverInfoMap.get(MAP_NAME);
        type = ServerType.findByValue(Integer.parseInt(serverInfoMap.get(GAMETYPE)));
        clients = Integer.parseInt(serverInfoMap.get(CLIENTS));
        maxClients = Integer.parseInt(serverInfoMap.get(MAX_CLIENTS));
        capacityInfo = "(" + clients + "/" + maxClients + ")";
        isEmpty = clients == 0;
        isFull = clients == maxClients;
    }

    private void initServerStatus(String statusResponse) {
        logger.debug(statusResponse);
        players = new ArrayList<>();
        List<String> responseContent = Splitter.on('\n').omitEmptyStrings().trimResults().splitToList(statusResponse);
        responseContent = responseContent.subList(1, responseContent.size());
        for (String playerLine : responseContent) {
            List<String> playerDetails = Splitter.on(' ').omitEmptyStrings().trimResults().splitToList(playerLine);
            Player player = new Player();
            player.setScore(Integer.parseInt(playerDetails.get(0)));
            player.setPing(Integer.parseInt(playerDetails.get(1)));
            player.setName(CharMatcher.is('\"').trimFrom(playerDetails.get(2)));
            players.add(player);
        }

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

    public String getCapacityInfo() {
        return capacityInfo;
    }

    public ServerType getType() {
        return type;
    }

    public int getPing() {
        return ping;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public int getClients() {
        return clients;
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

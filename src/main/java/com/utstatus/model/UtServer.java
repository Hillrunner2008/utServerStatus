package com.utstatus.model;

import static com.google.common.base.CharMatcher.is;
import static com.google.common.base.Splitter.on;
import com.google.common.base.Strings;
import static com.utstatus.model.ResponseConstants.CLIENTS;
import static com.utstatus.model.ResponseConstants.GAMETYPE;
import static com.utstatus.model.ResponseConstants.HOSTNAME;
import static com.utstatus.model.ResponseConstants.MAP_NAME;
import static com.utstatus.model.ResponseConstants.MAX_CLIENTS;
import static com.utstatus.model.ServerType.findByValue;
import static com.utstatus.server.QueryParser.stripColors;
import com.utstatus.server.QueryUtility;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

/**
 *
 * @author dcnorris
 */
public class UtServer {

    private static final Logger logger = getLogger(UtServer.class);

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
//        String infoResponse = queryUtil.getServerInfo();
        String statusResponse = queryUtil.getServerStatus();
//        initServerInfo(infoResponse);
        initServerStatus(statusResponse);
    }

    private void parseStatusResponse(String statusFirstLine) {
        List<String> responseContent = on('\\').omitEmptyStrings().trimResults().splitToList(statusFirstLine);
        ListIterator<String> iter = responseContent.listIterator();
        Map<String, String> serverInfoMap = new HashMap<>();
        while (iter.hasNext()) {
            serverInfoMap.put(iter.next(), iter.next());
        }
        name = stripColors(serverInfoMap.get(HOSTNAME));
        map = serverInfoMap.get(MAP_NAME);
        type = findByValue(parseInt(serverInfoMap.get(GAMETYPE)));
        maxClients = parseInt(serverInfoMap.get(MAX_CLIENTS));

    }

    private void initServerStatus(String statusResponse) {
        if (Strings.isNullOrEmpty(statusResponse)) {
            return;
        }
        logger.debug(statusResponse);
        players = new ArrayList<>();
        List<String> responseContent = on('\n').omitEmptyStrings().trimResults().splitToList(statusResponse);
        String firstLine = responseContent.subList(0, 1).get(0);
        parseStatusResponse(firstLine);
        responseContent = responseContent.subList(1, responseContent.size());
        for (String playerLine : responseContent) {
            List<String> playerDetails = on(' ').omitEmptyStrings().trimResults().splitToList(playerLine);
            Player player = new Player();
            player.setScore(parseInt(playerDetails.get(0)));
            player.setPing(parseInt(playerDetails.get(1)));
            player.setName(is('\"').trimFrom(playerDetails.get(2)));
            players.add(player);
        }
        clients = players.size();
        capacityInfo = "(" + clients + "/" + maxClients + ")";
        isEmpty = clients == 0;
        isFull = clients == maxClients;

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
        botCount = players.stream().filter((player) -> (player.isIsBot())).map((_item) -> 1).reduce(botCount, Integer::sum);
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

package com.utstatus.model;

/**
 *
 * @author dcnorris
 */
public class Configuration {

    public static int POLLING_DELAY = 8;

    private String ip = "209.190.50.170";
    private int port = 27960;
    private String playerName = "unnamedPlayer";
    private String executablePath = "";

    public Configuration() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getExecutablePath() {
        return executablePath;
    }

    public void setExecutablePath(String executablePath) {
        this.executablePath = executablePath;
    }

    public int getPollDelay() {
        return POLLING_DELAY;
    }

    public void setPollDelay(int pollDelay) {
        this.POLLING_DELAY = pollDelay;
    }

    public String getServerAddress() {
        return ip + ":" + getPortString();
    }

    public String getPortString() {
        return Integer.toString(port);
    }

    public String getDelayString() {
        String delayString = "" + POLLING_DELAY;
        return delayString;
    }

    public void setDelay(int delay) {
        this.POLLING_DELAY = delay;
    }
}

package com.utstatus.model;

/**
 *
 * @author dcnorris
 */
public class Configuration {

    private String serverIpAddress;
    private int port;
    private String playerName;
    private String executablePath;
    private int pollDelay = 8;

    public String getServerIpAddress() {
        return serverIpAddress;
    }

    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
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
        return pollDelay;
    }

    public void setPollDelay(int pollDelay) {
        this.pollDelay = pollDelay;
    }

    public String getServerAddress() {
        return serverIpAddress + ":" + getPortString();
    }

    public String getPortString() {
        return Integer.toString(port);
    }

    public String getDelayString() {
        String delayString = "" + pollDelay;
        return delayString;
    }

    public void setDelay(int delay) {
        this.pollDelay = delay;
    }
}

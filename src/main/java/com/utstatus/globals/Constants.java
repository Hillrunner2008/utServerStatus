package com.utstatus.globals;

/**
 *
 * @author dcnorris
 */
public final class Constants {

    private static String ip;
    private static int port;
    private static String name;
    private static String exePath;
    //todo: add back dynamic delay functionality 
    private static int delay = 8;

    public static String getExePath() {
        return exePath;
    }

    public static void setExePath(String path) {
        Constants.exePath = path;
    }

    public static String getIP() {
        return ip;
    }

    public static void setIP(String ip) {
        Constants.ip = ip;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Constants.port = port;
    }

    public static String getServerAddress() {
        return ip + ":" + getPortString();
    }

    public static String getPortString() {
        return Integer.toString(port);
    }

    public static String getPlayerName() {
        return name;
    }

    public static void setPlayerName(String name) {
        Constants.name = name;
    }

    public static int getDelay() {
        return delay;
    }

    public static String getDelayString() {
        String delayString = "" + delay;
        return delayString;
    }

    public static void setDelay(int delay) {
        Constants.delay = delay;
    }
}

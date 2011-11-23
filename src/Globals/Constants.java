package Globals;

/**
 *
 * @author dcnorris
 */
public final class Constants {

    static String ip;
    static int port;
    static String name;
    static String exePath;

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
        return ip.toString();
    }

    public static String getPlayerName() {
        return name;
    }

    public static void setPlayerName(String name) {
        Constants.name = name;
    }
}

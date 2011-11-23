/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utStatusCheck;

import Globals.Constants;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 *
 * @author dcnorris
 */
public class Driver {

    private static SysTray sysTray = new SysTray();
    public boolean inGame = false;
    private static boolean playSound = true;
    private static UrtApp app;

    @SuppressWarnings("SleepWhileInLoop")
    public static void main(String[] args) throws Exception {

        Setup setup = new Setup();
        setup.setVisible(true);

        app = new UrtApp();
        TrayIcon icon = new TrayIcon(SysTray.getImage(sysTray),
                "UT Status App", SysTray.createPopupMenu());

        icon.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        synchronized (app) {
                            app.updateTable();
                            app.setVisible(true);
                        }
                    }
                });
            }
        });
        SystemTray.getSystemTray().remove(icon); //?
        SystemTray.getSystemTray().add(icon);
        while (true) {

            Thread.sleep(10000);
            app.updateTable();
            Server server = new Server();
            int maxClients = 0;
            int activeClients = 0;
            int activePlayersMinusSelf = 0;

            String results;
            String mapName = "";
            maxClients = 0;
            activeClients = 0;
            Map serverInfo = server.getInfoMap(Constants.getIP(), Constants.getPort());
            Map<String, Integer> playerList = server.getPlayerList();

            try {
                maxClients = Integer.parseInt((String) serverInfo.get("sv_maxclients"));
            } catch (NumberFormatException e) {
                maxClients = 0;
            }
            try {
                activeClients = Integer.parseInt((String) serverInfo.get("clients"));
            } catch (NumberFormatException e) {
                activeClients = 0;
            }
            mapName = (String) serverInfo.get("mapname");
            mapName = "Map: " + mapName;
            results = "(" + activeClients + "/" + maxClients + ")" + " currently playing";
            if (activeClients < 2) {
                playSound = true;
            }

            if (playerList.containsKey(Constants.getPlayerName())) {
                activePlayersMinusSelf = 1;
                if (playerList.get(Constants.getPlayerName()) > 0) {
                    playSound = false;
                }
                if (activeClients < 2) {
                    playSound = true;
                }
            }

            if (activeClients > activePlayersMinusSelf) {
                icon.displayMessage("One in the chamber: players now online", "Map Name: " + mapName + " " + results,
                        TrayIcon.MessageType.INFO);
                if (playSound) {
                    SoundPlayer player = server.getPlayer();
                    player.start();
                }

            }
        }


    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utStatusCheck;

import GUI.SysTray;
import GUI.Setup;
import GUI.UrtApp;
import Sound.SoundPlayerService;
import Sound.SoundPlayer;
import Globals.Constants;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author dcnorris
 */
public class Driver {

    private static SysTray sysTray = new SysTray();
    public boolean inGame = false;
    private static boolean playSound = true;
    private static UrtApp app;
    private static ArrayList<Player> playerList = new ArrayList<Player>();

    @SuppressWarnings("SleepWhileInLoop")
    public static void main(String[] args) throws Exception {
        SoundPlayerService server = new SoundPlayerService();
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
                            try {
                                app.updateTable();
                            } catch (Exception ex) {
                                //do nothing
                            }
                            app.setVisible(true);
                        }
                    }
                });
            }
        });
        SystemTray.getSystemTray().remove(icon); //? 
        SystemTray.getSystemTray().add(icon);
        while (true) {
            Thread.sleep(5000);
            app.updateTable();
            int maxClients = app.getMaxClients();
            int activeClients = app.getActiveClients();
            String results = "(" + activeClients + "/" + maxClients + ")" + " currently playing";
            String mapName = "Map: " + app.getMapName();

            playerList = app.getTableModel().getPlayers();
            if (!playerList.isEmpty()) {
                //This is a test for reseting the alert mechanism automatically as needed.
                if (activeClients < 2) {
                    playSound = true;
                }
                //If the player is the only one in the game do not send alert 
                if (isPlayerInGame()) {
                    playSound = false;
                    //If the player is not the only activeClient in the game send alert
                    if (activeClients > 1) {
                        playSound = true;
                    }
                }
                if (playSound) {
                    icon.displayMessage("One in the chamber: players now online", "Map Name: " + mapName + " " + results,
                            TrayIcon.MessageType.INFO);  
                    SoundPlayer player = server.getAudioPlayer();
                    player.start();
                }
            }
        }
    }

    private static boolean isPlayerInGame() {
        for (Player p : playerList) {
            if (p.getName().equalsIgnoreCase(Constants.getPlayerName())) {
                return true;
            }
        }
        return false;
    }

}

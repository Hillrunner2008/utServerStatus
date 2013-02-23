/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utstatus.utStatusCheck;

import com.utstatus.gui.SysTray;
import com.utstatus.gui.UrtApp;
import com.utstatus.sound.SoundPlayer;
import com.utstatus.sound.SoundPlayerService;
import java.awt.TrayIcon;
import java.util.ArrayList;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author david
 */
public class ServerStatusCheck implements Job {

    SoundPlayerService server = new SoundPlayerService();
    public boolean inGame = false;
    private static boolean sendNotification = true;
    private static UrtApp app;
    private static ArrayList<Player> playerList = new ArrayList<Player>();

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        try {
            app = UrtApp.getInstance();
            SysTray sysTray = SysTray.getInstance();
            TrayIcon icon = sysTray.getIcon();
            app.updateTable();
            int maxClients = app.getMaxClients();
            int activeClients = app.getActiveClients();
            String results = "(" + activeClients + "/" + maxClients + ")" + " currently playing";
            String mapName = "Map: " + app.getMapName();

            playerList = app.getPlayers();
            if (!playerList.isEmpty()) {
                //This is a test for reseting the alert mechanism automatically as needed.
                if (activeClients < 2) {
                    sendNotification = true;
                }
                //If the player is the only one in the game do not send alert 
                if (app.getPrimaryPlayer() != null) {
                    sendNotification = false;
                    app.getJoinButton().setEnabled(false);
                    //If the player is not the only activeClient in the game send alert
                    if (activeClients > 1) {
                        //check if game is active (i.e. beep until your first kill)
                        if (app.getPrimaryPlayer().getScore() < 1) {
                            sendNotification = true;
                        } else {
                            //stop sending audio alerts until re-enabled from gui                            
                            app.setPlaySound(false);
                        }
                    }
                }


                if (sendNotification) {
                    icon.displayMessage("One in the chamber: players now online", "Map Name: " + mapName + " " + results,
                            TrayIcon.MessageType.INFO);
                    if (app.getPlaySound()) {
                        playSound();
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public synchronized void playSound() {
        new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
            public void run() {
                try {
                    SoundPlayer player = server.getAudioPlayer();
                    player.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}
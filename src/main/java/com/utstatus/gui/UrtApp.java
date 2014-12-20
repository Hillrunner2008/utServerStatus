/*
 * urtApp.java
 * @author dcnorris
 * Created on Jul 30, 2011, 7:44:59 PM
 */
package com.utstatus.gui;

import com.utstatus.model.Configuration;
import com.utstatus.server.QueryUtility;
import com.utstatus.model.Player;
import com.utstatus.sound.SoundPlayer;
import com.utstatus.sound.SoundPlayerService;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrtApp extends javax.swing.JDialog {

    private static final Logger logger = LoggerFactory.getLogger(UrtApp.class);

    public static final String APPLICATION_NAME = "UT Status App";
    private String currentMapID = "no_image";
    private String statusCommand = "getstatus";

    private int maxClients = 0;
    private int playerCount = 0;
    private String results;
    private String mapImageID;

    private TableModel tableModel;
    private boolean playSound = true;
    private Configuration config;
    private SystemTrayManager sysTray;
    private QueryUtility queryUtil;

    /**
     * Creates new form urtApp
     *
     * @param config
     */
    @SuppressWarnings("SleepWhileInLoop")
    public UrtApp(Configuration config) throws Exception {
        this.config = config;
        tableModel = new TableModel(config);
        initComponents();
        Setup setup = new Setup(config, this);
        setup.setVisible(true);
        queryUtil = new QueryUtility(config);
        sysTray = new SystemTrayManager(this);
        new ServerStatusCheck();
    }

    public void initScheduler() {
        try {

        } catch (Exception ex) {
            logger.error("Error during initialization of scheduler", ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serverNameLabel = new javax.swing.JLabel();
        resultsLabel = new javax.swing.JLabel();
        mapNameLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        playerTable = new javax.swing.JTable();
        mapImageLabel = new javax.swing.JLabel();
        joinButton = new javax.swing.JButton();
        audioCheckBox = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setName("mainPanel"); // NOI18N
        setResizable(false);

        serverNameLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        serverNameLabel.setText("Server:  ");

        resultsLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        resultsLabel.setText("0 / 16 currently playing");

        mapNameLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mapNameLabel.setText("Map: ");

        jLabel3.setIcon(new javax.swing.ImageIcon("urbanterror.jpg"));

        jScrollPane1.setViewportView(playerTable);
        playerTable.setModel(tableModel);

        mapImageLabel.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/"+currentMapID +".jpg")));

        joinButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        joinButton.setText("Join Server");
        joinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinButtonActionPerformed(evt);
            }
        });

        audioCheckBox.setSelected(true);
        audioCheckBox.setText("Audio Alerts");
        audioCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audioCheckBoxActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        JMenuItem exit = new JMenuItem("Exit");

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sysTray.removeSysTrayIcon();
                System.exit(0);
            }
        });
        jMenu1.add(exit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Credits");

        JMenuItem credits = new JMenuItem("credits");
        credits.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newline = "\n";
                String text = "Designed and Programmed by: David C. Norris" + newline + newline;
                JTextArea textArea = new JTextArea(text);
                textArea.append("I owe credit to a few open source projects for bits and pieces of code." + newline + newline);
                textArea.append("1. The author of the AePlayWave java sound player." + newline);
                textArea.append("2. 1up ModRcon group (for some server query code)" + newline);
                textArea.append("3. Main Image credit goes to author of the Urban Terror PHP Server Status project" + newline);
                textArea.setColumns(35);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setSize(textArea.getPreferredSize().width, 1);
                JOptionPane.showMessageDialog(null, textArea, "", JOptionPane.PLAIN_MESSAGE);
            }
        });
        jMenu2.add(credits);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Info");

        JMenuItem info = new JMenuItem("info");
        info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newline = "\n";
                String text = "Description of the program" + newline + newline;
                JTextArea textArea = new JTextArea(text);
                String message = "This program is designed to spare you the inconvenience of waiting around on an empty server. The program requires you enter your Urban Terror playername accurately to "
                + "alert you appropriately (note: most special characters are not supported).  If you are the only active player on the server, you will not receive any alerts (this allows you to \"bait\" the server.  \n\nIf you are joined by additional players you will begin to"
                + " receive alerts.  These alerts will continue until you get your first kill (Your first kill is the signal you are actively engaged in the game).  After your first kill the audio alerts will "
                + "stop until you re-enable them from the checkbox.";
                textArea.append(message);
                textArea.setColumns(35);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setSize(textArea.getPreferredSize().width, 1);
                JOptionPane.showMessageDialog(null, textArea, "", JOptionPane.PLAIN_MESSAGE);
            }
        });
        jMenu3.add(info);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(resultsLabel)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mapNameLabel)
                        .addComponent(serverNameLabel)
                        .addComponent(jLabel3)
                        .addComponent(mapImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(audioCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(joinButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serverNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mapNameLabel)
                .addGap(18, 18, 18)
                .addComponent(mapImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(resultsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(joinButton)
                    .addComponent(audioCheckBox))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void joinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinButtonActionPerformed
        //To Do: add a check to see if player is already in game and grey out button if that is the case.
        try {

            Launch(config.getExecutablePath(), config.getIp() + ":" + config.getPortString());
        } catch (Exception ex) {
            logger.error("Could not launch urban terror with the provided path {}", config.getExecutablePath(), config.getIp() + ":" + config.getPortString(), ex);
        }
    }//GEN-LAST:event_joinButtonActionPerformed

    private void audioCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audioCheckBoxActionPerformed
        playSound = audioCheckBox.isSelected();
    }//GEN-LAST:event_audioCheckBoxActionPerformed

    @Override
    public void setDefaultCloseOperation(int operation) {
        super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox audioCheckBox;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton joinButton;
    private javax.swing.JLabel mapImageLabel;
    private javax.swing.JLabel mapNameLabel;
    private javax.swing.JTable playerTable;
    private javax.swing.JLabel resultsLabel;
    private javax.swing.JLabel serverNameLabel;
    // End of variables declaration//GEN-END:variables

    public void updateTable() throws Exception {
        maxClients = 0;
        playerCount = 0;
        currentMapID = "no_image";
        String serverInfo = queryUtil.getServerInfo();
        Map serverInfoMap = queryUtil.getInfoMap(serverInfo);
        String rawStatus = queryUtil.getRawStatus();
        Map statusInfo = queryUtil.getStatusMap(rawStatus);
        if (statusInfo == null || serverInfo == null) {
            return;
        }
        ArrayList<Player> playerList = getPlayerList(rawStatus);
        try {
            maxClients = Integer.parseInt((String) serverInfoMap.get("sv_maxclients"));
        } catch (NumberFormatException e) {
            maxClients = 0;
        }

        playerCount = playerList.size();
        if (playerCount == 0) {
            getJoinButton().setEnabled(true);
        }

        String mapName = (String) serverInfoMap.get("mapname");
        currentMapID = mapName;
        ClassLoader classloader = getClass().getClassLoader();
        if (classloader.getResource(currentMapID + ".jpg") != null) {
            mapImageLabel.setIcon(new ImageIcon(classloader.getResource(currentMapID + ".jpg")));
        }
        mapImageID = "Map: ";
        mapImageID += mapName;
        mapNameLabel.setText(mapImageID);
        results = "(" + playerCount + "/" + maxClients + ")";
        resultsLabel.setText(results);
        String serverName = "unknown";
        if (statusInfo.get("sv_hostname") != null) {
            serverName = (String) statusInfo.get("sv_hostname");
        }
        serverNameLabel.setText("Server: " + serverName);
        tableModel.setData(playerList);
    }

    private ArrayList<Player> getPlayerList(String rawStatus) {
        ArrayList<Player> players = new ArrayList<>();
        String[] lines = rawStatus.split("\\n");
        for (int i = 1; i < lines.length; i++) {
            String[] lineSplit = breakLines(lines[i]);
            Player player = new Player();
            player.setScore(Integer.parseInt(lineSplit[0]));
            player.setPing(Integer.parseInt(lineSplit[1]));
            player.setName(lineSplit[2]);
            players.add(player);
        }
        return players;
    }

    private String[] breakLines(String line) {
        String[] thisLine = new String[3];
        String scorePing = line.substring(0, line.indexOf('\"'));
        String[] tempSplit = scorePing.split(" ");
        thisLine[0] = tempSplit[0]; // Score
        thisLine[1] = tempSplit[1]; // Ping
        thisLine[2] = line.substring(line.indexOf('\"') + 1, line.lastIndexOf('\"'));
        return thisLine;
    }

    public static void Launch(String pathToExe, String serverIP) throws Exception {
        File game = new File(pathToExe);
        ProcessBuilder processBuilder = new ProcessBuilder(pathToExe, "+connect", serverIP);
        processBuilder.directory(new File(game.getParent()));
        processBuilder.start();
    }

    public int getActiveClients() {
        return playerCount;
    }

    public void setActiveClients(int activeClients) {
        this.playerCount = activeClients;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }

    public String getMapName() {
        return mapImageID;
    }

    public JButton getJoinButton() {
        return joinButton;
    }

    public boolean getPlaySound() {
        return playSound;
    }

    public ArrayList<Player> getPlayers() {
        return tableModel.getPlayers();
    }

    public Player getPrimaryPlayer() {
        Player player = tableModel.getPrimaryPlayer();
        if (player == null) {
            getJoinButton().setEnabled(true);
        }
        return player;
    }

    public void setPlaySound(boolean playsound) {
        audioCheckBox.setSelected(playsound);
        this.playSound = playsound;
    }

    private class ServerStatusCheck {

        private SoundPlayerService server = new SoundPlayerService();
        public boolean inGame = false;
        private boolean sendNotification = true;
        private ArrayList<Player> playerList = new ArrayList<>();

        private Timer timer = new Timer(config.getPollDelay() * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                execute();
            }
        });

        private void execute() {
            try {
                updateTable();
                int maxClients = getMaxClients();
                int activeClients = getActiveClients();
                String results = "(" + activeClients + "/" + maxClients + ")" + " currently playing";
                String mapName = "Map: " + getMapName();

                playerList = getPlayers();
                if (!playerList.isEmpty()) {
                    //This is a test for reseting the alert mechanism automatically as needed.
                    if (activeClients < 2) {
                        sendNotification = true;
                    }
                    //If the player is the only one in the game do not send alert 
                    if (getPrimaryPlayer() != null) {
                        sendNotification = false;
                        getJoinButton().setEnabled(false);
                        //If the player is not the only activeClient in the game send alert
                        if (activeClients > 1) {
                            //check if game is active (i.e. beep until your first kill)
                            if (getPrimaryPlayer().getScore() < 1) {
                                sendNotification = true;
                            } else {
                                //stop sending audio alerts until re-enabled from gui                            
                                setPlaySound(false);
                            }
                        }
                    }

                    if (sendNotification) {
                        sysTray.getIcon().displayMessage("One in the chamber: players now online", "Map Name: " + mapName + " " + results,
                                TrayIcon.MessageType.INFO);
                        if (getPlaySound()) {
                            playSound();
                        }
                    }
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

        private void playSound() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        SoundPlayer player = server.getAudioPlayer();
                        player.start();
                    } catch (Exception ex) {
                        logger.error(ex.getMessage(), ex);
                    }
                }
            });

        }
    }

}

/*
 * urtApp.java
 * @author dcnorris
 * Created on Jul 30, 2011, 7:44:59 PM
 */
package GUI;

import Globals.Constants;
import Server.ServerQuery;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import utStatusCheck.Player;

public class UrtApp extends javax.swing.JDialog {

    int maxClients = 0;
    int activeClients = 0;
    String results;
    String mapName = "";
    String level_Image = "no_image";
    String statusCommand = "getstatus";
    TableModel tableModel = new TableModel();
    boolean firstTime = true;

    /**
     * Creates new form urtApp
     */
    @SuppressWarnings("SleepWhileInLoop")
    public UrtApp() throws Exception {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        resultsLabel = new javax.swing.JLabel();
        mapNameLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        playerTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setName("mainPanel"); // NOI18N
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Server:  ");

        resultsLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        resultsLabel.setText("0 / 16 currently playing");

        mapNameLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mapNameLabel.setText("Map: ");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/urbanterror.jpg"))); // NOI18N

        jScrollPane1.setViewportView(playerTable);
        playerTable.setModel(tableModel);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/"+ level_Image +".jpg")));

        jButton1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jButton1.setText("Join Server");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        JMenuItem exit = new JMenuItem("Exit");

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jMenu1.add(exit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Credits");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Info");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(resultsLabel)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mapNameLabel)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(14, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 22, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mapNameLabel)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(resultsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //To Do: add a check to see if player is already in game and grey out button if that is the case.
        try {
            Launch(Constants.getExePath(), Constants.getIP() + ":" + Constants.getPortString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
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
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        String newline = "\n";
        String text = "Description of the program" + newline + newline;
        JTextArea textArea = new JTextArea(text);
        String message = "This program is designed to spare you the inconvenience of waiting around on an empty server. The program requires you enter your Urban Terror playername accurately to "
                + "alert you appropriately.  If you are the only active player on the server, you will not receive any alerts.  \n\nIf you are joined by additional players you will begin to"
                + " receive alerts.  These alerts will continue until you get your first kill (Your first kill is the signal you are actively engaged in the game).  After your first kill the alerts will "
                + "stop until player scores are reset at the beggining of the next round.  \n\nAt this time the alert mechanism is reset and you will receive alerts again until your next kill.  Unfortunately "
                + "I have not yet put in a more elegant solution "
                + "to allow more discrimination on when to send an alert. Look forward to that feature in the next update.";
        textArea.append(message);
        textArea.setColumns(35);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setSize(textArea.getPreferredSize().width, 1);
        JOptionPane.showMessageDialog(null, textArea, "", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_jMenu3MouseClicked

    @Override
    public void setDefaultCloseOperation(int operation) {
        super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel mapNameLabel;
    private javax.swing.JTable playerTable;
    private javax.swing.JLabel resultsLabel;
    // End of variables declaration//GEN-END:variables

    public void updateTable() throws Exception {
        maxClients = 0;
        activeClients = 0;
        ServerQuery query = new ServerQuery();
        Map serverInfo = query.getInfoMap();
        String resp = query.getStatusString();
        Map statusInfo = query.getStatusMap(resp);
        if (statusInfo == null || serverInfo == null) {
            return;
        }
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
        level_Image = mapName;
        ClassLoader classloader = getClass().getClassLoader();
        if (classloader.getResource("images/" + level_Image + ".jpg") != null) {
            jLabel4.setIcon(new javax.swing.ImageIcon(classloader.getResource("images/" + level_Image + ".jpg")));
        }
        mapName = "Map: " + mapName;
        mapNameLabel.setText(mapName);
        results = "(" + activeClients + "/" + maxClients + ")";
        resultsLabel.setText(results);
        String serverName = "unknown";
        if (statusInfo.get("sv_hostname") != null) {
            serverName = (String) statusInfo.get("sv_hostname");
        }

        jLabel1.setText("Server: " + serverName);
        ArrayList<Player> players = new ArrayList<Player>();
        String[] lines = resp.split("\\n");
        for (int i = 1; i < lines.length; i++) {
            String[] lineSplit = breakLines(lines[i]);
            Player player = new Player();
            player.setScore(Integer.parseInt(lineSplit[0]));
            player.setPing(Integer.parseInt(lineSplit[1]));
            player.setName(lineSplit[2]);
            players.add(player);
        }
        if (players.size() == activeClients) {
            tableModel.setData(players);
        }

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
        return activeClients;
    }

    public void setActiveClients(int activeClients) {
        this.activeClients = activeClients;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }

    public String getMapName() {
        return mapName;
    }

    public TableModel getTableModel() {
        return tableModel;
    }
}

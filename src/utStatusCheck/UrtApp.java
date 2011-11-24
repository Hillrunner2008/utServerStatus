/*
 * urtApp.java
 * @author dcnorris
 * Created on Jul 30, 2011, 7:44:59 PM
 */
package utStatusCheck;

import Globals.Constants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class UrtApp extends javax.swing.JDialog {

    private Server server;
    int maxClients = 0;
    int activeClients = 0;
    String results;
    String mapName = "";
    String level_Image = "no_image";
    String statusCommand = "getstatus";
    TableModel tableModel = new TableModel();
    boolean firstTime = true;

    /** Creates new form urtApp */
    @SuppressWarnings("SleepWhileInLoop")
    public UrtApp() throws InterruptedException {
        server = new Server();
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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

        setName("mainPanel"); // NOI18N
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel1.setText("Server:  ");

        resultsLabel.setFont(new java.awt.Font("Tahoma", 0, 18));
        resultsLabel.setText("0 / 16 currently playing");

        mapNameLabel.setFont(new java.awt.Font("Tahoma", 0, 18));
        mapNameLabel.setText("Map: ");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/urbanterror.jpg"))); // NOI18N

        jScrollPane1.setViewportView(playerTable);
        playerTable.setModel(tableModel);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/"+ level_Image +".jpg")));

        jButton1.setFont(new java.awt.Font("SansSerif", 1, 14));
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

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(resultsLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mapNameLabel)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
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
        try {
            Launch(Constants.getExePath(), Constants.getIP() + ":" + Constants.getPortString());
        } catch (Exception ex) {
            ex.printStackTrace();
            // Logger.getLogger(UrtApp.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        String newline = "\n";
        String text = "Designed and Programmed by: David C. Norris" + newline  + newline;
        JTextArea textArea = new JTextArea(text);
        textArea.append("I owe credit to a few open source projects for bits and pieces of code." + newline  + newline);
        textArea.append("1. The author of the AePlayWave java sound player." + newline);
        textArea.append("2. 1up ModRcon group (for some server query code)" + newline);
        textArea.append("3. Main Image credit goes to author of the Urban Terror PHP Server Status project" + newline);
        textArea.setColumns(35);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setSize(textArea.getPreferredSize().width, 1);
        JOptionPane.showMessageDialog(null, textArea, "", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_jMenu2MouseClicked

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
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel mapNameLabel;
    private javax.swing.JTable playerTable;
    private javax.swing.JLabel resultsLabel;
    // End of variables declaration//GEN-END:variables

    public void updateTable() {
        maxClients = 0;
        activeClients = 0;
        Map serverInfo = server.getInfoMap(Constants.getIP(), Constants.getPort());
        Map statusInfo = server.getStatusMap(Constants.getIP(), Constants.getPort());
        if (statusInfo == null || serverInfo == null) {
            return;
        }
        if (serverInfo == null) {
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


        jLabel1.setText("Server: " + (String) statusInfo.get("sv_hostname"));

        String[] lines = server.getRawResponse(Constants.getIP(), Constants.getPort(), statusCommand).split("\\n");
        ArrayList<String> temp = new ArrayList<String>();
        temp.addAll(Arrays.asList(lines));
        temp.remove(0);
        tableModel.setData(temp);
    }

    public static void Launch(String pathToExe, String serverIP) throws Exception {
        File game = new File(pathToExe);
        ProcessBuilder processBuilder = new ProcessBuilder(pathToExe, "+connect", serverIP);
        processBuilder.directory(new File(game.getParent()));
        processBuilder.start();
    }
}

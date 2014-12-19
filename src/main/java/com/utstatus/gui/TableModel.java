package com.utstatus.gui;

/**
 *
 * @author dcnorris
 */
import com.utstatus.globals.Constants;
import com.utstatus.main.Player;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Model for table of features.
 */
public class TableModel extends AbstractTableModel {

    private static final String[] columnNames = {"Player Name", "Ping", "Score"};
    private static final int playerNameColumn = 0;
    private static final int pingColumn = 1;
    private static final int scoreColumn = 2;
    private ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private Player primaryPlayer = null;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return (tableData == null) ? 0 : tableData.size();
    }

    public Player getPrimaryPlayer() {
        return primaryPlayer;
    }

    public void setData(ArrayList<Player> players) {
        primaryPlayer = null;
        this.players = players;
        tableData.clear();
        for (Player p : players) {
            ArrayList<String> playerData = new ArrayList<String>();
            playerData.add(p.getName());
            Integer score = p.getScore();
            playerData.add(score.toString());
            Integer ping = p.getPing();
            playerData.add(ping.toString());
            tableData.add(playerData);
            if (p.getName().equals(Constants.getPlayerName())) {
                primaryPlayer = p;
            }
        }
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        ArrayList<String> temp = new ArrayList<String>();
        temp = tableData.get(row);
        switch (col) {
            case playerNameColumn:
                return temp.get(0);
            case pingColumn:
                return temp.get(2);
            case scoreColumn:
                return temp.get(1);
            default:
                System.out.println("Shouldn't reach here: " + row + " " + col);
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}

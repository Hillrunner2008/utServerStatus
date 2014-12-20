package com.utstatus.gui;

/**
 *
 * @author dcnorris
 */
import com.utstatus.model.Configuration;
import com.utstatus.model.Player;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

    private static final String[] columnNames = {"Player Name", "Ping", "Score"};
    private static final int playerNameColumn = 0;
    private static final int pingColumn = 1;
    private static final int scoreColumn = 2;
    private List<List<String>> tableData;
    private List<Player> players;
    private Player primaryPlayer;
    private final Configuration config;

    public TableModel(Configuration config) {
        this.config = config;
        tableData = new ArrayList<>();
        players = new ArrayList<>();
        primaryPlayer = null;
    }

    public List<Player> getPlayers() {
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

    public void setData(List<Player> players) {
        primaryPlayer = null;
        this.players = players;
        tableData.clear();
        for (Player p : players) {
            List<String> playerData = new ArrayList<>();
            playerData.add(p.getName());
            Integer score = p.getScore();
            playerData.add(score.toString());
            Integer ping = p.getPing();
            playerData.add(ping.toString());
            tableData.add(playerData);
            if (p.getName().equalsIgnoreCase(config.getPlayerName())) {
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
        List<String> temp = new ArrayList<>();
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

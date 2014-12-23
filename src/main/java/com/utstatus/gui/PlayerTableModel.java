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

public class PlayerTableModel extends AbstractTableModel {

    private static final String[] columnNames = {"Player Name", "Ping", "Score"};
    private static final int NAME_COLUMN = 0;
    private static final int PING_COLUMN = 1;
    private static final int SCORE_COLUMN = 2;
    private List<Player> players;
    private Player primaryPlayer;
    private final Configuration config;

    public PlayerTableModel(Configuration config) {
        this.config = config;
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
        return (players == null) ? 0 : players.size();
    }

    public Player getPrimaryPlayer() {
        return primaryPlayer;
    }

    public void setData(List<Player> players) {
        primaryPlayer = null;
        this.players = players;
        for (Player p : players) {
            if (p.getName().equalsIgnoreCase(config.getPlayerName())) {
                primaryPlayer = p;
                break;
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
        Player player = players.get(row);
        switch (col) {
            case NAME_COLUMN:
                return player.getName();
            case PING_COLUMN:
                return player.getPing();
            case SCORE_COLUMN:
                return player.getScore();
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

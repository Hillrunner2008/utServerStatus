package com.utstatus.gui;

/**
 *
 * @author dcnorris
 */
import com.utstatus.model.UtServer;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ServerTableModel extends AbstractTableModel {

    private static final String[] columnNames = {"Name", "Map", "Players", "Type", "Ping"};
    private static final int NAME_COLUMN = 0;
    private static final int MAP_COLUMN = 1;
    private static final int PLAYERS_COLUMN = 2;
    private static final int TYPE_COLUMN = 3;
    private static final int PING_COLUMN = 4;

    private List<UtServer> servers;

    public ServerTableModel(List<UtServer> servers) {
        this.servers = servers;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return (servers == null) ? 0 : servers.size();
    }

    public void setData(List<UtServer> servers) {
        this.servers = servers;
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        UtServer server = servers.get(row);
        switch (col) {
            case NAME_COLUMN:
                return server.getName();
            case MAP_COLUMN:
                return server.getMap();
            case PLAYERS_COLUMN:
                return server.getPlayerInfo();
            case TYPE_COLUMN:
                return server.getType();
            case PING_COLUMN:
                return server.getPing();
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

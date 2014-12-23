package com.utstatus.gui;

/**
 *
 * @author dcnorris
 */
import com.google.common.base.Strings;
import com.utstatus.model.Player;
import com.utstatus.model.UtServer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public class ServerTableModel extends AbstractTableModel {

    private static final Logger logger = getLogger(ServerTableModel.class);
    private static final String[] columnNames = {"Name", "Map", "Players", "Type", "IP/Port"};
    private static final int NAME_COLUMN = 0;
    private static final int MAP_COLUMN = 1;
    private static final int PLAYERS_COLUMN = 2;
    private static final int TYPE_COLUMN = 3;
    private static final int IP_PORT_COLUMN = 4;

    private final List<UtServer> servers;

    public ServerTableModel() {
        this.servers = new ArrayList<>();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return (servers == null) ? 0 : servers.size();
    }

    public void setData(Set<UtServer> serversList) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (UtServer server : serversList) {
                    server.refreshServer();
                    if (!Strings.isNullOrEmpty(server.getName())) {
//                        boolean hasBots = false;
//                        for (Player player : server.getPlayers()) {
//                            if (player.isIsBot()) {
//                                hasBots = true;
//                            }
//                        }
//                        if (!hasBots) {
                            servers.add(server);
                            fireTableDataChanged();
                            break;
//                        }

                    }

                }
                return null;
            }
        };
        worker.execute();
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
                return server.getCapacityInfo();
            case TYPE_COLUMN:
                return server.getType();
            case IP_PORT_COLUMN:
                return server.toString();
            default:
                logger.error("Shouldn't reach here: " + row + " " + col);
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        if (col == IP_PORT_COLUMN) {
            return true;
        }
        return false;
    }
}

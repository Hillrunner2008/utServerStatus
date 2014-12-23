package com.utstatus.gui;

/**
 *
 * @author dcnorris
 */
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.utstatus.model.UtServer;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerTableModel extends AbstractTableModel {

    private static final Logger logger = LoggerFactory.getLogger(ServerTableModel.class);
    private static final String[] columnNames = {"Name", "Map", "Players", "Type", "Ping"};
    private static final int NAME_COLUMN = 0;
    private static final int MAP_COLUMN = 1;
    private static final int PLAYERS_COLUMN = 2;
    private static final int TYPE_COLUMN = 3;
    private static final int PING_COLUMN = 4;

    private List<UtServer> servers;

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

    public void setData(Set<UtServer> servers) {
        this.servers = new ArrayList<>();
        this.servers.addAll(servers);
        SwingWorker x = new SwingWorker() {
            @Override
            protected String doInBackground() throws Exception {
                servers.stream().forEach(server -> {
                    server.refreshServer();
                    fireTableDataChanged();
                });
                return "";
            }

            @Override
            protected void done() {
                fireTableDataChanged();
            }
        };
        x.execute();
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
            case PING_COLUMN:
                return server.getPing();
            default:
                logger.error("Shouldn't reach here: " + row + " " + col);
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}

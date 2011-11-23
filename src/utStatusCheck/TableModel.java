package utStatusCheck;

/**
 *
 * @author dcnorris
 */
import java.util.ArrayList;
import java.util.Scanner;
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

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return (tableData == null) ? 0 : tableData.size();
    }

    public void setData(ArrayList<String> data) {
        tableData.clear();
        char startPosition = '"';
        for (String s : data) {
            ArrayList<String> playerData = new ArrayList<String>();
            String ans = "";
            Scanner sc = new Scanner(s);
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i)==startPosition) {
                    for (int j = i+1; j < s.length() - 1; j++) {
                        ans += s.charAt(j);
                    }
                    break;
                }
            }
            playerData.add(ans);
            Integer score = sc.nextInt();
            playerData.add(score.toString());
            Integer ping = sc.nextInt();
            playerData.add(ping.toString());
            tableData.add(playerData);
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

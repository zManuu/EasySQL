package EasySQL;

import EasySQL.object.EasyTable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EasySQL {

    public static void main(String[] args) {
        EasyTable table = new EasyTable("EasySqlDb", "root", "", "EasyTable");
        table.registerRow("Name");
        table.registerRow("Money");
        table.registerRow("AdminLevel");
        table.insertAutoIncrementID("Test", 500, 5000);

        String name = table.getString("Name", "ID", 50);
    }
}

package EasySQL;

import EasySQL.object.EasyTable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EasySQL {

    public static void main(String[] args) {
        EasyTable table = new EasyTable("EasySqlDb", "root", "", "EasyTable");
        Map<String, Object> values = new LinkedHashMap<>();
        values.put("Name", "Manuel");
        values.put("Money", 50000);
        values.put("AdminLevel", 10);
        table.insertAutoIncrement(values);

        String name = table.getString("Name", "ID", 50);
    }
}

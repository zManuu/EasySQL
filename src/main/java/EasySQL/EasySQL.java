package EasySQL;

import EasySQL.object.EasyTable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is only used as an example, you can take a look at the code and maybe understand better how the EasyTable class is used
 */
public class EasySQL {

    public static void main(String[] args) {

        EasyTable table = new EasyTable("EasySqlDb", "root", "", "EasyTable");
        table.registerRow("Name");
        table.registerRow("Money");
        table.registerRow("AdminLevel");
        table.insertAutoIncrementID("Test", 500, 5000);

        User user = new User();
        user.setName(table.getString("Name", "ID", 51));
        user.setAdminLevel(table.getInt("AdminLevel", "ID", 51));
        user.setMoney(table.getInt("Money", "ID", 51));

        System.out.println(user.toString());
    }

    private static class User {

        private String name;
        private int money;
        private int adminLevel;

        public void setName(String name) {
            this.name = name;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public void setAdminLevel(int adminLevel) {
            this.adminLevel = adminLevel;
        }

        public String getName() {
            return name;
        }
        public int getMoney() {
            return money;
        }
        public int getAdminLevel() {
            return adminLevel;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", money=" + money +
                    ", adminLevel=" + adminLevel +
                    '}';
        }
    }

}

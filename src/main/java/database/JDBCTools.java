package database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Angot Maxime on 18/04/16.
 */
public class JDBCTools {


    private static Map<String, Connection> connections = new HashMap<String, Connection>();

    /*
        //public static Connection connectTODB = null;
    public static void main(String[] argv) throws SQLException {
        JDBCTools tool = new JDBCTools();
        tool.connectToMySql("computer-database-db");
        tool.getComputers();
        tool.closeConnect();
        tool.getComputers();
        System.out.println(JDBCTools.connectTODB.getCatalog());
    }

    public void getComputers() throws SQLException {
        if (connectTODB == null) {
            return;
        }
        if (!connectTODB.isClosed()) {

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            Statement stmt = connectTODB.createStatement();

            String sql = "SELECT * FROM `computer` ";
            ResultSet rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name ame 	introduced 	discontinued 	company_id
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Timestamp first = rs.getTimestamp("introduced");
                Timestamp last = rs.getTimestamp("discontinued");
                int compID = rs.getInt("company_id");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Nom: " + name);
                System.out.print(", Introduit: " + first);
                System.out.print(", Stoppé: " + last);
                System.out.println(", ID compagnie: " + last);
            }
            rs.close();
        }
    }
    */



    public void connectToMySql(String name) throws SQLException {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        if (connections.get(name) != null) {
            return;
        }
        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            //TODO gérer les throws
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/"+ name +"?zeroDateTimeBehavior=convertToNull",
                            "admincdb", "qwerty1234");

        } catch (SQLException e) {
            //TODO gérer les throws
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
            //connectTODB = connection;
            connections.put(name, connection);
        } else {
            //TODO gérer les throws
            System.out.println("Failed to make connection!");
            return;
        }

    }

    public Connection getConnection(String name) throws SQLException {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        return connections.get(name);
    }

    public void closeConnect(String name) throws SQLException {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        for (Map.Entry<String, Connection> c : connections.entrySet()) {
            if (c.getKey().equals(name)) {
                c.getValue().close();
                connections.remove(c);
            }
        }
    }
}

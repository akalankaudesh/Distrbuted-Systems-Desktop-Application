package lk.nsbm.com.jr.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnection {

    private static Connection connection1;
    private static Connection connection2;
    private static Connection connection3;


    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        if (SelectConn.con1) {
            if (connection1 == null) {
                connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "1234");
            }
            if (connection2 == null) {
                connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "1234");
            }
            if (connection3 == null) {
                connection3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "1234");
            }
            connection = connection1;

        } else if (SelectConn.con2) {
            if (connection2 == null) {
                connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "1234");
            }
            if (connection1 == null) {
                connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "1234");
            }
            if (connection3 == null) {
                connection3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "1234");
            }
            connection = connection2;

        } else if (SelectConn.con3) {
            if (connection3 == null) {
                connection3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "1234");
            }
            if (connection2 == null) {
                connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "1234");
            }
            if (connection1 == null) {
                connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "1234");
            }
            connection = connection3;

        }


        return connection;
    }

    public static ArrayList<Connection> getConnections() {
        ArrayList<Connection> connectionArrayList =new ArrayList<>();
        if (SelectConn.con1) {
            connectionArrayList.add(connection2);
            connectionArrayList.add(connection3);

        } else if (SelectConn.con2) {
            connectionArrayList.add(connection1);
            connectionArrayList.add(connection3);


        } else if (SelectConn.con3) {
            connectionArrayList.add(connection1);
            connectionArrayList.add(connection2);

        }
        return connectionArrayList;
    }

}

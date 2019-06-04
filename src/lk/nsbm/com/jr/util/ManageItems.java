package lk.nsbm.com.jr.util;

import lk.nsbm.com.jr.db.DBConnection;
import lk.nsbm.com.jr.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageItems {

    public static ArrayList<Item> getItems() {
        ArrayList<Item> alItems = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM items");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                String code = rst.getString(1);
                String description = rst.getString(2);
                double unitPrice = rst.getDouble(3);
                int qty = rst.getInt(4);
                Item item = new Item(code, description, unitPrice, qty);
                alItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alItems;
    }

    public static void createItem(Item item) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = DBConnection.getConnection();
                    ArrayList<Connection> connectionArrayList = new ArrayList<>();
                    connectionArrayList.add(connection);
                    connectionArrayList.addAll(DBConnection.getConnections());
                    for (Connection conn : connectionArrayList) {
                        try {
                            PreparedStatement pstm = conn.prepareStatement("INSERT INTO items VALUES (?,?,?,?)");
                            pstm.setObject(1, item.getCode());
                            pstm.setObject(2, item.getDescription());
                            pstm.setObject(3, item.getQtyOnHand());
                            pstm.setObject(4, item.getUnitPrice());
                            pstm.executeUpdate();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    public static void updateItem(String code, Item item) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = DBConnection.getConnection();
                    ArrayList<Connection> connectionArrayList = new ArrayList<>();
                    connectionArrayList.add(connection);
                    connectionArrayList.addAll(DBConnection.getConnections());
                    for (Connection conn : connectionArrayList) {
                        try {
                            PreparedStatement pstm = conn.prepareStatement("UPDATE items SET description=?,unit_price=?,qty=? WHERE item_code=?");
                            pstm.setObject(4, code);
                            pstm.setObject(1, item.getDescription());
                            pstm.setObject(2, item.getUnitPrice());
                            pstm.setObject(3, item.getQtyOnHand());
                            pstm.executeUpdate();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static void deleteItem(String code) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = DBConnection.getConnection();
                    ArrayList<Connection> connectionArrayList = new ArrayList<>();
                    connectionArrayList.add(connection);
                    connectionArrayList.addAll(DBConnection.getConnections());
                    for (Connection conn : connectionArrayList) {
                        try {
                            PreparedStatement pstm = conn.prepareStatement("DELETE FROM items WHERE item_code=?");
                            pstm.setObject(1, code);
                            pstm.executeUpdate();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static Item findItem(String itemCode) {
        for (Item item : getItems()) {
            if (item.getCode().equals(itemCode)) {
                return item;
            }
        }
        return null;
    }
}

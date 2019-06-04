package lk.nsbm.com.jr.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lk.nsbm.com.jr.db.DBConnection;
import lk.nsbm.com.jr.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageCustomers {

    public static ArrayList<Customer> getCustomers() {

        ArrayList<Customer> alCustomers = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                Customer customer = new Customer(id, name, address);
                alCustomers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alCustomers;

    }

    public static boolean createCustomer(final Customer customer) {
        final boolean results[] = new boolean[1];
        try {
            if (!isAlreadyExists(customer)) {
                results[0] = true;
                Connection connection = DBConnection.getConnection();
                ArrayList<Connection> connectionArrayList = new ArrayList<>();
                connectionArrayList.add(connection);
                connectionArrayList.addAll(DBConnection.getConnections());
                for (Connection conn : connectionArrayList) {
                    try {
                        PreparedStatement pstm = conn.prepareStatement("INSERT INTO  customer (cusid,cusname,address) VALUES (?,?,?)");
                        pstm.setObject(1, customer.getId());
                        pstm.setObject(2, customer.getName());
                        pstm.setObject(3, customer.getAddress());
                        pstm.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            } else {
                results[0] = false;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        new Alert(Alert.AlertType.ERROR, "Customer  ID Exists!", ButtonType.OK).showAndWait();
                    }
                });

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return results[0];

    }

    private static boolean isAlreadyExists(Customer customer) {
        final boolean results[] = new boolean[1];
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "select * from customer where  customer.cusid='" + customer.getId() + "'";

            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            if (resultSet.next()) {
                if (!resultSet.getString(1).isEmpty())
                    results[0] = true;
                else
                    results[0] = false;
            }else
                results[0] = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results[0];

    }

    public static void updateCustomer(String customerID, Customer customer) {


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
                            PreparedStatement pstm = conn.prepareStatement("UPDATE customer SET  cusname=?, address=? WHERE cusid=?");
                            pstm.setObject(3, customer.getId());
                            pstm.setObject(1, customer.getName());
                            pstm.setObject(2, customer.getAddress());
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

    public static void deleteCustomer(String customerID) {

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
                            PreparedStatement pstm = conn.prepareStatement("DELETE FROM customer WHERE cusid=?");
                            pstm.setObject(1, customerID);
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

    public static Customer findCustomer(String id) {
        for (Customer customer : getCustomers()) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    public static int getLastInsertId() {
        int id = -1;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT LAST_INSERT_ID()");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                id = rst.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;

    }

}

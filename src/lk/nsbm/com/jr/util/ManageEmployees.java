package lk.nsbm.com.jr.util;

import lk.nsbm.com.jr.db.DBConnection;
import lk.nsbm.com.jr.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageEmployees {

    public static ArrayList<Employee> getEmployees() {

        ArrayList<Employee> alEmployees = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM employee");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                String id = rst.getString(1);
                String address = rst.getString(2);
                String contact = rst.getString(3);
                String name = rst.getString(4);
                Employee employee = new Employee(id, name, address, contact);
                alEmployees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alEmployees;

    }

    public static void createEmployee(Employee employee) {

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
                            PreparedStatement pstm = conn.prepareStatement("INSERT INTO employee (address,contact,name) VALUES (?,?,?)");
                            pstm.setObject(1, employee.getAddress());
                            pstm.setObject(2, employee.getContact());
                            pstm.setObject(3, employee.getName());
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

    public static void updateEmployee(String employeeID, Employee employee) {
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
                            PreparedStatement pstm = conn.prepareStatement("UPDATE employee SET name=?,address=?, contact=? WHERE id=?");
                            pstm.setObject(4, employee.getId());
                            pstm.setObject(1, employee.getName());
                            pstm.setObject(2, employee.getAddress());
                            pstm.setObject(3, employee.getContact());
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

    public static void deleteEmployee(String emeployeeID) {
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
                            PreparedStatement pstm = conn.prepareStatement("DELETE FROM employee WHERE id=?");
                            pstm.setString(1, emeployeeID);
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

    public static Employee findEmployee(String id) {
        for (Employee employee : getEmployees()) {
            if (employee.getId().equals(id)) {
                return employee;
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

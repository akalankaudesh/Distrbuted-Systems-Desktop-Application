package lk.nsbm.com.jr.util;

import lk.nsbm.com.jr.db.DBConnection;
import lk.nsbm.com.jr.model.SupplierOrder;
import lk.nsbm.com.jr.view.util.SupplierOrderTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageSupplierOders {


    public static ArrayList<SupplierOrderTM> getOrders() {
        ArrayList<SupplierOrderTM> supplierOrderTMArrayList = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM supplier_order");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                String orderID = rst.getString(1);
                String supplierName = rst.getString(2);
                String code = rst.getString(3);
                String orderDate = rst.getString(4);
                double unitPrice = rst.getDouble(5);
                String description = rst.getString(6);
                int qty = rst.getInt(7);
                double total = rst.getDouble(8);


                SupplierOrderTM supplierOrderTM = new SupplierOrderTM(code, description, qty, unitPrice, total, supplierName, orderDate, orderID);
                supplierOrderTMArrayList.add(supplierOrderTM);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplierOrderTMArrayList;
    }


    public static void createSupplierOrder(SupplierOrder supplierOrder) {
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
                            PreparedStatement pstm = conn.prepareStatement("INSERT INTO  supplier_order (supplier_name,item_code,supplier_order_date,unit_price,description,qty,supplier_order_total) VALUES (?,?,?,?,?,?,?)");
                            pstm.setString(1, supplierOrder.getSupplierName());
                            pstm.setString(2, supplierOrder.getCode());
                            pstm.setString(3, supplierOrder.getOrderDate());
                            pstm.setDouble(4, supplierOrder.getUnitPrice());
                            pstm.setString(5, supplierOrder.getDescription());
                            pstm.setInt(6, supplierOrder.getQty());
                            pstm.setDouble(7, supplierOrder.getTotal());
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

}











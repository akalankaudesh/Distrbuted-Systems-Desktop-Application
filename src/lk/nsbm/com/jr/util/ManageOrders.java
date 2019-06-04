package lk.nsbm.com.jr.util;

import lk.nsbm.com.jr.db.DBConnection;
import lk.nsbm.com.jr.model.Customer;
import lk.nsbm.com.jr.model.Order;

import java.sql.*;
import java.util.ArrayList;

public class ManageOrders {

    public static ArrayList<Order> getOrders(){
        ArrayList<Order> alOrders = new ArrayList<>();
        double total=0.00;
        Customer customer =null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM `orders`");
            ResultSet rst = pstm.executeQuery();
            while(rst.next()){
                String orderId = rst.getString(1);
                Date orderDate = rst.getDate(2);
                String customerId = rst.getString(3);
                customer= ManageCustomers.findCustomer(customerId);

                PreparedStatement pstm2 = connection.prepareStatement("SELECT order_detailpk.id FROM order_detailpk WHERE order_id=?");
                pstm2.setObject(1,orderId);
                ResultSet rst2 = pstm2.executeQuery();

                if (rst2.next()){
                    Long  orderDetaild= rst2.getLong(1);
                    PreparedStatement pstm3 = connection.prepareStatement("SELECT * FROM order_detail WHERE order_detail.order_detailpk=?");
                    pstm2.setObject(1,orderDetaild);
                    ResultSet rst3 = pstm3.executeQuery();
                     total = rst3.getDouble(3)*rst3.getDouble(4);
                }

                Order order = new Order(orderId, orderDate.toLocalDate(), customerId,total);
                order.setCustomerName(customer.getName());
                alOrders.add(order);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alOrders;
    }

    public static String generateOrderId(){
        return getOrders().size() + 1 + "";
    }

    public static void createOrder(Order order){
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Connection connection = DBConnection.getConnection();
//                    ArrayList<Connection> connectionArrayList = new ArrayList<>();
//                    connectionArrayList.add(connection);
//                    connectionArrayList.addAll(DBConnection.getConnections());
//                    for (Connection conn : connectionArrayList) {
//                        try {
//                            PreparedStatement pstm = conn.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
//                            pstm.setObject(1, item.getCode());
//                            pstm.setObject(2, item.getDescription());
//                            pstm.setObject(3, item.getQtyOnHand());
//                            pstm.setObject(4, item.getUnitPrice());
//                            pstm.executeUpdate();
//
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//
//













//        Connection connection = null;
//        try {
//
//            connection = DBConnection.getConnection();
//
//            // connection.setTransactionIsolation();
//
//
//                PreparedStatement pstm = connection.prepareStatement("INSERT INTO `oders` VALUES (?,?,?)");
//            pstm.setObject(1,order.getId());
//            pstm.setObject(2,order.getDate());
//            pstm.setObject(3,order.getCustomerId());
//            pstm.executeUpdate();
//
//
//
//            PreparedStatement pstm2 = connection.prepareStatement("INSERT INTO orderdetails VALUES (?,?,?,?)");
//            for (OrderDetail orderDetail : order.getOrderDetails()) {
//                pstm2.setObject(1,order.getId());
//                pstm2.setObject(2, orderDetail.getCode());
//                pstm2.setObject(3,orderDetail.getQty());
//                pstm2.setObject(4, orderDetail.getUnitPrice());
//               pstm2.executeUpdate();
//
////                if (true) {
////                    throw new RuntimeException();
////                }
//
//
//
//                int qtyOnHand = ManageItems.findItem(orderDetail.getCode()).getQtyOnHand();
//                qtyOnHand -= orderDetail.getQty();
//
//                PreparedStatement pstm3 = connection.prepareStatement("UPDATE item SET qtyOnHand=? WHERE code=?");
//                pstm3.setObject(1,qtyOnHand);
//                pstm3.setObject(2,orderDetail.getCode());
//
//                 pstm3.executeUpdate();
//
//            }
//
//
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }

//        ordersDB.add(order);
//        for (OrderDetail orderDetail : order.getOrderDetails()) {
//            Item item = ManageItems.findItem(orderDetail.getCode());
//            item.setQty(item.getQty() - orderDetail.getQty());
//        }
    }

    public static Order findOrder(String orderId){
        for (Order order : getOrders()) {
            if (order.getId().equals(orderId)){
                return order;
            }
        }
        return null;
    }
}

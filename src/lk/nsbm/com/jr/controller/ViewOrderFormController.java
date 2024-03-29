package lk.nsbm.com.jr.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.nsbm.com.jr.util.JasperUtil;
import lk.nsbm.com.jr.util.ManageItems;
import lk.nsbm.com.jr.view.util.OrderDetailTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class ViewOrderFormController {
    @FXML
    private JFXDatePicker txtOrderDate;
    @FXML
    private JFXTextField txtOrderID;
    @FXML
    private JFXTextField txtCustomerID;
    @FXML
    private JFXTextField txtItemCode;
    @FXML
    private JFXTextField txtCustomerName;
    @FXML
    private JFXTextField txtDescription;
    @FXML
    private JFXTextField txtQtyOnHand;
    @FXML
    private JFXTextField txtUnitPrice;
    @FXML
    private JFXTextField txtQty;
    @FXML
    private TableView<OrderDetailTM> tblOrderDetails;
    @FXML
    private Label lblTotal;

    private String orderId;
    private double orderTotal;

    @FXML
    private void navigateToMain(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/nsbm/com/jr/view/SearchOrderForm.fxml"));
        Scene mainScene = new Scene(root);
        Stage mainStage = (Stage) lblTotal.getScene().getWindow();
        mainStage.setScene(mainScene);

        TranslateTransition tt1 = new TranslateTransition(Duration.millis(300), root.lookup("AnchorPane"));
        tt1.setToX(0);
        tt1.setFromX(-mainScene.getWidth());
        tt1.play();

        mainStage.centerOnScreen();
    }

    public void initialize() {
        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderDetailTM>() {
            @Override
            public void changed(ObservableValue<? extends OrderDetailTM> observable, OrderDetailTM oldValue, OrderDetailTM selectedOrderDetail) {

                if (selectedOrderDetail == null) {
                    // Clear Selection
                    return;
                }

                txtItemCode.setText(selectedOrderDetail.getCode());
                txtDescription.setText(selectedOrderDetail.getDescription());
                txtUnitPrice.setText(selectedOrderDetail.getUnitPrice() + "");
                txtQty.setText(selectedOrderDetail.getQty() + "");
                txtQtyOnHand.setText(ManageItems.findItem(txtItemCode.getText()).getQtyOnHand() + "");

            }
        });
    }

    public void setInitData(String orderId, double orderTotal) {
        this.orderId = orderId;
        this.orderTotal = orderTotal;
        lblTotal.setText("Total : " + orderTotal);
        fillData();
    }

    public void fillData() {
//        Order order = ManageOrders.findOrder(this.orderId);
//        txtCustomerID.setText(order.getId());
//        txtOrderID.setText(order.getId());
//        txtOrderDate.setValue(order.getDate());
//        txtCustomerName.setText(ManageCustomers.findCustomer(order.getCustomerId()).getName());
//
//        ArrayList<OrderDetail> orderDetails = order.getOrderDetails();
//        ObservableList<OrderDetailTM> details = FXCollections.observableArrayList();
//
//        for (OrderDetail orderDetail : orderDetails) {
//            details.add(new OrderDetailTM(orderDetail.getCode(),
//                    orderDetail.getDescription(),
//                    orderDetail.getQty(),
//                    orderDetail.getUnitPrice(),
//                    orderDetail.getQty() * orderDetail.getUnitPrice()));
//        }
//        tblOrderDetails.setItems(details);
    }

    public void btnPrintOrder_OnAction(ActionEvent actionEvent) throws SQLException, JRException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("customerId", txtCustomerID.getText());
        params.put("customerName", txtCustomerName.getText());
        params.put("orderDate", txtOrderDate.getValue());
        params.put("orderId", txtOrderID.getText());
        params.put("total", orderTotal);
        JasperUtil.showReport(JasperUtil.REPORT_BILL,
                params,
                new JRBeanCollectionDataSource(tblOrderDetails.getItems()));
    }
}

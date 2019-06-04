package lk.nsbm.com.jr.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.nsbm.com.jr.main.AppInitializer;
import lk.nsbm.com.jr.model.Order;
import lk.nsbm.com.jr.model.OrderDetail;
import lk.nsbm.com.jr.util.JasperUtil;
import lk.nsbm.com.jr.util.ManageOrders;
import lk.nsbm.com.jr.view.util.OrderTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchOrderFormController {

    @FXML
    private JFXTextField txtSearchOrder;
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<OrderTM> tblOrders;

    private ObservableList<OrderTM> olOrders;

    public void initialize(){
        tblOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        ArrayList<Order> ordersDB = ManageOrders.getOrders();
        olOrders = FXCollections.observableArrayList();
        for (Order order : ordersDB) {
            olOrders.add(new OrderTM(order.getId(),order.getDate(),order.getCustomerId(),order.getCustomerName(),order.getTotal()));
        }

        tblOrders.setItems(olOrders);
    }

    private double getOrderTotal(ArrayList<OrderDetail> orderDetails){
        double total = 0.0;
        for (OrderDetail orderDetail : orderDetails) {
            total += orderDetail.getQty() * orderDetail.getUnitPrice();
        }
        return total;
    }

    @FXML
    private void txtOrderId_OnKeyReleased(KeyEvent keyEvent) {

        ObservableList<OrderTM> tempList = FXCollections.observableArrayList();
//        System.out.println("TEST : " + olOrders);
        for (OrderTM olOrder : olOrders) {
            if (olOrder.getOrderId().startsWith(txtSearchOrder.getText())){
                tempList.add(olOrder);
            }
        }

        tblOrders.setItems(tempList);
        
    }

    @FXML
    private void navigateToHome(MouseEvent mouseEvent) throws IOException {
        AppInitializer.navigateToHome(root, (Stage) this.root.getScene().getWindow());
    }

    @FXML
    private void tblOrders_OnClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2){

            OrderTM selectedItem = tblOrders.getSelectionModel().getSelectedItem();

            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/nsbm/com/jr/view/ViewOrderForm.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ViewOrderFormController controller = fxmlLoader.getController();
            controller.setInitData(selectedItem.getOrderId(), selectedItem.getTotal());
            Scene scene = new Scene(root);
            ((Stage)tblOrders.getScene().getWindow()).setScene(scene);
        }
    }

    public void btnShowReport_OnAction(ActionEvent actionEvent) throws SQLException, JRException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("totalOrders", tblOrders.getItems().size());
        JasperUtil.showReport(JasperUtil.REPORT_ORDER,
                params,
                new JRBeanCollectionDataSource(tblOrders.getItems()));
    }
}

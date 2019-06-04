package lk.nsbm.com.jr.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.nsbm.com.jr.main.AppInitializer;
import lk.nsbm.com.jr.model.SupplierOrder;
import lk.nsbm.com.jr.util.ManageSupplierOders;
import lk.nsbm.com.jr.view.util.SupplierOrderTM;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierOrderFormController implements Initializable {
    public JFXTextField txtItemCode;
    public JFXTextField txtSupplierName;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public TableView<SupplierOrderTM> tblOrderDetails;
    //    public TableColumn orderId;
//    public TableColumn orderDate;
//    public TableColumn itemCode;
//    public TableColumn description;
//    public TableColumn qty;
//    public TableColumn unitPrice;
//    public TableColumn total;
    public JFXDatePicker txtOrderDate;
    public Label lblTotalValue;
    public Label blTotalValue;
    public JFXButton saveOrderBtn;
    public AnchorPane root;

    public void navigateToMain(MouseEvent mouseEvent) throws IOException {
        AppInitializer.navigateToHome(root, (Stage) this.root.getScene().getWindow());
    }

    public void btnPrintOrder_OnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOrder_OnAction(ActionEvent actionEvent) {

        if (txtItemCode.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Item Code is empty", ButtonType.OK).showAndWait();
            txtItemCode.requestFocus();
            return;
        }
        if (txtDescription.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Item Description is empty", ButtonType.OK).showAndWait();
            txtDescription.requestFocus();
            return;
        }
        if (txtUnitPrice.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Unit Price is empty", ButtonType.OK).showAndWait();
            txtUnitPrice.requestFocus();
            return;
        }
        if (txtQty.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Qty On Hand is empty", ButtonType.OK).showAndWait();
            txtQty.requestFocus();
            return;
        }

        if (txtOrderDate.getEditor().getText() == null || txtOrderDate.getEditor().getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "Order Date is empty", ButtonType.OK).showAndWait();
            txtOrderDate.requestFocus();
            return;
        }
        blTotalValue.setText(Double.toString(Double.parseDouble(txtUnitPrice.getText()) * Double.parseDouble(txtQty.getText())));


        SupplierOrder supplierOrder = new SupplierOrder(txtItemCode.getText(), txtDescription.getText(), Integer.parseInt(txtQty.getText()), Double.parseDouble(txtUnitPrice.getText()), Double.parseDouble(blTotalValue.getText()), txtSupplierName.getText(), txtOrderDate.getValue().toString());
        ManageSupplierOders.createSupplierOrder(supplierOrder);
        new Alert(Alert.AlertType.INFORMATION, "Supplier Order has been saved successfully", ButtonType.OK).showAndWait();
        reset();
        ArrayList<SupplierOrderTM> supplierOrderTMArrayList = ManageSupplierOders.getOrders();
        ObservableList<SupplierOrderTM> supplierOrderTMObservableList = FXCollections.observableArrayList(supplierOrderTMArrayList);
        tblOrderDetails.setItems(supplierOrderTMObservableList);



    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblOrderDetails.getColumns().get(0).setStyle("-jr-alignment:center");
        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderID"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("total"));


        ArrayList<SupplierOrderTM> supplierOrderTMArrayList = ManageSupplierOders.getOrders();
        ObservableList<SupplierOrderTM> supplierOrderTMObservableList = FXCollections.observableArrayList(supplierOrderTMArrayList);

        tblOrderDetails.setItems(supplierOrderTMObservableList);

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SupplierOrderTM>() {
            @Override
            public void changed(ObservableValue<? extends SupplierOrderTM> observable, SupplierOrderTM oldValue, SupplierOrderTM supplierOrderTM) {

                if (supplierOrderTM == null) {
                    // Clear Selection
                    return;
                }

                txtSupplierName.setText(supplierOrderTM.getSupplierName());
                txtItemCode.setText(supplierOrderTM.getCode());
                txtDescription.setText(supplierOrderTM.getDescription());
                txtQty.setText(Integer.toString(supplierOrderTM.getQty()));
                blTotalValue.setText(Double.toString(supplierOrderTM.getTotal()));
                txtUnitPrice.setText(Double.toString(supplierOrderTM.getUnitPrice()));

                txtOrderDate.getEditor().setText(supplierOrderTM.getOrderDate());

                saveOrderBtn.setDisable(true);
                setEditable(false);

            }
        });

        tblOrderDetails.setItems(supplierOrderTMObservableList);
        txtQty.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtQty.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });




        txtUnitPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtUnitPrice.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private void setEditable(boolean state) {
        txtSupplierName.setEditable(state);
        txtItemCode.setEditable(state);
        txtDescription.setEditable(state);
        txtQty.setEditable(state);
        txtOrderDate.setEditable(false);
        txtUnitPrice.setEditable(state);
    }


    @FXML
    private void btnReport_OnAction(ActionEvent actionEvent) throws SQLException, JRException {
      /*  HashMap<String, Object> params = new HashMap<>();
        params.put("totalCustomers", tblCustomers.getItems().size());
        JasperUtil.showReport(JasperUtil.REPORT_CUSTOMER,
                params,
                new JRBeanCollectionDataSource(tblCustomers.getItems()));*/
    }

    private void reset() {
        txtSupplierName.clear();
        txtItemCode.clear();
        txtDescription.clear();
        txtQty.clear();
        txtUnitPrice.clear();
        txtOrderDate.getEditor().clear();
        blTotalValue.setText("");

        saveOrderBtn.setDisable(false);
        tblOrderDetails.getSelectionModel().clearSelection();
    }


    public void btnClearOrder_OnAction(ActionEvent actionEvent) {
        reset();
    }
}

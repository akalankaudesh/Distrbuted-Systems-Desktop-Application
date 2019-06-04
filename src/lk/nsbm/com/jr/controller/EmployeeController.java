package lk.nsbm.com.jr.controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.nsbm.com.jr.main.AppInitializer;
import lk.nsbm.com.jr.model.Employee;
import lk.nsbm.com.jr.util.JasperUtil;
import lk.nsbm.com.jr.util.ManageEmployees;
import lk.nsbm.com.jr.view.util.EmployeeTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtEmployeeName;
    @FXML
    private JFXTextField txtEmployeeAddress;
    @FXML
    private JFXTextField txtEmployeeContact;

    @FXML
    private TableView<EmployeeTM> tblEmployees;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblEmployees.getColumns().get(0).setStyle("-jr-alignment:center");
        tblEmployees.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblEmployees.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblEmployees.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblEmployees.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));

//        btnSave.setDisable(true);
        btnDelete.setDisable(true);

        ArrayList<Employee> employeesDb = ManageEmployees.getEmployees();
        ObservableList<Employee> employees = FXCollections.observableArrayList(employeesDb);
        ObservableList<EmployeeTM> tblItems = FXCollections.observableArrayList();
        for (Employee employee: employees) {
            tblItems.add(new EmployeeTM(employee.getId(), employee.getName(), employee.getAddress(), employee.getContact()));
        }
        tblEmployees.setItems(tblItems);

        txtEmployeeContact.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtEmployeeContact.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        tblEmployees.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmployeeTM>() {
            @Override
            public void changed(ObservableValue<? extends EmployeeTM> observable, EmployeeTM oldValue, EmployeeTM selectedEmployee) {

                if (selectedEmployee == null) {
                    // Clear Selection
                    return;
                }

                txtEmployeeName.setText(selectedEmployee.getName());
                txtEmployeeAddress.setText(selectedEmployee.getAddress());
                txtEmployeeContact.setText(selectedEmployee.getContact());

                btnSave.setDisable(false);
                btnDelete.setDisable(false);

            }
        });
    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        AppInitializer.navigateToHome(root, (Stage) this.root.getScene().getWindow());
    }

    @FXML
    private void btnSave_OnAction(ActionEvent event) {

        if (txtEmployeeName.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Employee Name is empty", ButtonType.OK).showAndWait();
            txtEmployeeName.requestFocus();
            return;
        } else if (txtEmployeeAddress.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Employee Address is empty", ButtonType.OK).showAndWait();
            txtEmployeeAddress.requestFocus();
            return;
        } else if (txtEmployeeContact.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Employee Contact is empty", ButtonType.OK).showAndWait();
            txtEmployeeContact.requestFocus();
            return;
        }else if (txtEmployeeContact.getText().length()!=10 || txtEmployeeContact.getText().charAt(0)!='0' ) {
            new Alert(Alert.AlertType.ERROR, "Employee Contact is Invalid", ButtonType.OK).showAndWait();
            txtEmployeeContact.requestFocus();
            return;
        }

        if (tblEmployees.getSelectionModel().isEmpty()) {
            // New

            ObservableList<EmployeeTM> items = tblEmployees.getItems();
//            for (CustomerTM customerTM : items) {
//                if (customerTM.getId().equals(txtCustomerId.getText())) {
//                    new Alert(Alert.AlertType.ERROR, "Duplicate Customer IDs are not allowed").showAndWait();
//                    txtCustomerId.requestFocus();
//                    return;
//                }
//            }

            Employee employee = new Employee(txtEmployeeName.getText(), txtEmployeeAddress.getText(), txtEmployeeContact.getText());
            ManageEmployees.createEmployee(employee);

            EmployeeTM employeeTM = new EmployeeTM(Integer.toString(ManageEmployees.getLastInsertId()), txtEmployeeName.getText(), txtEmployeeAddress.getText(), txtEmployeeContact.getText());
            tblEmployees.getItems().add(employeeTM);


            new Alert(Alert.AlertType.INFORMATION, "Employee has been saved successfully", ButtonType.OK).showAndWait();
            tblEmployees.scrollTo(employeeTM);

        } else {
            // Update

            EmployeeTM selectedEmployee = tblEmployees.getSelectionModel().getSelectedItem();
            selectedEmployee.setName(txtEmployeeName.getText());
            selectedEmployee.setAddress(txtEmployeeAddress.getText());
            tblEmployees.refresh();

            String selectedEmployeeID = tblEmployees.getSelectionModel().getSelectedItem().getId();

            ManageEmployees.updateEmployee(selectedEmployeeID, new Employee(selectedEmployee.getId(),
                    txtEmployeeName.getText(),
                    txtEmployeeAddress.getText(), txtEmployeeContact.getText()));

            new Alert(Alert.AlertType.INFORMATION, "Employee has been updated successfully", ButtonType.OK).showAndWait();
        }

        reset();

    }

    @FXML
    private void btnDelete_OnAction(ActionEvent event) {
        Alert confirmMsg = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this Employee?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = confirmMsg.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            String selectedEmployee = tblEmployees.getSelectionModel().getSelectedItem().getId();

            tblEmployees.getItems().remove(tblEmployees.getSelectionModel().getSelectedItem());
            ManageEmployees.deleteEmployee(selectedEmployee);
            reset();
        }

    }

    @FXML
    private void btnAddNew_OnAction(ActionEvent actionEvent) {
        reset();
    }

    @FXML
    private void btnReport_OnAction(ActionEvent actionEvent) throws SQLException, JRException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("totalCustomers", tblEmployees.getItems().size());
        JasperUtil.showReport(JasperUtil.REPORT_EMPLOYEE,
                params,
                new JRBeanCollectionDataSource(tblEmployees.getItems()));
    }

    private void reset() {
        txtEmployeeName.clear();
        txtEmployeeAddress.clear();
        txtEmployeeContact.clear();
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        tblEmployees.getSelectionModel().clearSelection();
    }














}

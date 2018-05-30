package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Department;
import model.DepartmentDAO;

import java.sql.SQLException;

public class DepartmentsController {
    private static final int NO_ID = -1;
    private int selectedDepartmentId = NO_ID;

    @FXML
    private TableView departmentTable;

    @FXML
    private TableColumn<Department, Integer> columnId;
    @FXML
    private TableColumn<Department, String> columnName;

    @FXML
    private Label labelSelectedDepartment;
    @FXML
    private TextField fieldDepartmentName;


    private void showAllDepartments() {
        try {
            departmentTable.setItems(DepartmentDAO.selectAll());
        } catch (SQLException e) {
            System.out.println("showAllDepartments ERROR: " + e.getSQLState());
            e.printStackTrace();
        }
        clearAllSelected();
    }

    @FXML
    private void initialize() {
        columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        columnId.setVisible(false);
        showAllDepartments();
    }

    public void selectDepartment(MouseEvent mouseEvent) {
        Department selected = (Department) departmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        selectedDepartmentId = selected.getId();

        labelSelectedDepartment.setText(selected.getName());
        fieldDepartmentName.setText(selected.getName());
    }

    private void clearAllSelected() {
        labelSelectedDepartment.setText("");
        fieldDepartmentName.clear();

        selectedDepartmentId = NO_ID;
    }
}

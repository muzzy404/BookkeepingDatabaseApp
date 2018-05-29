package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Department;
import model.DepartmentDAO;

import java.sql.SQLException;

public class DepartmentsController {

    @FXML
    private TableView departmentTable;

    @FXML
    private TableColumn<Department, Integer> columnId;
    @FXML
    private TableColumn<Department, String> columnName;


    private void showAllDepartments() {
        try {
            departmentTable.setItems(DepartmentDAO.selectAll());
        } catch (SQLException e) {
            System.out.println("showAllDepartments ERROR: " + e.getSQLState());
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        columnId.setVisible(false);
        showAllDepartments();
    }
}

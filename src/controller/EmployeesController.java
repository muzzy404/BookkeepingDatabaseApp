package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Department;
import model.Employee;
import model.EmployeeDAO;
import model.EmployeeDepartment;

import java.sql.SQLException;

public class EmployeesController {
    @FXML
    private TableView employeesTable;
    @FXML
    private TableView employeesInDepsTable;

    @FXML
    private TableColumn<Employee, Integer> columnIdEmp;
    @FXML
    private TableColumn<Employee, String> columnLastNameEmp;
    @FXML
    private TableColumn<Employee, String> columnFirstNameEmp;
    @FXML
    private TableColumn<Employee, String> columnPatronymicEmp;
    @FXML
    private TableColumn<Employee, String> columnPosition;
    @FXML
    private TableColumn<Employee, Double> columnSalary;

    @FXML
    private TableColumn<EmployeeDepartment, Integer> columnIdEmpDep;
    @FXML
    private TableColumn<EmployeeDepartment, String> columnLastNameEmpDep;
    @FXML
    private TableColumn<EmployeeDepartment, String> columnFirstNameEmpDep;
    @FXML
    private TableColumn<EmployeeDepartment, String> columnPatronymicEmpDep;
    @FXML
    private TableColumn<EmployeeDepartment, String> columnDepartment;

    private void showAllEmployees() {
        try {
            employeesTable.setItems(EmployeeDAO.selectAll());
        } catch (SQLException e) {
            System.out.println("showAllEmployees ERROR: " + e.getSQLState());
            e.printStackTrace();
        }

        showAllEmployeesWithDepartments();
        // TODO: clear selected! and update second table
    }

    private void showAllEmployeesWithDepartments() {
        try {
            employeesInDepsTable.setItems(EmployeeDAO.selectAllWithDepartments());
        } catch (SQLException e) {
            System.out.println("showAllEmployeesWithDepartments ERROR: " + e.getSQLState());
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        // first table
        columnIdEmp.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        columnLastNameEmp.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        columnFirstNameEmp.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        columnPatronymicEmp.setCellValueFactory(cellData -> cellData.getValue().patronymicProperty());
        columnPosition.setCellValueFactory(cellData -> cellData.getValue().positionProperty());

        columnSalary.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());
        columnSalary.setCellFactory(cellData -> new TableCell<Employee, Double>() {

            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText("");
                else
                    setText(String.format("%.2f", item));
            }
        });

        // second table
        columnIdEmpDep.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        columnLastNameEmpDep.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        columnFirstNameEmpDep.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        columnPatronymicEmpDep.setCellValueFactory(cellData -> cellData.getValue().patronymicProperty());
        columnDepartment.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());

        columnIdEmp.setVisible(false);
        columnIdEmpDep.setVisible(false);

        showAllEmployees();

        // TODO: deps combo box
    }
}

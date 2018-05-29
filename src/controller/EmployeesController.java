package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Department;
import model.Employee;
import model.EmployeeDAO;
import model.EmployeeDepartment;

import java.sql.SQLException;

public class EmployeesController {
    static private final int NO_ID = -1;
    static private final String NOT_SELECTED = "not selected";

    private int selectedEmpId    = NO_ID;
    private int selectedEmpDepId = NO_ID;

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

    @FXML
    private TextField fieldFirstName;
    @FXML
    private TextField fieldLastName;
    @FXML
    private TextField fieldPatronymic;
    @FXML
    private TextField fieldPosition;
    @FXML
    private TextField fieldSalary;

    @FXML
    private Label selectedFromEmployees;
    @FXML
    private Label selectedFromDepsEmp;

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

    public void employeeSelected(MouseEvent mouseEvent) {
        Employee selected = (Employee) employeesTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        selectedEmpId = selected.getId();
        selectedFromEmployees.setText(selected.getLastName()  + " " +
                                      selected.getFirstName() + " " +
                                      selected.getPatronymic());

        fieldFirstName.setText(selected.getFirstName());
        fieldLastName.setText(selected.getLastName());
        fieldPatronymic.setText(selected.getPatronymic());
        fieldPosition.setText(selected.getPosition());
        fieldSalary.setText(String.format("%.2f", selected.getSalary()));
    }

    public void employeeWithDepSelected(MouseEvent mouseEvent) {
        EmployeeDepartment selected = (EmployeeDepartment) employeesInDepsTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        selectedEmpDepId = selected.getId();
        selectedFromDepsEmp.setText(selected.getLastName()  + " " +
                                    selected.getFirstName() + " " +
                                    selected.getPatronymic());
    }

    private void clearSelectedEmployee() {
        selectedEmpId = NO_ID;

        fieldFirstName.clear();
        fieldLastName.clear();
        fieldPatronymic.clear();
        fieldPosition.clear();
        fieldSalary.clear();

        selectedFromEmployees.setText(NOT_SELECTED);
    }

    private void clearSelectedEmpDep() {
        selectedEmpDepId = NO_ID;
        selectedFromDepsEmp.setText(NOT_SELECTED);
    }
}

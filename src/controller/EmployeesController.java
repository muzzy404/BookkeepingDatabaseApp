package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.*;
import util.AppUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeesController {
    private ArrayList<Integer> departmentsIds;

    static private final int NO_ID = -1;
    static private final String NOT_SELECTED = "not selected";

    private int selectedEmpId        = NO_ID;
    private int selectedRecordEmpDep = NO_ID;

    @FXML
    private ComboBox comboBoxDepartments;

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
    private Label selectedRecordDepsEmp;

    private void showAllEmployees() {
        try {
            employeesTable.setItems(EmployeeDAO.selectAll());
        } catch (SQLException e) {
            System.out.println("showAllEmployees ERROR: " + e.getSQLState());
            e.printStackTrace();
        }

        showAllEmployeesWithDepartments();

        clearSelectedEmployee();
        clearSelectedEmpDep();
    }

    private void showAllEmployeesWithDepartments() {
        try {
            employeesInDepsTable.setItems(EmployeeDAO.selectAllWithDepartments());
        } catch (SQLException e) {
            System.out.println("showAllEmployeesWithDepartments ERROR: " + e.getSQLState());
            e.printStackTrace();
        }
    }

    public void addNewEmployee(ActionEvent actionEvent) {
        addEmployeeData(true);
    }

    public void updateEmployee(ActionEvent actionEvent) {
        addEmployeeData(false);
    }

    private void addEmployeeData(boolean newRecord) {
        try {
            String lastName = fieldLastName.getText();
            String firstName = fieldFirstName.getText();
            String patronymic = fieldPatronymic.getText();
            String position = fieldPosition.getText();
            String salary = String.valueOf(Double.valueOf(fieldSalary.getText()));

            if (lastName.length()   == 0 ||
                firstName.length()  == 0 ||
                patronymic.length() == 0 ||
                position.length()   == 0) throw new Exception("All fields must contain data.");

            if (newRecord)
                EmployeeDAO.insertEmployee(lastName, firstName, patronymic, position, salary);
            else
                EmployeeDAO.updateEmployee(selectedEmpId, lastName, firstName, patronymic, position, salary);
        } catch (Exception e) {
            AppUtil.showAlert(Alert.AlertType.WARNING, "Warning", "Failed", e.getMessage());
        }

        showAllEmployees();
    }

    public void deleteEmployee(ActionEvent actionEvent) {
        try {
            if (selectedEmpId == NO_ID) throw new Exception("Please, select employee to delete.");
            EmployeeDAO.deleteEmployee(selectedEmpId);
            showAllEmployees();
        } catch (Exception e) {
            AppUtil.showAlert(Alert.AlertType.WARNING, "Warning", "Delete failed", e.getMessage());
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

        departmentsIds = new ArrayList<>();
        departmentsIds = AppUtil.updateDepartmentsComboBox(comboBoxDepartments);
        System.out.println(departmentsIds.size());
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

    public void empDepRecordSelected(MouseEvent mouseEvent) {
        EmployeeDepartment selected = (EmployeeDepartment) employeesInDepsTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        selectedRecordEmpDep = selected.getId();
        selectedRecordDepsEmp.setText(selected.getLastName()  + " " +
                                    selected.getFirstName() + " " +
                                    selected.getPatronymic());
        System.out.println(selectedRecordEmpDep);
    }

    public void addEmployeeToDepartment(ActionEvent actionEvent) {
        try {
            if (selectedEmpId == NO_ID) throw new Exception("Select employee to add him in department.");
            int departmentId = departmentsIds.get(comboBoxDepartments.getSelectionModel().getSelectedIndex());

            EmployeeDAO.addEmployeeToDepartment(selectedEmpId, departmentId);
            showAllEmployeesWithDepartments();
        } catch (Exception e) {
            AppUtil.showAlert(Alert.AlertType.WARNING,
                    "Waning", "Cannot add an employee to department", e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteEmployeeFromDepartment(ActionEvent actionEvent) {
        try {
            if (selectedRecordEmpDep == NO_ID) throw new Exception("Select employee to remove him from department.");

            EmployeeDAO.deleteEmployeeFromDepartment(selectedRecordEmpDep);
            showAllEmployeesWithDepartments();
        } catch (Exception e) {
            AppUtil.showAlert(Alert.AlertType.WARNING,
                    "Waning", "Cannot remove an employee from department", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void clearSelectedEmployee() {
        selectedEmpId = NO_ID;

        fieldFirstName.clear();
        fieldLastName.clear();
        fieldPatronymic.clear();
        fieldPosition.clear();
        fieldSalary.clear();

        selectedFromEmployees.setText(NOT_SELECTED);
    }

    @FXML
    private void clearSelectedEmpDep() {
        selectedRecordEmpDep = NO_ID;
        selectedRecordDepsEmp.setText(NOT_SELECTED);
    }

}

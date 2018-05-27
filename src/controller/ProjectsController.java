package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Department;
import model.DepartmentDAO;
import model.Project;
import model.ProjectDAO;
import util.DBUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ProjectsController {
    private static final String DATE_FORMAT = "dd-MMM-YY";
    private DateTimeFormatter formatter;

    private ArrayList<Integer> departmentsIds;

    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldCost;
    @FXML
    private ComboBox comboBoxDepartments;
    @FXML
    private DatePicker datePickerBeginDate;
    @FXML
    private DatePicker datePickerEndDate;

    @FXML
    private TableView projectsTable;

    @FXML
    private TableColumn<Project, Integer> idColumn;
    @FXML
    private TableColumn<Project, String> nameColumn;
    @FXML
    private TableColumn<Project, Double> costColumn;
    @FXML
    private TableColumn<Project, String> departmentColumn;
    @FXML
    private TableColumn<Project, Date> begDateColumn;
    @FXML
    private TableColumn<Project, Date> endDateColumn;
    @FXML
    private TableColumn<Project, Date> endDateRealColumn;

    @FXML
    private void showAllProjects(ActionEvent actionEvent) {
        try {
            ObservableList<Project> projectsList = ProjectDAO.selectAll();
            projectsTable.setItems(projectsList);
        } catch (SQLException e) {
            System.out.println("showAllProjects ERROR: " + e.getSQLState());
            e.printStackTrace();
        }
    }

    @FXML
    public void addProject(ActionEvent actionEvent) {
        try {
            String name = fieldName.getText();
            if (name.length() == 0) throw new Exception("Null project name");

            String cost = String.valueOf(Double.valueOf(fieldCost.getText()));
            int department = comboBoxDepartments.getSelectionModel().getSelectedIndex();
            String dateBegin = datePickerBeginDate.getValue().format(formatter);
            String dateEnd = datePickerEndDate.getValue().format(formatter);

            ProjectDAO.insertProject(name, cost, department, dateBegin, dateEnd);
            showAllProjects(null);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Insert Error");
            alert.setContentText(e.getSQLState());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("All fields must be correctly filled in contain data");
            alert.setContentText("Please, fill all data fields correctly to add a new project to Projects table.");
            alert.show();
        }
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        costColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        begDateColumn.setCellValueFactory(cellData -> cellData.getValue().date_begProperty());
        endDateColumn.setCellValueFactory(cellData -> cellData.getValue().date_endProperty());
        endDateRealColumn.setCellValueFactory(cellData -> cellData.getValue().date_end_realProperty());

        idColumn.setVisible(false);
        showAllProjects(null);

        updateDepartmentsComboBox();

        formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    }

    private void updateDepartmentsComboBox() {
        try {
            ObservableList<Department> departments = DepartmentDAO.selectAll();
            departmentsIds = new ArrayList<>();

            for(Department department : departments) {
                comboBoxDepartments.getItems().add(department.getName());
                departmentsIds.add(department.getId());
            }
        } catch (SQLException e) {
            System.out.println("Departments update ERROR " + e.getSQLState());
            e.printStackTrace();
        }
    }
}

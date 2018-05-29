package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Department;
import model.DepartmentDAO;
import model.Project;
import model.ProjectDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ProjectsController {
    private static final int NO_ID = -1;
    private static final String DATE_FORMAT = "dd-MMM-YY";
    private DateTimeFormatter formatter;

    private int selectedId = NO_ID;

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
    private TextField fieldNewCost;
    @FXML
    private DatePicker datePickerEndDateReal;
    @FXML
    private Label textSelected;

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

    public void addProject(ActionEvent actionEvent) {
        try {
            String name = fieldName.getText();
            if (name.length() == 0) throw new Exception("Null project name");

            String cost = String.valueOf(Double.valueOf(fieldCost.getText()));
            int department = departmentsIds.get(comboBoxDepartments.getSelectionModel().getSelectedIndex());
            String dateBegin = datePickerBeginDate.getValue().format(formatter);
            String dateEnd = datePickerEndDate.getValue().format(formatter);

            ProjectDAO.insertProject(name, cost, department, dateBegin, dateEnd);
            showAllProjects(null);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Insert Error", e.getSQLState());
        } catch (Exception e) {
            showAlert(Alert.AlertType.WARNING, "Warning",
                    "All fields must be correctly filled and contain data",
                    "Please, fill all data fields correctly to add a new project to Projects table.");
            return;
        }

        clearAddNewFields();
        clearSelected();
    }

    public void updateSelectedProject(ActionEvent actionEvent) {
        if (selectedId == NO_ID) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Update failed",
                    "Please, select project for updating.");
        }


    }

    @FXML
    private void initialize() {
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

    public void onRowSelected(MouseEvent mouseEvent) {
        Project selected = (Project) projectsTable.getSelectionModel().getSelectedItem();

        selectedId = selected.getId();
        textSelected.setText(selected.getName());
        fieldNewCost.setText(String.valueOf(selected.getCost()));
        datePickerEndDateReal.setValue(selected.getDate_end().toLocalDate());
    }

    private void clearAddNewFields() {
        fieldName.clear();
        fieldCost.clear();
    }

    private void clearSelected() {
        fieldNewCost.setText("");
        textSelected.setText("");
        selectedId = NO_ID;
    }

    private void showAlert(Alert.AlertType type,
                           String title,
                           String header,
                           String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}

package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Project;
import model.ProjectDAO;
import util.AppUtil;

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

        clearSelected();
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
            AppUtil.showAlert(Alert.AlertType.ERROR, "Error", "Insert Error", e.getSQLState());
        } catch (Exception e) {
            AppUtil.showAlert(Alert.AlertType.WARNING, "Warning",
                    "All fields must be correctly filled and contain data",
                    "Please, fill all data fields correctly to add a new project to Projects table.");
            return;
        }

        clearAddNewFields();
    }

    public void updateSelectedProject(ActionEvent actionEvent) {
        try {
            if (selectedId == NO_ID) throw new Exception("Please, select project to update.");

            String cost = String.valueOf(Double.valueOf(fieldNewCost.getText()));
            String date = datePickerEndDateReal.getValue().format(formatter);

            ProjectDAO.updateProject(selectedId, cost, date);
            showAllProjects(null);
        } catch (Exception e) {
            AppUtil.showAlert(Alert.AlertType.WARNING, "Warning", "Update failed", e.getMessage());
        }
    }

    public void deleteSelectedProject(ActionEvent actionEvent) {
        try {
            if (selectedId == NO_ID) throw new Exception("Please, select project to delete.");
            ProjectDAO.deleteProject(selectedId);
            showAllProjects(null);
        } catch (Exception e) {
            AppUtil.showAlert(Alert.AlertType.WARNING, "Warning", "Delete failed", e.getMessage());
        }
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        begDateColumn.setCellValueFactory(cellData -> cellData.getValue().date_begProperty());
        endDateColumn.setCellValueFactory(cellData -> cellData.getValue().date_endProperty());
        endDateRealColumn.setCellValueFactory(cellData -> cellData.getValue().date_end_realProperty());

        costColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());
        costColumn.setCellFactory(cellData -> new TableCell<Project, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText("");
                else
                    setText(String.format("%.2f", item.doubleValue()));
            }
        });

        idColumn.setVisible(false);
        showAllProjects(null);

        departmentsIds = AppUtil.updateDepartmentsComboBox(comboBoxDepartments);

        formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    }

    public void onRowSelected(MouseEvent mouseEvent) {
        Project selected = (Project) projectsTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        selectedId = selected.getId();
        textSelected.setText(selected.getName());
        fieldNewCost.setText(String.format("%.2f", selected.getCost()));
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
}

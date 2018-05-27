package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Project;
import model.ProjectDAO;

import java.sql.Date;
import java.sql.SQLException;

public class ProjectsController {

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
            System.out.println(projectsList.size());
            projectsTable.setItems(projectsList);
        } catch (SQLException e) {
            System.out.println("showAllProjects ERROR: " + e.getSQLState());
            e.printStackTrace();
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
    }
}

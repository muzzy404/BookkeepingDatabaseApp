package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectDAO {
    public static ObservableList<Project> selectAll() throws SQLException {
        String select = "SELECT * FROM BOOKKEEPING.PROJECTS";

        ResultSet resultSet = DBUtil.dbExecuteSelect(select);
        return getProjectsList(resultSet);
    }

    private static ObservableList<Project> getProjectsList(ResultSet set) throws SQLException {
        ObservableList<Project> list = FXCollections.observableArrayList();

        while(set.next()) {
            Project project = new Project(
                    set.getInt(Project.ID),
                    set.getString(Project.NAME),
                    set.getDouble(Project.COST),
                    set.getInt(Project.DEPARTMENT_ID),
                    set.getDate(Project.DATE_BEG),
                    set.getDate(Project.DATE_END),
                    set.getDate(Project.DATE_END_REAL));
            list.add(project);
        }

        return list;
    }
}

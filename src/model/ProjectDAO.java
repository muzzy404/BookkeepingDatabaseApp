package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectDAO {
    protected static final  String PROJECTS_T = "BOOKKEEPING.PROJECTS";
    private static final String DEPARTMENTS_T = DepartmentDAO.DEPARTMENTS_T;

    public static ObservableList<Project> selectAll() throws SQLException {

        StringBuilder select = new StringBuilder("SELECT ")
                .append(PROJECTS_T + "." + Project.ID   + ", ")
                .append(PROJECTS_T + "." + Project.NAME + ", ")
                .append(PROJECTS_T + "." + Project.COST + ", ")
                .append(DEPARTMENTS_T + "." + Department.NAME + " AS " + Project.DEPARTMENT + ", ")
                .append(PROJECTS_T + "." + Project.DATE_BEG + ", ")
                .append(PROJECTS_T + "." + Project.DATE_END + ", ")
                .append(PROJECTS_T + "." + Project.DATE_END_REAL)
                .append(" FROM " + PROJECTS_T + ", " + DEPARTMENTS_T)
                .append(" WHERE " + PROJECTS_T + "." + Project.DEPARTMENT_ID + " = " +
                        DEPARTMENTS_T + "." + Department.ID);

        ResultSet resultSet = DBUtil.dbExecuteSelect(select.toString());
        return getProjectsList(resultSet);
    }

    public static void insertProject(String name, String cost, int dep, String beginDate, String endDate) throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO " + PROJECTS_T + " (")
                .append(Project.NAME + ", ")
                .append(Project.COST + ", ")
                .append(Project.DEPARTMENT_ID + ", ")
                .append(Project.DATE_BEG + ", ")
                .append(Project.DATE_END + ") VALUES (")
                // values
                .append("'" + name + "', ")
                .append(cost + ", ")
                .append(String.valueOf(dep) + ", ")
                .append("'" + beginDate + "', ")
                .append("'" + endDate + "')");
        System.out.println(query.toString());

        DBUtil.dbExecuteUpdate(query.toString());
    }

    private static ObservableList<Project> getProjectsList(ResultSet set) throws SQLException {
        ObservableList<Project> list = FXCollections.observableArrayList();

        while(set.next()) {
            Project project = new Project(
                    set.getInt(Project.ID),
                    set.getString(Project.NAME),
                    set.getDouble(Project.COST),
                    set.getString(Project.DEPARTMENT),
                    set.getDate(Project.DATE_BEG),
                    set.getDate(Project.DATE_END),
                    set.getDate(Project.DATE_END_REAL));
            list.add(project);
        }
        return list;
    }
}

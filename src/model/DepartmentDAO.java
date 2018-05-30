package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDAO {
    protected static final String DEPARTMENTS_T = "BOOKKEEPING.DEPARTMENTS";

    private static final String selectAll = "SELECT * FROM " + DEPARTMENTS_T;

    public static ObservableList<Department> selectAll() throws SQLException {
        ResultSet resultSet = DBUtil.dbExecuteSelect(selectAll);
        return getDepartmentsList(resultSet);
    }

    public static void addNewDepartment(String name) throws SQLException {
        String query = new String("INSERT INTO " + DEPARTMENTS_T + " (" +
                Department.NAME + ") VALUES (" + "'" + name + "')");
        //System.out.println(query);

        DBUtil.dbExecuteUpdate(query);
    }

    public static void updateDepartment(int id, String newName) throws SQLException {
        String query = new String("UPDATE " + DEPARTMENTS_T + " SET " +
                Department.NAME + " = '" + newName + "' WHERE " +
                Department.ID + " = " + String.valueOf(id));
        //System.out.println(query);

        DBUtil.dbExecuteUpdate(query);
    }

    public static void deleteDepartment(int id) throws SQLException {
        String query = new String("DELETE FROM " + DEPARTMENTS_T +
                " WHERE " + Department.ID + " = " + String.valueOf(id));
        //System.out.println(query);

        DBUtil.dbExecuteUpdate(query);
    }

    private static ObservableList<Department> getDepartmentsList(ResultSet set) throws SQLException {
        ObservableList<Department> list = FXCollections.observableArrayList();

        while(set.next()) {
            Department department = new Department(
                    set.getInt(Department.ID),
                    set.getString(Department.NAME));
            list.add(department);
        }

        return list;
    }
}

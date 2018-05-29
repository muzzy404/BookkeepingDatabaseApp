package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO {
    protected static final String EMPLOYEES_T = "BOOKKEEPING.EMPLOYEES";
    protected static final String DEPARTMENTS_EMPLOYEES_T = "BOOKKEEPING.DEPARTMENTS_EMPLOYEES";
    private static final String DEPARTMENTS_T = DepartmentDAO.DEPARTMENTS_T;

    public static ObservableList<Employee> selectAll() throws SQLException {
        ResultSet resultSet = DBUtil.dbExecuteSelect("SELECT * FROM " + EMPLOYEES_T);
        return getEmployeesList(resultSet);
    }

    public static ObservableList<EmployeeDepartment> selectAllWithDepartments() throws SQLException {
        String empField = EMPLOYEES_T + ".";
        String depField = DEPARTMENTS_T + ".";

        StringBuilder query = new StringBuilder("SELECT ")
                .append(empField + Employee.ID + ", ")
                .append(empField + Employee.LAST_NAME + ", ")
                .append(empField + Employee.FIRST_NAME + ", ")
                .append(empField + Employee.PATRONYMIC + ", ")
                .append(depField + Department.NAME + "\n")
                .append("FROM " + DEPARTMENTS_EMPLOYEES_T + "\n")
                .append("JOIN " + DEPARTMENTS_T + " ON " +
                        EmployeeDepartment.DEPARTMENT_ID + " = " + depField + Department.ID + "\n")
                .append("JOIN " + EMPLOYEES_T + " ON " +
                        EmployeeDepartment.EMPLOYEE_ID + " = " + empField + Employee.ID);
        System.out.println(query.toString());

        return getEmployeeDepartmentsList(DBUtil.dbExecuteSelect(query.toString()));
    }

    public static void insertEmployee(String lastName, String firstName, String patronymic,
                                      String position, String salary) throws SQLException {
        String query = new String("INSERT INTO " + EMPLOYEES_T + " (" +
                Employee.LAST_NAME  + ", "     +
                Employee.FIRST_NAME + ", "     +
                Employee.PATRONYMIC + ", "     +
                Employee.POSITION   + ", "     +
                Employee.SALARY + ") VALUES (" +
                // values
                "'" + lastName   + "', " +
                "'" + firstName  + "', " +
                "'" + patronymic + "', " +
                "'" + position   + "', " +
                salary + ")");
        System.out.println(query);

        DBUtil.dbExecuteUpdate(query);
    }

    public static void updateEmployee(int id,
                                      String lastName, String firstName, String patronymic,
                                      String position, String salary) throws SQLException {
        String query = new String("UPDATE " + EMPLOYEES_T + " SET " +
                Employee.LAST_NAME  + " = '" + lastName   + "', " +
                Employee.FIRST_NAME + " = '" + firstName  + "', " +
                Employee.PATRONYMIC + " = '" + patronymic + "', " +
                Employee.POSITION   + " = '" + position   + "', " +
                Employee.SALARY     +  " = " + salary     +  " WHERE " +
                Employee.ID + " = " + id);
        System.out.println(query);

        DBUtil.dbExecuteUpdate(query);
    }

    public static void deleteEmployee(int id) throws SQLException {
        String query = new String("DELETE FROM " + EMPLOYEES_T +
                                  " WHERE " + Employee.ID + " = " + String.valueOf(id));
        System.out.println(query);

        DBUtil.dbExecuteUpdate(query);
    }

    private static ObservableList<Employee> getEmployeesList(ResultSet set) throws SQLException {
        ObservableList<Employee> list = FXCollections.observableArrayList();

        while(set.next()) {
            Employee employee = new Employee(
                    set.getInt(Employee.ID),
                    set.getString(Employee.LAST_NAME),
                    set.getString(Employee.FIRST_NAME),
                    set.getString(Employee.PATRONYMIC),
                    set.getString(Employee.POSITION),
                    set.getDouble(Employee.SALARY));
            list.add(employee);
        }
        return list;
    }

    private static ObservableList<EmployeeDepartment> getEmployeeDepartmentsList(ResultSet set) throws SQLException {
        ObservableList<EmployeeDepartment> list = FXCollections.observableArrayList();

        while(set.next()) {
            EmployeeDepartment employeeDepartment = new EmployeeDepartment(
                    set.getInt(Employee.ID),
                    set.getString(Employee.LAST_NAME),
                    set.getString(Employee.FIRST_NAME),
                    set.getString(Employee.LAST_NAME),
                    set.getString(Department.NAME));
            list.add(employeeDepartment);
        }
        return list;
    }
}

package util;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import model.Department;
import model.DepartmentDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class AppUtil {
    static public void showAlert(Alert.AlertType type,
                           String title,
                           String header,
                           String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    public static ArrayList<Integer> updateDepartmentsComboBox(ComboBox comboBoxDepartments) {
        System.out.println("UPDATE!!!");
        ArrayList<Integer> departmentsIds = new ArrayList<>();

        try {
            ObservableList<Department> departments = DepartmentDAO.selectAll();

            for(Department department : departments) {
                comboBoxDepartments.getItems().add(department.getName());
                departmentsIds.add(department.getId());

                System.out.println(departmentsIds.size());
            }
            if (departments.size() > 0) comboBoxDepartments.getSelectionModel().select(0);
        } catch (SQLException e) {
            System.out.println("Departments update ERROR " + e.getSQLState());
            e.printStackTrace();
        }

        return departmentsIds;
    }
}

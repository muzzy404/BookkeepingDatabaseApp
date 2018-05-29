package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeDepartment {
    protected static final String EMPLOYEE_ID = "EMPLOYEE_ID";
    protected static final String DEPARTMENT_ID = "DEPARTMENT_ID";

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty patronymic = new SimpleStringProperty();
    private StringProperty department = new SimpleStringProperty();

    public EmployeeDepartment(int id,
                              String lastName, String firstName, String patronymic,
                              String department) {
        this.id.set(id);
        this.lastName.set(lastName);
        this.firstName.set(firstName);
        this.patronymic.set(patronymic);
        this.department.set(department);
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public String getLastName() { return lastName.get(); }
    public StringProperty lastNameProperty() { return lastName; }

    public String getFirstName() { return firstName.get(); }
    public StringProperty firstNameProperty()  { return firstName; }

    public String getPatronymic() { return patronymic.get(); }
    public StringProperty patronymicProperty() { return patronymic; }

    public String getDepartment() { return department.get(); }
    public StringProperty departmentProperty() { return department; }
}

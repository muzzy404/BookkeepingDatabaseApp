package model;

import javafx.beans.property.*;

public class Employee {
    protected static final String ID = "ID";
    protected static final String LAST_NAME = "LAST_NAME";
    protected static final String FIRST_NAME = "FIRST_NAME";
    protected static final String PATRONYMIC = "PATHER_NAME";
    protected static final String POSITION = "POSITION";
    protected static final String SALARY = "SALARY";

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty patronymic = new SimpleStringProperty();
    private StringProperty position = new SimpleStringProperty();
    private DoubleProperty salary = new SimpleDoubleProperty();

    public Employee() {}

    public Employee(int id, String lastName, String firstName, String patronymic,
                    String position, double salary) {
        this.id.set(id);
        this.lastName.set(lastName);
        this.firstName.set(firstName);
        this.patronymic.set(patronymic);
        this.position.set(position);
        this.salary.set(salary);
    }

    public void setId(int id) { this.id.set(id); }
    public int  getId()       { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public void setLastName(String lastName) { this.lastName.set(lastName); }
    public String getLastName()              { return lastName.get();       }
    public StringProperty lastNameProperty() { return lastName; }

    public void setFirstName(String firstName) { this.firstName.set(firstName); }
    public String getFirstName()               { return firstName.get();        }
    public StringProperty firstNameProperty()  { return firstName; }

    public void setPatronymic(String patronymic) { this.patronymic.set(patronymic); }
    public String getPatronymic()                { return patronymic.get();         }
    public StringProperty patronymicProperty()   { return patronymic; }

    public void setPosition(String position) { this.position.set(position); }
    public String getPosition()              { return position.get();       }
    public StringProperty positionProperty() { return position; }

    public void setSalary(double salary)   { this.salary.set(salary); }
    public double getSalary()              { return salary.get();     }
    public DoubleProperty salaryProperty() { return salary; }
}

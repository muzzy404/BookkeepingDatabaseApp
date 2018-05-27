package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Department {
    protected static String ID = "ID";
    protected static String NAME = "NAME";

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();

    public Department() {}

    public Department(int id, String name) {
        this.id.set(id);
        this.name.set(name);
    }

    public void setId(int id) { this.id.set(id); }
    public int  getId()       { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public void setName(String name) { this.name.set(name); }
    public String getName()          { return name.get();   }
    public StringProperty nameProperty() { return name; }
}

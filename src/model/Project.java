package model;

import javafx.beans.property.*;

import java.sql.Date;

public class Project {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private DoubleProperty cost = new SimpleDoubleProperty();
    private IntegerProperty department_id = new SimpleIntegerProperty();
    private SimpleObjectProperty<Date> date_beg = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Date> date_end = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Date> date_end_real = new SimpleObjectProperty<>();

    public Project() {}

    public void setId(int id) { this.id.set(id); }
    public int  getId()       { return id.get(); }

    public void setName(String name) { this.name.set(name); }
    public String getName()          { return name.get();   }

    public void setCost(double cost) { this.cost.set(cost); }
    public double getCost()          { return cost.get();   }

    public void setDepartment_id(int dep_id) { this.department_id.set(dep_id); }
    public int getDepartment_id()            { return department_id.get();     }

    public void setDate_beg(Date date) { this.date_beg.set(date); }
    public Date getDate_beg()          { return date_beg.get();   }

    public void setDate_end(Date date) { this.date_end.set(date); }
    public Date getDate_end()          { return date_end.get();   }

    public void setDate_end_real(Date date) { this.date_end_real.set(date); }
    public Date getDate_end_real()          { return date_end_real.get();   }
}

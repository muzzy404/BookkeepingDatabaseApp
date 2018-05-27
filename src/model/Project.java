package model;

import javafx.beans.property.*;

import java.sql.Date;

public class Project {
    protected static String ID = "ID";
    protected static String NAME = "NAME";
    protected static String COST = "COST";
    protected static String DEPARTMENT_ID = "DEPARTMENT_ID";
    protected static String DATE_BEG = "DATE_BEG";
    protected static String DATE_END = "DATE_END";
    protected static String DATE_END_REAL = "DATE_END_REAL";

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private DoubleProperty cost = new SimpleDoubleProperty();
    private IntegerProperty department_id = new SimpleIntegerProperty();
    private SimpleObjectProperty<Date> date_beg = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Date> date_end = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Date> date_end_real = new SimpleObjectProperty<>();

    public Project() {}

    public Project(int id, String name, double cost, int dep_id,
                   Date date_beg, Date date_end, Date date_end_real) {
        this.id.set(id);
        this.name.set(name);
        this.cost.set(cost);
        this.department_id.set(dep_id);
        this.date_beg.set(date_beg);
        this.date_end.set(date_end);
        this.date_end_real.set(date_end_real);
    }

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

package nl.whitehorses.sbcc.eventprocessor.model;

import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;

public class Milestone {

    private String name;
    private String state;
    private GregorianCalendar deadline;

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public GregorianCalendar getDeadline() {
        return deadline;
    }

    public void setDeadline(GregorianCalendar deadline) {
        this.deadline = deadline;
    }

}

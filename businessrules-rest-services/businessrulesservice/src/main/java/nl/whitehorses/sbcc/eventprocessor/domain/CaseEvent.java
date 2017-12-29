package nl.whitehorses.sbcc.eventprocessor.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class CaseEvent {

    private String type;
    private String name;
    private String value;

    @NotEmpty
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CaseEvent{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}

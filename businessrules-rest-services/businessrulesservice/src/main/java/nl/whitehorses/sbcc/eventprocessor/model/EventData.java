package nl.whitehorses.sbcc.eventprocessor.model;

import javax.validation.constraints.NotNull;

public class EventData {

    private String name;
    private Object payload;

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

}

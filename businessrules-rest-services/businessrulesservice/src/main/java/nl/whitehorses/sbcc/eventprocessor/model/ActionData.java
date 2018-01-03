package nl.whitehorses.sbcc.eventprocessor.model;

import javax.validation.constraints.NotNull;

public class ActionData {
    private String name;
    private String payload;

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

}

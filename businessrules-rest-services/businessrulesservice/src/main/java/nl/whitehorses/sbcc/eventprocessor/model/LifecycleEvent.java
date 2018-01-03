package nl.whitehorses.sbcc.eventprocessor.model;

import javax.validation.constraints.NotNull;

public class LifecycleEvent {

    private String lifecycleEvent;

    @NotNull
    public String getLifecycleEvent() {
        return lifecycleEvent;
    }

    public void setLifecycleEvent(String lifecycleEvent) {
        this.lifecycleEvent = lifecycleEvent;
    }

}

package nl.whitehorses.sbcc.eventprocessor.model;

import javax.validation.constraints.NotNull;

public class ActivityEvent {

    private String activityName;
    private String activityEvent;

    public ActivityEvent() {
    }

    public ActivityEvent(String activityName, String activityEvent) {
        this.activityName = activityName;
        this.activityEvent = activityEvent;
    }

    @NotNull
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @NotNull
    public String getActivityEvent() {
        return activityEvent;
    }

    public void setActivityEvent(String activityEvent) {
        this.activityEvent = activityEvent;
    }

}

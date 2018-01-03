package nl.whitehorses.sbcc.eventprocessor.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ActivityAction {
    private String activityName;
    private String action;
    private List<ActionData> actionDataList;

    public ActivityAction() {
    }

    public ActivityAction(String activityName, String action) {
        this.activityName = activityName;
        this.action = action;
    }

    @NotNull
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @NotNull
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<ActionData> getActionDataList() {
        return actionDataList;
    }

    public void setActionDataList(List<ActionData> actionDataList) {
        this.actionDataList = actionDataList;
    }
}

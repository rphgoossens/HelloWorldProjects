package nl.whitehorses.sbcc.eventprocessor.model;

import javax.validation.constraints.NotNull;

public class MilestoneAction {
    private String milestoneName;
    private String action;

    public MilestoneAction() {
    }

    public MilestoneAction(String milestoneName, String action) {
        this.milestoneName = milestoneName;
        this.action = action;
    }

    @NotNull
    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    @NotNull
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}

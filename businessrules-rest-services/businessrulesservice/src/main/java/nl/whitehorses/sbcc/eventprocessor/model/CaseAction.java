package nl.whitehorses.sbcc.eventprocessor.model;

public class CaseAction {

    private ActivityAction activityAction;
    private MilestoneAction milestoneAction;

    //TODO:date
    //private ActionDate actionDate;

    public CaseAction() {
    }

    public CaseAction(ActivityAction activityAction, MilestoneAction milestoneAction) {
        this.activityAction = activityAction;
        this.milestoneAction = milestoneAction;
    }

    public ActivityAction getActivityAction() {
        return activityAction;
    }

    public void setActivityAction(ActivityAction activityAction) {
        this.activityAction = activityAction;
    }


    public MilestoneAction getMilestoneAction() {
        return milestoneAction;
    }

    public void setMilestoneAction(MilestoneAction milestoneAction) {
        this.milestoneAction = milestoneAction;
    }

    @Override
    public String toString() {
        return "CaseAction{" +
                "activityAction=" + activityAction +
                ", milestoneAction=" + milestoneAction +
                '}';
    }

}

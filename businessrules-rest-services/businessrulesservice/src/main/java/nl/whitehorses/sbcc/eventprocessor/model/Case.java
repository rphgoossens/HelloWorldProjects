package nl.whitehorses.sbcc.eventprocessor.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Case {
    private CaseHeader caseHeader;
    private List<Milestone> milestones;

    public Case() {
    }

    public Case(CaseHeader caseHeader, List<Milestone> milestones) {
        this.caseHeader = caseHeader;
        this.milestones = milestones;
    }

    @NotNull
    public CaseHeader getCaseHeader() {
        return caseHeader;
    }

    public void setCaseHeader(CaseHeader caseHeader) {
        this.caseHeader = caseHeader;
    }

    @NotNull
    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    @Override
    public String toString() {
        return "Case{" +
                "caseHeader=" + caseHeader +
                ", milestones=" + milestones +
                '}';
    }
}

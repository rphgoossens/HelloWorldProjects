package nl.whitehorses.sbcc.eventprocessor.model;

import javax.validation.constraints.NotNull;

public class CaseHeader {

    private String name;
    private String version;
    private String state;
    private String outcome;
    private String phase;
    private String category;
    private String rulesVersion;

    public CaseHeader() {
    }

    public CaseHeader(String name, String version, String state, String rulesVersion) {
        this.name = name;
        this.version = version;
        this.state = state;
        this.rulesVersion = rulesVersion;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @NotNull
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NotNull
    public String getRulesVersion() {
        return rulesVersion;
    }

    public void setRulesVersion(String rulesVersion) {
        this.rulesVersion = rulesVersion;
    }
}

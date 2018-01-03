package nl.whitehorses.sbcc.eventprocessor.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CustomEvent {

    private String eventName;
    private List<EventData> eventDataList;

    @NotNull
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<EventData> getEventDataList() {
        return eventDataList;
    }

    public void setEventDataList(List<EventData> eventDataList) {
        this.eventDataList = eventDataList;
    }
    
}

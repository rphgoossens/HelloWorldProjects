package nl.whitehorses.sbcc.eventprocessor.impl;

import nl.whitehorses.sbcc.eventprocessor.api.EventProcessor;
import nl.whitehorses.sbcc.eventprocessor.domain.CaseAction;
import nl.whitehorses.sbcc.eventprocessor.domain.CaseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

@Validated
public class EventProcessorImpl implements EventProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EventProcessorImpl.class);

    @Override
    public CaseAction processEvent(CaseEvent caseEvent) {

        logger.debug("CaseEvent: " + caseEvent.toString());
        logger.debug("CaseEvent.type: " + caseEvent.getType());

        return new CaseAction("MILESTONE_ACTION", "msProcesinleidingIngediend", "REACH");
    }
}

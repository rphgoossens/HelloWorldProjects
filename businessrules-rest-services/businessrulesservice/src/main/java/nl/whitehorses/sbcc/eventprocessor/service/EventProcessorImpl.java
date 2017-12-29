package nl.whitehorses.sbcc.eventprocessor.service;

import nl.whitehorses.sbcc.eventprocessor.model.CaseAction;
import nl.whitehorses.sbcc.eventprocessor.model.CaseEvent;
import nl.whitehorses.sbcc.eventprocessor.rules.DroolsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

@Validated
public class EventProcessorImpl implements EventProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EventProcessorImpl.class);

    @Autowired
    private DroolsService droolsService;

    @Override
    public CaseAction processEvent(CaseEvent caseEvent) {

        logger.debug("CaseEvent: " + caseEvent.toString());
        logger.debug("CaseEvent.type: " + caseEvent.getType());

        //TODO: call rules service
        return droolsService.processEvent(caseEvent);
        //return new CaseAction("MILESTONE_ACTION", "msProcesinleidingIngediend", "REACH");
    }
}

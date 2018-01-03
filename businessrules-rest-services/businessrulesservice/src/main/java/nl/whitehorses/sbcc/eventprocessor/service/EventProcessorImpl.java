package nl.whitehorses.sbcc.eventprocessor.service;

import nl.whitehorses.sbcc.eventprocessor.model.Case;
import nl.whitehorses.sbcc.eventprocessor.model.CaseAction;
import nl.whitehorses.sbcc.eventprocessor.model.CaseEvent;
import nl.whitehorses.sbcc.eventprocessor.rules.DecisionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public class EventProcessorImpl implements EventProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EventProcessorImpl.class);

    @Autowired
    private DecisionService droolsService;

    @Override
    public List<CaseAction> processEvent(Case caseInstance, CaseEvent caseEvent, List<CaseAction> caseActions) {

        logger.debug("CaseEvent: " + caseEvent.toString());

        return droolsService.processEvent(caseInstance, caseEvent, caseActions);
    }
}

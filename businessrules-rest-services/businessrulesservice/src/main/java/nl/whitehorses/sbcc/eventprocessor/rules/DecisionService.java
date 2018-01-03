package nl.whitehorses.sbcc.eventprocessor.rules;

import nl.whitehorses.sbcc.eventprocessor.model.Case;
import nl.whitehorses.sbcc.eventprocessor.model.CaseAction;
import nl.whitehorses.sbcc.eventprocessor.model.CaseEvent;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DecisionService {

    private static final Logger logger = LoggerFactory.getLogger(DecisionService.class);

    @Autowired
    private KieContainer kContainer;

    public List<CaseAction> processEvent(Case caseInstance, CaseEvent caseEvent, List<CaseAction> caseActions) {
        CaseAction caseAction = new CaseAction();

        KieSession kieSession = kContainer.newKieSession();
        kieSession.setGlobal("caseAction", caseAction);
        // TODO: Put all facts in Decison Service
        kieSession.insert(caseEvent);
        kieSession.fireAllRules();
        kieSession.dispose();

        logger.debug("CaseAction: " + caseAction.toString());

        return Arrays.asList(caseAction);
    }
}

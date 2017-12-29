package nl.whitehorses.sbcc.eventprocessor.rules;

import nl.whitehorses.sbcc.eventprocessor.model.CaseAction;
import nl.whitehorses.sbcc.eventprocessor.model.CaseEvent;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroolsService {

    private static final Logger logger = LoggerFactory.getLogger(DroolsService.class);


    @Autowired
    private KieContainer kContainer;

    public CaseAction processEvent(CaseEvent caseEvent) {
//        return new CaseAction("MILESTONE_ACTION", "msProcesinleidingIngediend", "REACH");


        CaseAction caseAction = new CaseAction();

        KieSession kieSession = kContainer.newKieSession();
        kieSession.setGlobal("caseAction", caseAction);
        kieSession.insert(caseEvent);
        kieSession.fireAllRules();
        kieSession.dispose();

        logger.debug("CaseAction: " + caseAction.toString());


        return caseAction;
    }


}

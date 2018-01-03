package nl.whitehorses.sbcc.eventprocessor.service;

import nl.whitehorses.sbcc.eventprocessor.model.*;
import nl.whitehorses.sbcc.eventprocessor.rules.DecisionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventProcessorImplTest {

    @MockBean
    private DecisionService decisionService;

    @Autowired
    EventProcessorImpl eventProcessorImpl;

    @Test
    public void processEventValidActivityEvent() {

        Case caseInstance = new Case(new CaseHeader("HandelsvorderingCasus", "1.0", "ACTIVE", "DEFAULT"), Arrays.asList());
        CaseEvent caseEvent = new CaseEvent("MILESTONE_EVENT", null, null, null, new MilestoneEvent("msProcesinleidingIngediend", "REACHED"));
        List<CaseAction> caseActions = Arrays.asList();

        List<CaseAction> caseActionsExpected = Arrays.asList(new CaseAction(null, new MilestoneAction("msProcesinleidingIngediend", "REACHED")));

        given(this.decisionService.processEvent(caseInstance, caseEvent, caseActions)).willReturn(caseActionsExpected);
        List<CaseAction> caseActionsGot = eventProcessorImpl.processEvent(caseInstance, caseEvent, caseActions);
        assertThat(caseActionsGot.get(0), samePropertyValuesAs(caseActionsExpected.get(0)));

    }

}

package nl.whitehorses.sbcc.eventprocessor.service;

import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import nl.whitehorses.sbcc.eventprocessor.model.Case;
import nl.whitehorses.sbcc.eventprocessor.model.CaseAction;
import nl.whitehorses.sbcc.eventprocessor.model.CaseEvent;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public interface EventProcessor {

//    @JsonRpcErrors({
//            @JsonRpcError(exception=ConstraintViolationException.class,
//                    code=-5678, message="CaseEventNotValid")
//    })

    @JsonRpcMethod(value = "processEvent")
    List<CaseAction> processEvent(@JsonRpcParam(value = "case") @Valid Case caseInstance,
                                  @JsonRpcParam(value = "caseEvent") @Valid CaseEvent caseEvent,
                                  @JsonRpcParam(value = "caseActions") @Valid List<CaseAction> caseActions);
}

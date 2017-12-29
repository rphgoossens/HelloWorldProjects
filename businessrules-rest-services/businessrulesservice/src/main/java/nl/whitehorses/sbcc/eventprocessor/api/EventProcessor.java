package nl.whitehorses.sbcc.eventprocessor.api;

import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import nl.whitehorses.sbcc.eventprocessor.domain.CaseAction;
import nl.whitehorses.sbcc.eventprocessor.domain.CaseEvent;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@Service
public interface EventProcessor {

//    @JsonRpcErrors({
//            @JsonRpcError(exception=ConstraintViolationException.class,
//                    code=-5678, message="CaseEventNotValid")
//    })

    @JsonRpcMethod(value = "processEvent")
    CaseAction processEvent(@JsonRpcParam(value = "caseEvent") @Valid CaseEvent caseEvent);
}

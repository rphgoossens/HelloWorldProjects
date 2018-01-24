package nl.whitehorses.hellobeer.generated.api;

import nl.whitehorses.hellobeer.generated.model.Beer;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-21T19:48:57.257Z")

@Controller
public class HelloBeerApiController implements HelloBeerApi {

    private final HelloBeerApiDelegate delegate;

    @org.springframework.beans.factory.annotation.Autowired
    public HelloBeerApiController(HelloBeerApiDelegate delegate) {
        this.delegate = delegate;
    }
    public ResponseEntity<Beer> addToBeerRepositoryUsingPOST(@ApiParam(value = "beer" ,required=true )  @Valid @RequestBody Beer beer) {
        return delegate.addToBeerRepositoryUsingPOST(beer);
    }

    public ResponseEntity<List<Beer>> getAllBeersUsingGET(@ApiParam(value = "type") @Valid @RequestParam(value = "type", required = false) String type) {
        return delegate.getAllBeersUsingGET(type);
    }

}

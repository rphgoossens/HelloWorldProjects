package nl.whitehorses.hellobeer.generated.api;

import nl.whitehorses.hellobeer.generated.model.Beer;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * A delegate to be called by the {@link HelloBeerApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-21T19:48:57.257Z")

public interface HelloBeerApiDelegate {

    /**
     * @see HelloBeerApi#addToBeerRepositoryUsingPOST
     */
    ResponseEntity<Beer> addToBeerRepositoryUsingPOST(Beer beer);

    /**
     * @see HelloBeerApi#getAllBeersUsingGET
     */
    ResponseEntity<List<Beer>> getAllBeersUsingGET(String type);

}

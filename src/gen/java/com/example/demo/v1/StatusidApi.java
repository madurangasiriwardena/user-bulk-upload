/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (4.3.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.example.demo.v1;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@Validated
@Api(value = "status{id}", description = "the status{id} API")
public interface StatusidApi {

    default StatusidApiDelegate getDelegate() {
        return new StatusidApiDelegate() {};
    }

    /**
     * GET /status{id} : Get operation status
     *
     * @param id  (required)
     * @return Successful operation (status code 200)
     */
    @ApiOperation(value = "Get operation status", nickname = "getStatus", notes = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation") })
    @RequestMapping(value = "/status{id}",
        method = RequestMethod.GET)
    default ResponseEntity<Void> getStatus(@ApiParam(value = "",required=true) @PathVariable("id") String id) {
        return getDelegate().getStatus(id);
    }

}

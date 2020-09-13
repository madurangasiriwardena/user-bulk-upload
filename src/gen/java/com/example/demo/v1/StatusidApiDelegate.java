package com.example.demo.v1;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A delegate to be called by the {@link StatusidApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */

public interface StatusidApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /status{id} : Get operation status
     *
     * @param id  (required)
     * @return Successful operation (status code 200)
     * @see StatusidApi#getStatus
     */
    default ResponseEntity<Void> getStatus(String id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}

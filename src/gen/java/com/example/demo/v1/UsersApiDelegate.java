package com.example.demo.v1;

import org.springframework.core.io.Resource;
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
 * A delegate to be called by the {@link UsersApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */

public interface UsersApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /users : Upload users via CSV file
     *
     * @param file  (optional)
     * @return Successful operation (status code 200)
     * @see UsersApi#uploadUsers
     */
    default ResponseEntity<Void> uploadUsers(MultipartFile file) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}

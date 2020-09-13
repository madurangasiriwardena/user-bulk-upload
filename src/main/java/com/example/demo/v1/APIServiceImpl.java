package com.example.demo.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component()
public class APIServiceImpl implements UsersApiDelegate, StatusApiDelegate {

    @Override
    public ResponseEntity<String> getStatus(String id) {

        try {
            return new ResponseEntity<>(
                    convertMapToJson(StatusHolder.getStatus(id)),
                    HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {

        return Optional.empty();
    }

    @Override
    public ResponseEntity<String> uploadUsers(MultipartFile file) {

        UUID correlationId = UUID.randomUUID();

        try {
            Path tempFile = Files.createTempFile("", correlationId.toString());
            file.transferTo(tempFile.toFile());

            Executor executor = Executors.newSingleThreadExecutor();
            UserService task = new UserService(tempFile.toFile().getPath(), correlationId.toString());
            executor.execute(task);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                correlationId.toString(),
                HttpStatus.ACCEPTED);
    }

    private String convertMapToJson(Map<String, Integer> elements) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(elements);
    }
}

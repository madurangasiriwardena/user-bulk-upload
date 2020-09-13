package com.example.demo.v1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component()
public class APIServiceImpl implements UsersApiDelegate, StatusidApiDelegate {

    @Override
    public ResponseEntity<Void> getStatus(String id) {

        return ResponseEntity.status(200).build();
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {

        return Optional.empty();
    }

    @Override
    public ResponseEntity<Void> uploadUsers(MultipartFile file) {

        UUID correlationId = UUID.randomUUID();

        try {
            Path tempFile = Files.createTempFile("", correlationId.toString());
            file.transferTo(tempFile.toFile());

            Executor executor = Executors.newSingleThreadExecutor();
            UserService task = new UserService(tempFile.toFile().getPath());
            executor.execute(task);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                correlationId.toString(),
                HttpStatus.OK);
    }
}

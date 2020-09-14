package com.example.demo.v1;

import com.example.demo.v1.bean.BulkResponse;
import com.example.demo.v1.dao.StatusDao;
import com.example.demo.v1.task.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component()
public class APIServiceImpl implements UsersApiDelegate, StatusApiDelegate {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    public ResponseEntity<String> getStatus(String id) {

        try {
            return new ResponseEntity<>(
                    convertListToJson(StatusDao.getStatus(id)),
                    HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

            UserService user = ctx.getBean(UserService.class, tempFile.toFile().getPath(), correlationId.toString());
            taskExecutor.execute(user);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                correlationId.toString(),
                HttpStatus.ACCEPTED);
    }

    private String convertListToJson(List<BulkResponse> elements) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(elements);
    }
}

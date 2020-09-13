package com.example.demo.v1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserService implements Runnable {

    private final String filePath;
    private final String correlationId;

    public UserService(String filePath, String correlationId) {

        this.filePath = filePath;
        this.correlationId = correlationId;
    }

    @Override
    public void run() {

        Path path = Paths.get(filePath);

        try (Reader reader = new FileReader(path.toFile());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                int status = new UserManager().createUser(csvRecord);
                String username = csvRecord.get("USR_LOGIN");
                StatusHolder.addStatus(correlationId, username, status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

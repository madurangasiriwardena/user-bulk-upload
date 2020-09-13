package com.example.demo.v1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
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

        Path csvFilePath = Paths.get(filePath);

        File csvFile = csvFilePath.toFile();
        try (Reader reader = new FileReader(csvFile);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                BulkResponse status = new UserManager().createUser(csvRecord);
                StatusHolder.addStatus(correlationId, status);
            }

            csvFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

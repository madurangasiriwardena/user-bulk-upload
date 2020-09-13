package com.example.demo.v1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserService implements Runnable {

    private String filePath;

    public UserService(String filePath) {

        this.filePath = filePath;
    }

    @Override
    public void run() {
        Path path = Paths.get(filePath);

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                int status = new UserManager().createUser(csvRecord);
                System.out.println(" =========== Status =========== " +status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

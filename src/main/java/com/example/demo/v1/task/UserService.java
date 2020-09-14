package com.example.demo.v1.task;

import com.example.demo.v1.UserManager;
import com.example.demo.v1.bean.BulkResponse;
import com.example.demo.v1.dao.StatusDao;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Scope("prototype")
public class UserService implements Runnable {

    private final String filePath;
    private final String correlationId;

    @Autowired
    private ApplicationContext ctx;

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
                UserManager userManager = ctx.getBean(UserManager.class);
                BulkResponse status = userManager.createUser(csvRecord);
                StatusDao.addStatus(correlationId, status);
            }

            csvFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

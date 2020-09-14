package com.example.demo.v1;

import com.example.demo.v1.bean.BulkResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.demo.v1.Constants.EMAILS_ATTRIBUTE;
import static com.example.demo.v1.Constants.EMAIL_TYPE_WORK_ATTRIBUTE;
import static com.example.demo.v1.Constants.FAMILY_NAME_ATTRIBUTE;
import static com.example.demo.v1.Constants.GIVEN_NAME_ATTRIBUTE;
import static com.example.demo.v1.Constants.NAME_ATTRIBUTE;
import static com.example.demo.v1.Constants.PASSWORD_ATTRIBUTE;
import static com.example.demo.v1.Constants.SCHEMAS_ATTRIBUTE;
import static com.example.demo.v1.Constants.TYPE_PARAM;
import static com.example.demo.v1.Constants.USER_NAME_ATTRIBUTE;
import static com.example.demo.v1.Constants.VALUE_PARAM;

@Service
@Scope("prototype")
public class UserManager {

    private CloseableHttpClient client;

    @Value("${scim.endpoint}")
    private String endpoint;

    @Value("${scim.username}")
    private String adminUsername;

    @Value("${scim.password}")
    private String adminPassword;

    public UserManager() {

        client = HttpClients.createDefault();
    }

    public BulkResponse createUser(CSVRecord csvRecord) {

        try {
            JSONObject scimUser = buildSCIMUser(csvRecord);
            return sendRequest(scimUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject buildSCIMUser(CSVRecord csvRecord) {

        String lastName = csvRecord.get("USR_LAST_NAME");
        String firstName = csvRecord.get("USR_FIRST_NAME");
        String email = csvRecord.get("USR_EMAIL");
        String username = csvRecord.get("USR_LOGIN");

        JSONObject rootObject = new JSONObject();

        JSONArray schemas = new JSONArray();
        rootObject.put(SCHEMAS_ATTRIBUTE, schemas);

        JSONObject names = new JSONObject();
        names.put(FAMILY_NAME_ATTRIBUTE, lastName);
        names.put(GIVEN_NAME_ATTRIBUTE, firstName);

        rootObject.put(NAME_ATTRIBUTE, names);
        rootObject.put(USER_NAME_ATTRIBUTE, username);

        if (StringUtils.isNotBlank(email)) {
            JSONObject emailWork = new JSONObject();
            emailWork.put(TYPE_PARAM, EMAIL_TYPE_WORK_ATTRIBUTE);
            emailWork.put(VALUE_PARAM, email);

            JSONArray emails = new JSONArray();
            emails.add(emailWork);

            rootObject.put(EMAILS_ATTRIBUTE, emails);
        }

        rootObject.put(PASSWORD_ATTRIBUTE, "Random@123");

        return rootObject;
    }

    public BulkResponse sendRequest(JSONObject rootObject) throws IOException {

        HttpPost request = new HttpPost(endpoint);
        request.addHeader(HttpHeaders.AUTHORIZATION, getAuthzHeader());
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        StringEntity entity = new StringEntity(rootObject.toString());
        request.setEntity(entity);

        HttpResponse response = client.execute(request);
        BulkResponse scimResponse = new BulkResponse();

        if (!HttpStatus.valueOf(response.getStatusLine().getStatusCode()).is2xxSuccessful()) {
            scimResponse.setResponse(EntityUtils.toString(response.getEntity()));
        }
        EntityUtils.consumeQuietly(response.getEntity());

        scimResponse.setStatus(response.getStatusLine().getStatusCode());
        scimResponse.setMethod("POST");
        if (response.getStatusLine().getStatusCode() == 201) {
            scimResponse.setLocation(response.getHeaders("Location")[0].getValue());
        }
        return scimResponse;
    }

    private String getAuthzHeader() {

        return "Basic " + Base64.encodeBase64String((adminUsername + ":" + adminPassword).getBytes()).trim();
    }
}

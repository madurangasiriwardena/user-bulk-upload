package com.example.demo.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class BulkResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String location;

    private String method;
    private int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonRawValue
    private String response;

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public String getMethod() {

        return method;
    }

    public void setMethod(String method) {

        this.method = method;
    }

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {

        this.status = status;
    }

    public String getResponse() {

        return response;
    }

    public void setResponse(String response) {

        this.response = response;
    }
}

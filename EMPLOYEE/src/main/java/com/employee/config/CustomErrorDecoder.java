package com.employee.config;

import com.employee.exceptions.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        ObjectMapper  mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        try(InputStream inputStream = response.body().asInputStream()) {
            com.employee.response.Response resp = mapper.readValue(inputStream, com.employee.response.Response.class);
            return new CustomException(resp.getMessage(), HttpStatus.valueOf(resp.getStatus()));
        } catch (IOException e) {
            throw new CustomException("Internal Server Error");

        }

    }
}

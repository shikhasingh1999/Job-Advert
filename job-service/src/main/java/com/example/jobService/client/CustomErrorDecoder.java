package com.example.jobService.client;

import com.example.common.exception.GenericErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Exception decode(String s, Response response) {
        try (InputStream inputStream = response.body().asInputStream()) {
            Map<String, String> errors = objectMapper.readValue
                    (IOUtils.toString(inputStream, StandardCharsets.UTF_8), Map.class);
            return GenericErrorResponse.builder()
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .message(errors.get("error"))
                    .build();
        }
        catch (Exception e) {
            System.out.println(e);
            return GenericErrorResponse.builder()
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .message(e.getMessage())
                    .build();
        }
    }
}

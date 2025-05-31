package com.maddenmatt.noteocr.controller.advice;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GlobalExceptionHandlerTest {

    @TestConfiguration
    static class GlobalExceptionHandlerTestContextConfiguration {

        @Bean
        public GlobalExceptionHandler createGlobalExceptionHandler() {
            return new GlobalExceptionHandler();
        }

    }

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void whenParseTextFromImageIsSuccessful_thenParsedTextDtoListIsReturned() {
        HashMap<String,String> expectedResult = new HashMap<>();
        expectedResult.put("errorMessage", "java.io.FileNotFoundException: testImage.png");

        Map<String,String> result = globalExceptionHandler.handleException(new FileNotFoundException("testImage.png"));
        assertEquals(expectedResult, result, "Expected error message response does not match actual error message response!");
    }

}

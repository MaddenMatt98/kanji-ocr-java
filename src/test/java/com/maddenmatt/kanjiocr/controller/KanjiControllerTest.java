package com.maddenmatt.kanjiocr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import com.maddenmatt.kanjiocr.dto.ParsedTextDto;
import com.maddenmatt.kanjiocr.service.OCRService;
import com.maddenmatt.kanjiocr.util.UnitTestingUtil;

@ExtendWith(SpringExtension.class)
public class KanjiControllerTest {

    @TestConfiguration
    static class KanjiControllerTestContextConfiguration {

        @Bean
        public KanjiController createKanjiController() {
            return new KanjiController();
        }

    }

    @Autowired
    private KanjiController kanjiController;

    @MockitoBean
    private OCRService ocrService;

    @Test
    public void whenParseTextFromImageIsSuccessful_thenParsedTextDtoListIsReturned() {
        try {
            MultipartFile testMultipartFile = UnitTestingUtil.getMockMultipartFile();

            String testS3ObjectKey = "1417601730000-PXL_20250120_043330496.jpg";
            when(ocrService.uploadImagetoS3(testMultipartFile)).thenReturn(testS3ObjectKey);

            List<ParsedTextDto> expectedResult = Arrays.asList(new ParsedTextDto("日本", Float.parseFloat("99.9"), testS3ObjectKey));
            when(ocrService.getTextInImage(testS3ObjectKey)).thenReturn(expectedResult);

            List<ParsedTextDto> result = kanjiController.parseTextFromImage(testMultipartFile);

            assertEquals(expectedResult, result, "Expected parsed text DTO does not match actual DTO!");
            verify(ocrService, times(1)).uploadImagetoS3(testMultipartFile);
            verify(ocrService, times(1)).getTextInImage(testS3ObjectKey);
        } catch (IOException ioException) {
            fail("Unexpected exception thrown when loading test image %s!".formatted(ioException));
        }
    }

    @Test
    public void whenParseTextFromImageIsSuccessfulWithNoDetections_thenEmptyParsedTextDtoListIsReturned() {
        try {
            MultipartFile testMultipartFile = UnitTestingUtil.getMockMultipartFile();

            String testS3ObjectKey = "1417601730000-PXL_20250120_043330496.jpg";
            when(ocrService.uploadImagetoS3(testMultipartFile)).thenReturn(testS3ObjectKey);

            List<ParsedTextDto> expectedResult = new ArrayList<>();
            when(ocrService.getTextInImage(testS3ObjectKey)).thenReturn(expectedResult);

            List<ParsedTextDto> result = kanjiController.parseTextFromImage(testMultipartFile);

            assertEquals(expectedResult, result, "Expected parsed text DTO does not match actual DTO!");
            verify(ocrService, times(1)).uploadImagetoS3(testMultipartFile);
            verify(ocrService, times(1)).getTextInImage(testS3ObjectKey);
        } catch (IOException ioException) {
            fail("Unexpected exception thrown when loading test image %s!".formatted(ioException));
        }
    }

}

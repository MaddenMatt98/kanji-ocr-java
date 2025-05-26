package com.maddenmatt.kanjiocr.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import com.maddenmatt.kanjiocr.dto.ParsedTextDto;
import com.maddenmatt.kanjiocr.util.UnitTestingUtil;

import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectTextRequest;
import software.amazon.awssdk.services.rekognition.model.DetectTextResponse;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.TextDetection;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.CompletedFileUpload;
import software.amazon.awssdk.transfer.s3.model.FileUpload;
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest;

@ExtendWith(SpringExtension.class)
public class OCRServiceImplTest {

    @TestConfiguration
    static class OCRServiceImplTestContextConfiguration {

        @Bean
        public OCRService createOCRService() {
            return new OCRServiceImpl();
        }

    }

    @Autowired
    private OCRService ocrService;

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    @MockitoBean
    private S3TransferManager s3TransferManager;

    @MockitoBean
    private RekognitionClient rekogntitionClient;

    @Test
    public void whenFileUploadSuccessful_thenS3ObjectKeyShouldBeReturned() {
        try {
            MultipartFile testMultipartFile = UnitTestingUtil.getMockMultipartFile();

            FileUpload mockFileUpload = mock(FileUpload.class);
            when(mockFileUpload.completionFuture()).thenReturn(CompletableFuture.completedFuture(mock(CompletedFileUpload.class)));
            when(s3TransferManager.uploadFile(any(UploadFileRequest.class))).thenReturn(mockFileUpload);

            String s3ObjectKey = ocrService.uploadImagetoS3(testMultipartFile);

            assertTrue(s3ObjectKey.contains("PXL_20250120_043330496.jpg"), "Returned s3ObjectKey does not contain original file name!");
            verify(s3TransferManager, times(1)).uploadFile(any(UploadFileRequest.class));
            verify(mockFileUpload, times(1)).completionFuture();
        } catch (IOException ioException) {
            fail("Unexpected exception thrown when loading test image %s!".formatted(ioException));
        }
    }

    @Test
    public void whenRekognitionDetectionSuccessful_thenParsedTextDTOListShouldBeReturned() {
        String testS3ObjectKey = "1417601730000-PXL_20250120_043330496.jpg";

        Image expectedRequestImage = Image.builder()
                                          .s3Object(builder -> builder.bucket(s3BucketName).name(testS3ObjectKey))
                                          .build();
        DetectTextRequest detectTextRequest = DetectTextRequest.builder()
                                                               .image(expectedRequestImage)
                                                               .build();
        float testFloat = Float.parseFloat("99.9");
        DetectTextResponse testDetectTextResponse = DetectTextResponse.builder()
                                                                      .textDetections(TextDetection.builder().detectedText("日本").confidence(testFloat).build())
                                                                      .build();
        when(rekogntitionClient.detectText(detectTextRequest)).thenReturn(testDetectTextResponse);

        List<ParsedTextDto> result = ocrService.getTextInImage(testS3ObjectKey);

        assertTrue(result.size() == 1, "Expected one text detection, %s returned.".formatted(String.valueOf(result.size())));
        assertEquals("日本", result.get(0).getDetectedText(), "Expected detected text to be \"日本\", but %s was returned.".formatted(result.get(0).getDetectedText()));
        assertEquals(testFloat, result.get(0).getConfidence(), "Expected detected confidence to be %s, but %s was returned.".formatted(String.valueOf(testFloat), String.valueOf(result.get(0).getConfidence())));
        assertEquals(testS3ObjectKey, result.get(0).getS3ObjectKey(), "Expected S3 object key to be %s, but %s was returned.".formatted(testS3ObjectKey, result.get(0).getS3ObjectKey()));
        verify(rekogntitionClient, times(1)).detectText(detectTextRequest);
    }

    @Test
    public void whenRekognitionDetectionSuccessfulButNoDetectionsFound_thenEmptyParsedTextDTOListShouldBeReturned() {
        String testS3ObjectKey = "1417601730000-PXL_20250120_043330496.jpg";

        Image expectedRequestImage = Image.builder()
                                          .s3Object(builder -> builder.bucket(s3BucketName).name(testS3ObjectKey))
                                          .build();
        DetectTextRequest detectTextRequest = DetectTextRequest.builder()
                                                               .image(expectedRequestImage)
                                                               .build();
        DetectTextResponse testDetectTextResponse = DetectTextResponse.builder()
                                                                      .build();
        when(rekogntitionClient.detectText(detectTextRequest)).thenReturn(testDetectTextResponse);

        List<ParsedTextDto> result = ocrService.getTextInImage(testS3ObjectKey);

        assertTrue(result.isEmpty(), "Expected no text detections, %s returned.".formatted(String.valueOf(result.size())));
        verify(rekogntitionClient, times(1)).detectText(detectTextRequest);
    }

}

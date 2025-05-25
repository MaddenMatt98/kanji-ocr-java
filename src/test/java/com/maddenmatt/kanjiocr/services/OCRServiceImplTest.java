package com.maddenmatt.kanjiocr.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import com.maddenmatt.kanjiocr.service.OCRService;
import com.maddenmatt.kanjiocr.service.OCRServiceImpl;

import software.amazon.awssdk.services.rekognition.RekognitionClient;
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
            
            FileInputStream testImageStream = new FileInputStream("src/test/resources/PXL_20250120_043330496.jpg");
            MultipartFile multipartFile = new MockMultipartFile("PXL_20250120_043330496.jpg", "src/test/resources/PXL_20250120_043330496.jpg", "image/png", testImageStream.readAllBytes());

            FileUpload mockFileUpload = mock(FileUpload.class);
            when(mockFileUpload.completionFuture()).thenReturn(CompletableFuture.completedFuture(mock(CompletedFileUpload.class)));
            when(s3TransferManager.uploadFile(any(UploadFileRequest.class))).thenReturn(mockFileUpload);

            String s3ObjectKey = ocrService.uploadImagetoS3(multipartFile);

            assertTrue(s3ObjectKey.contains("PXL_20250120_043330496.jpg"), "Returned s3ObjectKey does not contain original file name!");
            verify(s3TransferManager, times(1)).uploadFile(any(UploadFileRequest.class));
            verify(mockFileUpload, times(1)).completionFuture();
        } catch (IOException ioException) {
            fail("Unexpected exception thrown when loading test image %s!".formatted(ioException));
        }
    }

}

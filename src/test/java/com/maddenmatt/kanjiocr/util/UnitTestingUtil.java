package com.maddenmatt.kanjiocr.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class UnitTestingUtil {

    public static MultipartFile getMockMultipartFile() throws IOException {
        FileInputStream testImageStream = new FileInputStream("src/test/resources/PXL_20250120_043330496.jpg");
        MultipartFile multipartFile = new MockMultipartFile("PXL_20250120_043330496.jpg", "src/test/resources/PXL_20250120_043330496.jpg", "image/png", testImageStream.readAllBytes());
        return multipartFile;
    }

}

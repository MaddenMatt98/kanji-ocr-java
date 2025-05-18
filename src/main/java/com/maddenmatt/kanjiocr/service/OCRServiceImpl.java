package com.maddenmatt.kanjiocr.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maddenmatt.kanjiocr.dto.ParsedTextDto;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectTextRequest;
import software.amazon.awssdk.services.rekognition.model.DetectTextResponse;
import software.amazon.awssdk.services.rekognition.model.Image;

@Service
public class OCRServiceImpl implements OCRService {

    @Autowired
    private RekognitionClient rekogntitionClient;

    @Override
    public List<ParsedTextDto> getTextInImage(MultipartFile inputImage) throws IOException {
        Image requestImage = Image.builder()
                                  .bytes(SdkBytes.fromByteArray(inputImage.getBytes()))
                                  .build();

        DetectTextRequest detectTextRequest = DetectTextRequest.builder()
                                                               .image(requestImage)
                                                               .build();

        DetectTextResponse detectTextResponse = rekogntitionClient.detectText(detectTextRequest);

        List<ParsedTextDto> textInImage = new ArrayList<>();
        detectTextResponse.textDetections()
                          .forEach(textDetection -> textInImage.add(new ParsedTextDto(textDetection)));

        return textInImage;
    }

}

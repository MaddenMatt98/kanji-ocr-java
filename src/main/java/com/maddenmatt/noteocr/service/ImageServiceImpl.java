package com.maddenmatt.noteocr.service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maddenmatt.noteocr.dto.ParsedTextDto;

import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectTextRequest;
import software.amazon.awssdk.services.rekognition.model.DetectTextResponse;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.FileUpload;
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${s3.bucket.name}")
    public String s3BucketName;

    @Autowired
    private S3TransferManager s3TransferManager;

    @Autowired
    private RekognitionClient rekogntitionClient;

    @Override
    public String uploadImagetoS3(MultipartFile image) throws IOException {
        /*
            Combine the current timestamp and original filename to generate the s3 key.
            This app is just for personal use, so this should be sufficient.  In a
            high volume app it would be advisable to include a random base 62 string to
            reduce chances of collision.
        */
        String s3ObjectKey = "%s-%s".formatted(Long.toString(Instant.now().toEpochMilli()), image.getOriginalFilename());

        File temporaryFile = File.createTempFile("temporary", s3ObjectKey);
        temporaryFile.deleteOnExit();
        image.transferTo(temporaryFile);

        UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
                                                               .putObjectRequest(builder -> builder.bucket(s3BucketName).key(s3ObjectKey))
                                                               .source(temporaryFile)
                                                               .build();

        FileUpload fileUpload = s3TransferManager.uploadFile(uploadFileRequest);

        //Wait for the response from the file upload.  We don't need anything from the returned object.
        fileUpload.completionFuture().join();

        /*
            Attempt to delete the file immediately to prevent accumulation on the container.
            If it fails that shouldn't be a big issue since it will be deleted on program
            termination since we call deleteOnExit() after creating the object.
        */
        temporaryFile.delete();

        return s3ObjectKey;
    }

    @Override
    public List<ParsedTextDto> getTextInImage(String s3ObjectKey) {
        Image requestImage = Image.builder()
                                  .s3Object(builder -> builder.bucket(s3BucketName).name(s3ObjectKey))
                                  .build();

        DetectTextRequest detectTextRequest = DetectTextRequest.builder()
                                                               .image(requestImage)
                                                               .build();

        DetectTextResponse detectTextResponse = rekogntitionClient.detectText(detectTextRequest);

        List<ParsedTextDto> textInImage = new ArrayList<>();
        detectTextResponse.textDetections()
                          .forEach(textDetection -> textInImage.add(new ParsedTextDto(textDetection, s3ObjectKey)));

        return textInImage;
    }

}

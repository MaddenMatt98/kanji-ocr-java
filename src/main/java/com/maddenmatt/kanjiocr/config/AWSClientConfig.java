package com.maddenmatt.kanjiocr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

@Configuration
public class AWSClientConfig {

    @Bean
    public RekognitionClient getRekognitionClient() {
        return RekognitionClient.builder().build();
    }

    @Bean
    public S3TransferManager getTransferManager() {
        S3AsyncClient s3AsyncClient = S3AsyncClient.crtBuilder()
                                                   .build();
        S3TransferManager transferManager = S3TransferManager.builder()
                                                             .s3Client(s3AsyncClient)
                                                             .build();
        return transferManager;
    }

}

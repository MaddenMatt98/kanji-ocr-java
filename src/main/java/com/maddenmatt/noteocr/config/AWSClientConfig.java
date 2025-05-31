package com.maddenmatt.noteocr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.maddenmatt.noteocr.dto.SavedNoteDto;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

@Configuration
public class AWSClientConfig {

    @Value("${dynamodb.table.name}")
    public String dynamoDbTableName;

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

    @Bean
    public DynamoDbTable<SavedNoteDto> getDynamoDbClient() {
        DynamoDbEnhancedClient dynamoDbEnhancedClient = DynamoDbEnhancedClient.create();
        return dynamoDbEnhancedClient.table(dynamoDbTableName, TableSchema.fromClass(SavedNoteDto.class));
    }

}

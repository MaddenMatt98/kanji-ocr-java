package com.maddenmatt.kanjiocr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.services.rekognition.RekognitionClient;

@Configuration
public class RekognitionClientConfig {

    @Bean
    public RekognitionClient getRekognitionClient(){
        return RekognitionClient.builder().build();
    }

}

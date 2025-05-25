package com.maddenmatt.kanjiocr.dto;

import software.amazon.awssdk.services.rekognition.model.TextDetection;

public class ParsedTextDto {

    private String detectedText;
    private Float confidence;
    private String s3ObjectKey;

    public ParsedTextDto(TextDetection textDetection, String s3ObjectKey) {
        this.detectedText = textDetection.detectedText();
        this.confidence = textDetection.confidence();
        this.s3ObjectKey = s3ObjectKey;
    }

    public String getDetectedText() {
        return detectedText;
    }

    public void setDetectedText(String detectedText) {
        this.detectedText = detectedText;
    }

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public String getS3ObjectKey() {
        return s3ObjectKey;
    }

    public void setS3ObjectKey(String s3ObjectKey) {
        this.s3ObjectKey = s3ObjectKey;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParsedTextDto{");
        sb.append("detectedText=").append(detectedText);
        sb.append(", confidence=").append(confidence);
        sb.append(", s3ObjectKey=").append(s3ObjectKey);
        sb.append('}');
        return sb.toString();
    }

}

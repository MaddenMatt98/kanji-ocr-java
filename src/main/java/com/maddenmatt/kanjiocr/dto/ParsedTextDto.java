package com.maddenmatt.kanjiocr.dto;

import java.util.Objects;

import software.amazon.awssdk.services.rekognition.model.TextDetection;

public class ParsedTextDto {

    private String detectedText;
    private Float confidence;
    private String s3ObjectKey;

    public ParsedTextDto(String detectedText, float confidence, String s3ObjectKey) {
        this.detectedText = detectedText;
        this.confidence = confidence;
        this.s3ObjectKey = s3ObjectKey;
    }

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.detectedText);
        hash = 47 * hash + Objects.hashCode(this.confidence);
        hash = 47 * hash + Objects.hashCode(this.s3ObjectKey);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParsedTextDto other = (ParsedTextDto) obj;
        if (!Objects.equals(this.detectedText, other.detectedText)) {
            return false;
        }
        if (!Objects.equals(this.s3ObjectKey, other.s3ObjectKey)) {
            return false;
        }
        return Objects.equals(this.confidence, other.confidence);
    }

}

package com.maddenmatt.kanjiocr.dto;

import software.amazon.awssdk.services.rekognition.model.TextDetection;

public class ParsedTextDto {

    private String detectedText;
    private Float confidence;

    public ParsedTextDto(TextDetection textDetection) {
        detectedText = textDetection.detectedText();
        confidence = textDetection.confidence();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParsedTextDto{");
        sb.append("detectedText=").append(detectedText);
        sb.append(", confidence=").append(confidence);
        sb.append('}');
        return sb.toString();
    }

}

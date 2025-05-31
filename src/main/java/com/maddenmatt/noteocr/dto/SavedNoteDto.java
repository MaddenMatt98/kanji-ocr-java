package com.maddenmatt.noteocr.dto;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class SavedNoteDto {

    private String id;
    private String detectedText;
    private Float confidence;
    private String s3ObjectKey;
    private String notes;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SavedNoteDto [");
        sb.append("id=").append(id);
        sb.append(", detectedText=").append(detectedText);
        sb.append(", confidence=").append(confidence);
        sb.append(", s3ObjectKey=").append(s3ObjectKey);
        sb.append(", notes=").append(notes);
        sb.append(']');
        return sb.toString();
    }

}

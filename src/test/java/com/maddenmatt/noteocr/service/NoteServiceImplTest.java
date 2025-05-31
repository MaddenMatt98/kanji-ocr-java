package com.maddenmatt.noteocr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.maddenmatt.noteocr.dto.SavedNoteDto;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@ExtendWith(SpringExtension.class)
public class NoteServiceImplTest {

    @TestConfiguration
    static class NoteServiceImplTestContextConfiguration {

        @Bean
        public NoteService createNoteService() {
            return new NoteServiceImpl();
        }

    }

    @Autowired
    private NoteService noteService;

    @MockitoBean
    private DynamoDbTable<SavedNoteDto> dynamoDbTable;

    @Test
    public void whenSaveNoteSuccessful_thenSavedNoteDtoShouldBeReturned() {
        SavedNoteDto testNote = new SavedNoteDto();
        testNote.setDetectedText("ABCD");
        testNote.setConfidence(Float.valueOf("99.9"));
        testNote.setS3ObjectKey("1417601730000-PXL_20250120_043330496.jpg");
        testNote.setNotes("Noting this for later viewing.");

        SavedNoteDto result = noteService.saveNote(testNote);

        assertTrue(result.getId().contains(testNote.getDetectedText()), "Id doesn't contain detected text!");
        assertEquals(testNote.getDetectedText(), result.getDetectedText(), "Detected text doesn't equal expected value!");
        assertEquals(testNote.getConfidence(), result.getConfidence(), "Confidence doesn't equal expected value!");
        assertEquals(testNote.getS3ObjectKey(), result.getS3ObjectKey(), "S3 Object Key doesn't equal expected value!");
        assertEquals(testNote.getNotes(), result.getNotes(), "Notes don't equal expected value!");
        verify(dynamoDbTable, times(1)).putItem(any(SavedNoteDto.class));
    }

}

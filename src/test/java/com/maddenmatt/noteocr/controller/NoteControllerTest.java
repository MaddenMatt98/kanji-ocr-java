package com.maddenmatt.noteocr.controller;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.maddenmatt.noteocr.dto.SavedNoteDto;
import com.maddenmatt.noteocr.service.NoteService;

@ExtendWith(SpringExtension.class)
public class NoteControllerTest {

    @TestConfiguration
    static class NoteControllerTestContextConfiguration {

        @Bean
        public NoteController createimageController() {
            return new NoteController();
        }

    }

    @Autowired
    private NoteController noteController;

    @MockitoBean
    private NoteService noteService;

    @Test
    public void whenPostNoteIsSuccessful_thenSavedNoteDtoIsReturned() {
        SavedNoteDto testNote = new SavedNoteDto();
        testNote.setId("1417601730000#ABCD");
        testNote.setDetectedText("ABCD");
        testNote.setConfidence(Float.valueOf("99.9"));
        testNote.setS3ObjectKey("1417601730000-PXL_20250120_043330496.jpg");
        testNote.setNotes("Noting this for later viewing.");

        when(noteService.saveNote(testNote)).thenReturn(testNote);

        SavedNoteDto result = noteController.postNote(testNote);

        assertEquals(testNote, result, "Test note not equal to result!");
        verify(noteService, times(1)).saveNote(testNote);
    }

    @Test
    public void whenListNotesIsSuccessful_thenSavedNoteDtoListIsReturned() {
        SavedNoteDto testNote = new SavedNoteDto();
        testNote.setId("1417601730000#ABCD");
        testNote.setDetectedText("ABCD");
        testNote.setConfidence(Float.valueOf("99.9"));
        testNote.setS3ObjectKey("1417601730000-PXL_20250120_043330496.jpg");
        testNote.setNotes("Noting this for later viewing.");

        List<SavedNoteDto> expectedResult = Arrays.asList(testNote);
        when(noteService.listNotes()).thenReturn(expectedResult);

        List<SavedNoteDto> result = noteController.getNotes();

        assertEquals(expectedResult, result, "Test note not equal to result!");
        verify(noteService, times(1)).listNotes();
    }

}

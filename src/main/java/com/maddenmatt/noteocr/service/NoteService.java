package com.maddenmatt.noteocr.service;

import java.util.List;

import com.maddenmatt.noteocr.dto.SavedNoteDto;

public interface NoteService {

    SavedNoteDto saveNote(SavedNoteDto noteToSave);

    List<SavedNoteDto> listNotes();

}

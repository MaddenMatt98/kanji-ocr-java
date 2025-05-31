package com.maddenmatt.noteocr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.maddenmatt.noteocr.dto.SavedNoteDto;
import com.maddenmatt.noteocr.service.NoteService;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/note")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public SavedNoteDto postNote(@RequestBody SavedNoteDto noteToSave) {
        return noteService.saveNote(noteToSave);
    }

    @GetMapping("/notes")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<SavedNoteDto> getNotes() {
        return noteService.listNotes();
    }

}

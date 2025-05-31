package com.maddenmatt.noteocr.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maddenmatt.noteocr.dto.SavedNoteDto;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private DynamoDbTable<SavedNoteDto> dynamoDbTable;

    @Override
    public SavedNoteDto saveNote(SavedNoteDto noteToSave) {
        noteToSave.setId("%s#%s".formatted(Long.toString(Instant.now().toEpochMilli()), noteToSave.getDetectedText()));

        dynamoDbTable.putItem(noteToSave);

        return noteToSave;
    }

    @Override
    public List<SavedNoteDto> listNotes() {
        /*
            This is a personal project for my own study use, I can only
            ingest so many characters at one time, so it's unlikely this database will
            ever grow large enough that pagination is needed.  In a high volume production
            system it would be advisable to implement (designing data in such a way to avoid
            scans in general would be advisable too).
        */
        PageIterable<SavedNoteDto> scanResult = dynamoDbTable.scan();

        List<SavedNoteDto> response = new ArrayList<>();
        scanResult.forEach(page -> response.addAll(page.items()));

        return response;
    }

}

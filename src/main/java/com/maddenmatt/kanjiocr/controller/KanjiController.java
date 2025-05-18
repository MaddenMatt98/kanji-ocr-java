package com.maddenmatt.kanjiocr.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maddenmatt.kanjiocr.dto.ParsedTextDto;
import com.maddenmatt.kanjiocr.service.OCRService;

@RestController
public class KanjiController {

    @Autowired
    private OCRService ocrService;

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ParsedTextDto> parseTextFromImage(@RequestParam("file") MultipartFile inputImage) throws IOException {
        List<ParsedTextDto> parsedTextInImage = ocrService.getTextInImage(inputImage);
        return parsedTextInImage;
    }

}

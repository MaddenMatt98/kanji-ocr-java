package com.maddenmatt.noteocr.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maddenmatt.noteocr.dto.ParsedTextDto;
import com.maddenmatt.noteocr.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ParsedTextDto> parseTextFromImage(@RequestParam("file") MultipartFile inputImage) throws IOException {
        String s3ObjectKey = imageService.uploadImagetoS3(inputImage);
        List<ParsedTextDto> parsedTextInImage = imageService.getTextInImage(s3ObjectKey);

        return parsedTextInImage;
    }

}

package com.maddenmatt.noteocr.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.maddenmatt.noteocr.dto.ParsedTextDto;

public interface ImageService {

    String uploadImagetoS3(MultipartFile image) throws IOException;
    List<ParsedTextDto> getTextInImage(String s3ObjectKey);

}

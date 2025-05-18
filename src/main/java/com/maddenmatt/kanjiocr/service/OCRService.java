package com.maddenmatt.kanjiocr.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.maddenmatt.kanjiocr.dto.ParsedTextDto;

public interface OCRService {
    List<ParsedTextDto> getTextInImage(MultipartFile image) throws IOException;
}

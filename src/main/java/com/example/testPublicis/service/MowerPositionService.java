package com.example.testPublicis.service;

import com.example.testPublicis.exception.InvalidFileContentException;
import java.util.List;

public interface MowerPositionService {
    List<String> calculatePositions(List<String> fileContent) throws InvalidFileContentException;
}

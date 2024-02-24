package com.example.testPublicis.controller;

import com.example.testPublicis.exception.InvalidFileContentException;
import com.example.testPublicis.service.MowerPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("MowItNow")
public class MowController {

    @Autowired
    private MowerPositionService mowerPositionService;

    @PostMapping(value="/calculatePositions",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> getFinalPositionsOfMachines(@RequestPart MultipartFile file) throws InvalidFileContentException, IOException {
        if(file!=null && !file.isEmpty())
            return mowerPositionService.calculatePositions(Arrays.asList(new String(file.getBytes(), StandardCharsets.UTF_8).split("\n")));
        else
            throw new InvalidFileContentException();
    }

}

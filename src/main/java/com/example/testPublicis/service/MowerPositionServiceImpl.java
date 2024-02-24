package com.example.testPublicis.service;

import com.example.testPublicis.exception.InvalidFileContentException;
import com.example.testPublicis.model.Mower;
import org.springframework.stereotype.Service;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Service
public class MowerPositionServiceImpl implements MowerPositionService {


    @Override
    public List<String> calculatePositions(List<String> fileContent) throws InvalidFileContentException {

        if(fileContent!=null && !fileContent.isEmpty())
        {
            String[] firstLine = fileContent.get(0).trim().split(" ");
            if(isNumeric(firstLine[0])&& isNumeric(firstLine[1]))
            {
                Point mapSize = new Point(Integer.parseInt(firstLine[0]),Integer.parseInt(firstLine[1]));
                List<Mower> mowers= new ArrayList<>();
                List<String> mowersInstructions = new ArrayList<>();
                List<Mower> mowersFinalPositions = new ArrayList<>();
                String[] data;
                for(int i =1;i<fileContent.size();i+=2){
                    data = fileContent.get(i).trim().split(" ");
                    if(data.length == 3 && isNumeric(data[0]) && isNumeric(data[1]))
                        mowers.add(new Mower(data));
                    else
                        throw new InvalidFileContentException();
                    mowersInstructions.add(fileContent.get(i+1).trim());
                }
                for(int i =0;i<mowers.size();i++)
                    calculateMowerFinalPosition(mowers.get(i),mowersInstructions.get(i),mapSize,mowersFinalPositions);

                return mowers.stream().map(Mower::getMowerPosition).collect(Collectors.toList());
            }
            else
                throw new InvalidFileContentException();
        }
        else
            throw new InvalidFileContentException();

    }

    //this method calculate the final position for each mower respecting the list of instructions provided
    private void calculateMowerFinalPosition(Mower mower,String instructions,Point mapSize,List<Mower> mowersFinalPositions) throws InvalidFileContentException {
        for (Character c:instructions.toCharArray()) {
            switch (c) {
                case 'D' -> mower.switchDirection(false);
                case 'G' -> mower.switchDirection(true);
                case 'A' -> mower.advanceMower(mapSize,mowersFinalPositions.stream().map(Mower::getPosition).collect(Collectors.toList()));
                default -> throw new InvalidFileContentException();
            }
        }
        mowersFinalPositions.add(mower);
    }


}

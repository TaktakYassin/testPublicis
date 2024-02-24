package com.example.testPublicis.model;

import com.example.testPublicis.exception.InvalidFileContentException;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.List;

import static com.example.testPublicis.model.Direction.*;
import static org.apache.commons.lang3.StringUtils.isNumeric;

@Slf4j
public class Mower {

    private Point position;
    private Direction direction;

    public Mower(String[] data) {
        this.position = new Point(Integer.parseInt(data[0]),Integer.parseInt(data[1]));
        this.direction =  Direction.of(data[2]);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    //this method get the mower position and direction as a String
    public String getMowerPosition() {
        return (int) position.getX() + " " + (int) position.getY() + " " + direction.prefix;
    }

    //this method switch by 90° the direction of the mower
    public void switchDirection(boolean leftRotation) throws InvalidFileContentException {
        if(leftRotation)
        {
            switch (direction) {
                case NORTH -> direction = WEST;
                case WEST -> direction = SOUTH;
                case SOUTH -> direction = EAST;
                case EAST -> direction = NORTH;
                default -> throw new InvalidFileContentException();
            }
        }
        else
        {
            switch (direction) {
                case NORTH -> direction = EAST;
                case WEST -> direction = NORTH;
                case SOUTH -> direction = WEST;
                case EAST -> direction = SOUTH;
                default -> throw new InvalidFileContentException();
            }
        }

    }

    //this method advance the mower in the provided direction if it is possible
    public void advanceMower(Point mapSize, List<Point> mowersFinalPositions) throws InvalidFileContentException {
        switch (direction) {
            case NORTH -> {
                if (position.getY() < mapSize.getY() && checkPossibleTranslation(position.getX(),position.getY()+1,mowersFinalPositions))
                    position.translate(0, 1);
                else
                    log.warn("End of map reached or an already mower was fond");
            }
            case EAST -> {
                if (position.getX() < mapSize.getX() && checkPossibleTranslation(position.getX()+1,position.getY(),mowersFinalPositions))
                    position.translate(1, 0);
                else
                    log.warn("End of map reached or an already mower was fond");
            }
            case WEST -> {
                if (position.getX() > 0 && checkPossibleTranslation(position.getX()-1,position.getY(),mowersFinalPositions))
                    position.translate(-1, 0);
                else
                    log.warn("End of map reached or an already mower was fond");
            }
            case SOUTH -> {
                if (position.getY() > 0 && checkPossibleTranslation(position.getX(),position.getY()-1,mowersFinalPositions))
                    position.translate(0, -1);
                else
                    log.warn("End of map reached or an already mower was fond");
            }
            default -> throw new InvalidFileContentException();
        }
    }

    //this method check if the translation of the mower is possible
    private boolean checkPossibleTranslation(double x,double y,List<Point> mowersFinalPositions){
        return !mowersFinalPositions.contains(new Point((int) x,(int) y));
    }

    @Override
    public String toString() {
        return "Mower{" +
                "Position=" + position +
                ", direction=" + direction +
                '}';
    }
}

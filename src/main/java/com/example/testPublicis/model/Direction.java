package com.example.testPublicis.model;

public enum Direction {
    NORTH('N'),
    EAST('E'),
    WEST('W'),
    SOUTH('S');

    public final char prefix;

    Direction(char prefix) {
        this.prefix = prefix;
    }

    public static Direction of(String d) {
        for (Direction direction : values()) {
            if (direction.prefix == d.charAt(0)) {
                return direction;
            }
        }
        throw new IllegalArgumentException(d);
    }
}

package com.garnerju.catalogservice.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    @Test
    public void testThatYouCanCreateNewGameWithArgs() {
        Game testGame1 =
                new Game(1, "PacMan", "5", "Eat things", BigDecimal.valueOf(10.00), "Pacman", 4);
        Game testGame =
                new Game(1, "PacMan", "5", "Eat things", BigDecimal.valueOf(10.00), "Pacman", 4);

        assertEquals(testGame1,testGame);

    }
}
package com.example.springtest1.game;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "nintendo.game", havingValue = "mario")
public class Mario implements NintendoGames {
    @Override
    public void up() {
        System.out.println("Mario jumps");
    }

    @Override
    public void down() {
        System.out.println("Mario enters a pipe");
    }

    @Override
    public void left() {
        System.out.println("Mario moves left");
    }

    @Override
    public void right() {
        System.out.println("Mario moves right");
    }
}


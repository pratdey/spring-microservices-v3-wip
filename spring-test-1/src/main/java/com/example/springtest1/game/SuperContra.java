package com.example.springtest1.game;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "nintendo.game", havingValue = "super-contra")
public class SuperContra implements NintendoGames {
    @Override
    public void up() {
        System.out.println("Super Contra jumps");
    }

    @Override
    public void down() {
        System.out.println("Super Contra ducks");
    }

    @Override
    public void left() {
        System.out.println("Super Contra moves left");
    }

    @Override
    public void right() {
        System.out.println("Super Contra moves right");
    }
}


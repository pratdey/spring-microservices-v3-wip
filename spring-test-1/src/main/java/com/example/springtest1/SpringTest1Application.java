package com.example.springtest1;

import com.example.springtest1.controller.DataController;
import com.example.springtest1.game.GameRunner;
import com.example.springtest1.game.Mario;
import com.example.springtest1.game.NintendoGames;
import com.example.springtest1.game.SuperContra;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.springtest1")  // default component scan under the current pkg
public class SpringTest1Application {

    public static void main(String[] args) {
       
     // 1st level loose coupling using interface
        /* NintendoGames game =  new Mario();
        GameRunner runner = new GameRunner(game);
        runner.run();
 */
        /* ConfigurableApplicationContext context = SpringApplication.run(SpringTest1Application.class, args);
        GameRunner runner = context.getBean(GameRunner.class); */
        //runner.run();  
        
        ConfigurableApplicationContext contextsrv = SpringApplication.run(SpringTest1Application.class, args);
        DataController dController = contextsrv.getBean(DataController.class);
        dController.sumData();
        
    }


    /* 
    @Bean
    CommandLineRunner runGame(GameRunner gameRunner) {
        return args -> gameRunner.run();
    }
        */
}


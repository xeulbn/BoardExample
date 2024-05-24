package org.example.boardexamapplication;

import org.example.boardexamapplication.repository.BoardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BoardExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardExamApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner run(BoardRepository repository) {
//        return args -> {
//            repository.findAll().forEach(System.out::println);
//        };
//    }

}

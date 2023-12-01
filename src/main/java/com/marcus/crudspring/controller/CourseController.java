package com.marcus.crudspring.controller;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marcus.crudspring.model.Course;
import com.marcus.crudspring.repository.CourseRepository;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;

import java.util.List;

@Component // spring cria instancia e gerencia o ciclo de vida dela
@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {

    // @Autowired
    private final CourseRepository courseRepository;

    @GetMapping
    // @RequestMapping(method = RequestMethod.GET)
    public List<Course> list() {
        return courseRepository.findAll();
    }


    @PostMapping
    public Course create(@RequestBody Course course){
        return courseRepository.save(course);
    }

    @Bean
    CommandLineRunner initDatabase(CourseRepository courseRepository) {

        return args -> {
            courseRepository.deleteAll();
            Course c = new Course();
            c.setName("backend spring");
            c.setCategory("backend");
            courseRepository.save(c);
        };
    }
}

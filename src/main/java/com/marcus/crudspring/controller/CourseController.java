package com.marcus.crudspring.controller;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marcus.crudspring.model.Course;
import com.marcus.crudspring.repository.CourseRepository;

import io.micrometer.core.ipc.http.HttpSender.Response;
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

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable Long id){
       return courseRepository.findById(id)
       .map(recordFound -> ResponseEntity.ok()
       .body(recordFound))
       .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Course create(@RequestBody Course course){
        return courseRepository.save(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id,@RequestBody Course course){
        return courseRepository.findById(id)
       .map(recordFound -> {
            recordFound.setName(course.getName());
            recordFound.setCategory(course.getCategory());
            Course updated = courseRepository.save(recordFound);
            return ResponseEntity.ok().body(updated);
       })
       .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return courseRepository.findById(id)
            .map(recordFound -> {
                    courseRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());

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

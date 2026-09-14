package com.example.springboottest1.controller;

import com.example.springboottest1.model.Course;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    @GetMapping("/courses")
    public List<Course> retrieveCourses() {
        return List.of(
                new Course("ECON101", "Economics Fundamentals 1", "Commerce"),
                new Course("PHY201", "Physics Optics 3", "Science"),
                new Course("PHY202", "Physics Optics 4", "Science"),
                new Course("ART102", "Sociology Fundamentals 1", "Arts"),
                new Course("ART110", "Sociology Intermediate 2", "Arts")
        );
    }
}

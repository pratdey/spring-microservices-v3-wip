/* package com.example.springbootjpahibernate.runner;

import com.example.springbootjpahibernate.Entity.CourseEntity;
import com.example.springbootjpahibernate.repository.CourseJPARepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseJPACLRunner implements CommandLineRunner {

    private final CourseJPARepository courseJPARepository;

    public CourseJPACLRunner(CourseJPARepository courseJPARepository) {
        this.courseJPARepository = courseJPARepository;
    }

    @Override
    public void run(String... args) {
        courseJPARepository.insertCourse(new CourseEntity(2, "phy001", "dutta"));
        courseJPARepository.insertCourse(new CourseEntity(3, "phy001", "dutta"));
        courseJPARepository.insertCourse(new CourseEntity(8, "phy001", "dutta"));
        courseJPARepository.insertCourse(new CourseEntity(9, "phy001", "dutta"));

        courseJPARepository.deleteCourse(3);

        CourseEntity course = courseJPARepository.findById(2);
        System.out.println("Course: id=" + course.getId()
                + ", name=" + course.getName()
                + ", author=" + course.getAuthor());
    }
} */

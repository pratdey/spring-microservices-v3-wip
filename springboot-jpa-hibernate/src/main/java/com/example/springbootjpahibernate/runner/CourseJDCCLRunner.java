/* package com.example.springbootjpahibernate.runner;

import com.example.springbootjpahibernate.Entity.Course;
import com.example.springbootjpahibernate.repository.CourseJdbcRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseJDCCLRunner implements CommandLineRunner {

    private final CourseJdbcRepository courseJdbcRepository;

    public CourseJDCCLRunner(CourseJdbcRepository courseJdbcRepository) {
        this.courseJdbcRepository = courseJdbcRepository;
    }

    @Override
    public void run(String... args) {
        courseJdbcRepository.insertCourse(new Course( 2 , "phy001" , "dutta"));
        courseJdbcRepository.insertCourse(new Course( 3 , "phy001" , "dutta"));
        courseJdbcRepository.insertCourse(new Course( 8 , "phy001" , "dutta"));
        courseJdbcRepository.insertCourse(new Course( 9 , "phy001" , "dutta"));
        
        courseJdbcRepository.deleteCourse( 8);

        Course course = courseJdbcRepository.findById(2);
        System.out.println("Course: id=" + course.getId()
                + ", name=" + course.getName()
                + ", author=" + course.getAuthor());
    }
} */

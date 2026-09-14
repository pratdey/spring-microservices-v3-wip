package com.example.springbootjpahibernate.runner;

import com.example.springbootjpahibernate.Entity.CourseEntity;
import com.example.springbootjpahibernate.repository.CourseSpringDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseSDRCLRunner implements CommandLineRunner {

    private final CourseSpringDataRepository courseSpringDataRepository;

    public CourseSDRCLRunner(CourseSpringDataRepository courseSpringDataRepository) {
        this.courseSpringDataRepository = courseSpringDataRepository;
    }

    @Override
    public void run(String... args) {
        courseSpringDataRepository.save(new CourseEntity(2, "phy001", "dutta"));
        courseSpringDataRepository.save(new CourseEntity(3, "phy001", "hcverma"));
        courseSpringDataRepository.save(new CourseEntity(8, "phy001", "mahadevDK"));
        courseSpringDataRepository.save(new CourseEntity(9, "phy001", "daspal"));

        courseSpringDataRepository.deleteById(8);

        CourseEntity course = courseSpringDataRepository.findById(9).orElse(null);
        if (course != null) {
            System.out.println("Course: id=" + course.getId()
                    + ", name=" + course.getName()
                    + ", author=" + course.getAuthor());
        }

        System.out.println("All courses:");
        courseSpringDataRepository.findAll().forEach(allCourse ->
                System.out.println("Course: id=" + allCourse.getId()
                        + ", name=" + allCourse.getName()
                        + ", author=" + allCourse.getAuthor()));

        System.out.println("Courses by author (dutta):");
        courseSpringDataRepository.findByAuthor("dutta").forEach(authorCourse ->
                System.out.println("Course: id=" + authorCourse.getId()
                        + ", name=" + authorCourse.getName()
                        + ", author=" + authorCourse.getAuthor()));
    }
}

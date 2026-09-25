package com.example.springbootjpahibernate.repository;

import com.example.springbootjpahibernate.Entity.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class CourseJPARepository {

    private final EntityManager entityManager;

    public CourseJPARepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void insertCourse(CourseEntity course) {
        entityManager.persist(course);
    }

    @Transactional
    public CourseEntity updateCourse(int id, String name, String author) {
        CourseEntity course = entityManager.find(CourseEntity.class, id);

        if (course != null) {
            course.setName(name);
            course.setAuthor(author);
        }

        return course;
    }

    @Transactional
    public void deleteCourse(int id) {
        CourseEntity course = entityManager.find(CourseEntity.class, id);

        if (course != null) {
            entityManager.remove(course);
        }
    }

    public CourseEntity findById(int id) {
        return entityManager.find(CourseEntity.class, id);
    }
}

package com.example.springbootjpahibernate.repository;

import com.example.springbootjpahibernate.Entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseSpringDataRepository extends JpaRepository<CourseEntity, Integer> {

    List<CourseEntity> findByAuthor(String author);
}

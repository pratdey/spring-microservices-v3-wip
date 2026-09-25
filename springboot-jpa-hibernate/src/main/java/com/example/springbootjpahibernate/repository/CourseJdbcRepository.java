package com.example.springbootjpahibernate.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.springbootjpahibernate.Entity.Course;

@Repository
public class CourseJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public CourseJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int updateCourse(int id, String name, String author) {
        String sql = "UPDATE Course SET name = ?, author = ? WHERE id = ?";
        return jdbcTemplate.update(sql, name, author, id);
    }

    public int insertCourse(Course course) {
        String sql = "INSERT INTO Course (id, name, author) VALUES (?, ?, ?)";
        //return jdbcTemplate.update(sql, id, name, author);
        //return jdbcTemplate.update(sql, 2, "phy001", "hcverma");
        return jdbcTemplate.update(sql, course.getId() , course.getName() , course.getAuthor());
    }

    public int deleteCourse(int id) {
        String sql = "DELETE FROM Course WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Course findById(int id) {
        String sql = "SELECT id, name, author FROM Course WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) ->
                new Course(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("author")
                ), id);
    }
}

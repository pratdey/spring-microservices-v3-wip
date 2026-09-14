package com.example.springboottest1.model;

public class Course {

    private String course_id;
    private String course_desc;
    private String course_stream;

    public Course(String course_id, String course_desc, String course_stream) {
        this.course_id = course_id;
        this.course_desc = course_desc;
        this.course_stream = course_stream;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_desc() {
        return course_desc;
    }

    public void setCourse_desc(String course_desc) {
        this.course_desc = course_desc;
    }

    public String getCourse_stream() {
        return course_stream;
    }

    public void setCourse_stream(String course_stream) {
        this.course_stream = course_stream;
    }
}

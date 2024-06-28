package com.workintech.spring17challenge.controller;


import com.workintech.spring17challenge.model.*;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech/courses")
@CrossOrigin(origins = "http://localhost:3000/")
public class CourseController {

    private Map<Integer, Course> courses;
    private int currentId = 1;
    private final LowCourseGpa lowCourseGpa = new LowCourseGpa();
    private final MediumCourseGpa mediumCourseGpa = new MediumCourseGpa();
    private final HighCourseGpa highCourseGpa = new HighCourseGpa();

    @PostConstruct
    public void init() {
        courses = new HashMap<>();
        courses.put(currentId++, new Course("Math", 3, new Grade(3, "B")));
        courses.put(currentId++, new Course("Science", 4, new Grade(4, "A")));
    }

    @GetMapping("/all")
    public List<Course> getAllCourses() {
        return courses.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        if (!courses.containsKey(id)) {
            throw new IllegalArgumentException("Course not found with id: " + id);
        }
        return courses.get(id);
    }

    @PostMapping("/add")
    public Map<String, Object> addCourse(@RequestBody Course course) {
        for (Course existingCourse : courses.values()) {
            if (existingCourse.getName().equals(course.getName())) {
                throw new IllegalArgumentException("Course with the same name already exists.");
            }
        }
        if (course.getCredit() < 1 || course.getCredit() > 4) {
            throw new IllegalArgumentException("Credit value must be between 1 and 4");
        }

        int totalGpa = calculateGpa(course);
        courses.put(currentId++, course);

        Map<String, Object> response = new HashMap<>();
        response.put("course", course);
        response.put("totalGpa", totalGpa);

        return response;
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateCourse(@PathVariable int id, @RequestBody Course course) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        if (!courses.containsKey(id)) {
            throw new IllegalArgumentException("Course not found with id: " + id);
        }
        if (course.getCredit() < 1 || course.getCredit() > 4) {
            throw new IllegalArgumentException("Credit value must be between 1 and 4");
        }

        int totalGpa = calculateGpa(course);
        courses.put(id, course);

        Map<String, Object> response = new HashMap<>();
        response.put("course", course);
        response.put("totalGpa", totalGpa);

        return response;
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        if (!courses.containsKey(id)) {
            throw new IllegalArgumentException("Course not found with id: " + id);
        }
        courses.remove(id);
    }

    private int calculateGpa(Course course) {
        int gpa = 0;
        if (course.getCredit() == 1 || course.getCredit() == 2) {
            gpa = course.getGrade().getCoefficient() * course.getCredit() * lowCourseGpa.getGpa();
        } else if (course.getCredit() == 3) {
            gpa = course.getGrade().getCoefficient() * course.getCredit() * mediumCourseGpa.getGpa();
        } else if (course.getCredit() == 4) {
            gpa = course.getGrade().getCoefficient() * course.getCredit() * highCourseGpa.getGpa();
        }
        return gpa;
    }
}

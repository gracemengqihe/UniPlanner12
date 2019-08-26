package com.example.uniplanner.program.structure;

import com.example.uniplanner.persistence.AppDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Program {

    private final int id;

    private String title;
    private String description;
    private String level;

    private List<String> learning_outcomes;
    private String award;

    private double duration;
    private int uoc;

    private Map<String, Course> courses;

    private Map<String, Set<String>> selective;

    public Program(int id, String title, String description, String level, String award, double duration, int uoc) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.level = level;
        this.learning_outcomes = new ArrayList<>();
        this.award = award;
        this.duration = duration;
        this.uoc = uoc;
        this.courses = new HashMap<>();
        this.selective = new HashMap<>();
    }

    public Program(int id, String title, String description, String level, int uoc) {
        this(id, title, description, level, "None", 3, uoc);
    }

    public String getAward() {
        return award;
    }

    public Course getCourse(String id) {
        return courses.get(id.toLowerCase());
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses.values());
    }

    public List<Course> getCourses(Course.CourseType type, Profile profile) {
        switch (type) {
            case CORE:
                return courses.values().stream().filter((course) -> course.is(type) && !selective.getOrDefault(course.getID(), new HashSet<>()).stream().anyMatch((id) -> AppDatabase.get().getRepository().isTaking(profile.getID(), id))).collect(Collectors.toList());

            case ELECTIVE:
                return courses.values().stream().filter((course) -> course.is(type) || selective.getOrDefault(course.getID(), new HashSet<>()).stream().anyMatch((id) -> AppDatabase.get().getRepository().isTaking(profile.getID(), id))).collect(Collectors.toList());

            default:
                return courses.values().stream().filter((course) -> course.is(type)).collect(Collectors.toList());
        }
    }

    public String getDescription() {
        return description;
    }

    public double getDuration() {
        return duration;
    }

    public int getID() {
        return id;
    }

    public List<String> getLearningOutcomes() {
        return Collections.unmodifiableList(learning_outcomes);
    }

    public String getLevel() {
        return level;
    }

    public String getTitle() {
        return title;
    }

    public int getUOC() {
        return uoc;
    }

    public Program setAward(String award) {
        if (award != null && !award.isEmpty())
            this.award = award;
        else
            this.award = "None";

        return this;
    }

    public Program setCourses(Map<String, Course> courses) {
        if (courses != null)
            this.courses = Collections.unmodifiableMap(courses);
        else
            this.courses = new HashMap<>();

        return this;
    }

    public Program setDescription(String description) {
        if (description != null && !description.isEmpty())
            this.description = description;
        else
            this.description = "This program has no description";

        return this;
    }

    public Program setDuration(double duration) {
        if (duration > 0)
            this.duration = duration;
        else
            this.duration = 1.0;

        return this;
    }

    public Program setLearningOutcomes(List<String> learning_outcomes) {
        if (learning_outcomes != null)
            this.learning_outcomes = new ArrayList<>(learning_outcomes);
        else
            this.learning_outcomes = new ArrayList<>();

        return this;
    }

    public Program setTitle(String title) {
        if (title != null && !title.isEmpty())
            this.title = title;
        else
            this.title = "Unknown Program";

        return this;
    }

    public Program setUOC(int uoc) {
        if (uoc >= 6)
            this.uoc = uoc;
        else
            this.uoc = 6;

        return this;
    }
}
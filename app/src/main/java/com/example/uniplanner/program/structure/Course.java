package com.example.uniplanner.program.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Course {

    private final String id;

    private String title;
    private String description;

    private CourseType type;

    private String faculty;
    private String school;

    private List<String> notes;
    private int level;
    private int uoc;

    private int min_uoc;
    private Set<Set<String>> prerequisite;
    private Set<String> excluded;
    private Set<String> equivalent;

    private boolean[] terms;

    public Course(String id, String title, String description, CourseType type, int level, int uoc, int min_uoc) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.faculty = "None";
        this.school = "None";
        this.notes = new ArrayList<>();
        this.level = level;
        this.uoc = uoc;
        this.min_uoc = min_uoc;
        this.prerequisite = new HashSet<>();
        this.excluded = new HashSet<>();
        this.equivalent = new HashSet<>();
        this.terms = new boolean[] { false, false, false };
    }

    public Course(String id) {
        this(id, "Unknown Course", "This course has no description.", CourseType.UNAVAILABLE, 1, 6, 0);
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getEquivalent() {
        return Collections.unmodifiableSet(equivalent);
    }

    public Set<String> getExcluded() {
        return Collections.unmodifiableSet(excluded);
    }

    public String getFaculty() {
        return faculty;
    }

    public String getID() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public int getMinUOC() {
        return min_uoc;
    }

    public List<String> getNotes() {
        return Collections.unmodifiableList(notes);
    }

    public Set<Set<String>> getPrerequisites() {
        return Collections.unmodifiableSet(prerequisite);
    }

    public String getSchool() {
        return school;
    }

    public String getTitle() {
        return title;
    }

    public CourseType getType() {
        return type;
    }

    public int getUOC() {
        return uoc;
    }

    public boolean is(CourseType type) {
        return this.type.equals(type);
    }

    public boolean isAvailable(int term) {
        return term >= 1 && term <= 3 && terms[term - 1];
    }

    public Course setAvailability(boolean uno, boolean dos, boolean tres) {
        this.terms[0] = uno;
        this.terms[1] = dos;
        this.terms[2] = tres;

        return this;
    }

    public Course setAvailability(int term, boolean availability) {
        if (term >= 1 && term <= 3)
            this.terms[term - 1] = availability;

        return this;
    }

    public Course setDescription(String description) {
        if(description != null && !description.isEmpty())
            this.description = description;
        else
            this.description = "This course has no description.";

        return this;
    }

    public Course setEquivalent(Set<String> equivalent) {
        if (equivalent != null)
            this.equivalent = new HashSet<>(equivalent);
        else
            this.equivalent = new HashSet<>();

        return this;
    }

    public Course setExcluded(Set<String> excluded) {
        if (excluded != null)
            this.excluded = new HashSet<>(excluded);
        else
            this.excluded = new HashSet<>();

        return this;
    }

    public Course setFaculty(String faculty) {
        if (faculty != null && !faculty.isEmpty())
            this.faculty = faculty;
        else
            this.faculty = "None";

        return this;
    }

    public Course setLevel(int level) {
        if (level > 0)
            this.level = level;
        else
            this.level = 1;

        return this;
    }

    public Course setMinUOC(int min_uoc) {
        if (min_uoc >= 0)
            this.min_uoc = min_uoc;
        else
            this.min_uoc = 0;

        return this;
    }

    public Course setNotes(List<String> notes) {
        if (notes != null)
            this.notes = notes;
        else
            this.notes = new ArrayList<>();

        return this;
    }

    public Course setPrerequisites(Set<Set<String>> prerequisite) {
        if (prerequisite != null)
            this.prerequisite = prerequisite;
        else
            this.prerequisite = new HashSet<>();

        return this;
    }

    public Course setSchool(String school) {
        if (school != null && !school.isEmpty())
            this.school = school;
        else
            this.school = "None";

        return this;
    }

    public Course setTitle(String title) {
        if (title != null && !title.isEmpty())
            this.title = title;
        else
            this.title = "Unknown Course";

        return this;
    }

    public Course setType(CourseType type) {
        if (type != null)
            this.type = type;
        else
            this.type = CourseType.UNAVAILABLE;

        return this;
    }

    public Course setUOC(int uoc) {
        if (uoc >= 6)
            this.uoc = uoc;
        else
            this.uoc = 6;

        return this;
    }

    public enum CourseType {
        CORE("Core Courses"),
        ELECTIVE("Elective Courses"),
        GENERAL("General Education"),
        UNAVAILABLE("Unavailable");

        private final String title;

        CourseType(String title) {
            this.title = title;
        }

        public String title() {
            return title;
        }
    }
}
package com.example.uniplanner.program.structure;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProgramLoader {

    private static Course create(JSONObject course_json) {
        String id = course_json.optString("id");

        String title = course_json.optString("title");
        String description = course_json.optString("description");

        Course.CourseType type = Course.CourseType.valueOf(course_json.optString("type").toUpperCase());

        int level = course_json.optInt("level", 1);
        int uoc = course_json.optInt("uoc", 6);

        int min_uoc = course_json.optInt("min_uoc", 0);

        Course course = new Course(id, title, description, type, level, uoc, min_uoc);

        course.setNotes(toList(course_json.optJSONArray("notes"), String.class));

        JSONArray prerequisite_json = course_json.optJSONArray("prerequisite");

        Set<Set<String>> prerequisite = new HashSet<>();

        if (prerequisite_json != null) {
            for (int or = 0; or < prerequisite_json.length(); or++) {
                if (prerequisite_json.isNull(or))
                    continue;

                prerequisite.add(toSet(prerequisite_json.optJSONArray(or), String.class));
            }
        }

        course.setPrerequisites(prerequisite);

        course.setExcluded(toSet(course_json.optJSONArray("exclude"), String.class));

        course.setEquivalent(toSet(course_json.optJSONArray("equivalent"), String.class));

        JSONArray terms_json = course_json.optJSONArray("terms");

        if (terms_json != null) {
            for (int t = 0; t < terms_json.length(); t++) {
                if (terms_json.isNull(t))
                    continue;

                course.setAvailability(terms_json.optInt(t), true);
            }
        }

        return course;
    }

    private static Map<String, Course> generate(JSONArray course_array) {
        Map<String, Course> courses = new HashMap<>();

        if (course_array == null)
            return courses;

        for (int c = 0; c < course_array.length(); c++) {
            if (course_array.isNull(c))
                continue;

            Course course = create(course_array.optJSONObject(c));

            courses.put(course.getID().toLowerCase(), course);
        }

        return courses;
    }

    public static Program load(JSONObject program_json) {
        int id = program_json.optInt("id");

        String title = program_json.optString("title", "Unknown Program");
        String description = program_json.optString("description", "This program has no description.");
        String level = program_json.optString("level", "Unavailable");

        String award = program_json.optString("award", "None");

        double duration = program_json.optDouble("duration", 0);
        int uoc = program_json.optInt("uoc", 0);

        Program program = new Program(id, title, description, level, award, duration, uoc);

        program.setLearningOutcomes(toList(program_json.optJSONArray("learning_outcomes"), String.class));

        program.setCourses(generate(program_json.optJSONArray("courses")));

        return program;
    }

    private static <T> List<T> toList(JSONArray array, Class<T> type) {
        List<T> list = new ArrayList<>();

        if (array == null)
            return list;

        for (int i = 0; i < array.length(); i++) {
            if (array.isNull(i))
                continue;

            list.add(type.cast(array.opt(i)));
        }

        return list;
    }

    private static <T> Set<T> toSet(JSONArray array, Class<T> type) {
        Set<T> set = new HashSet<>();

        if (array == null)
            return set;

        for (int i = 0; i < array.length(); i++) {
            if (array.isNull(i))
                continue;

            set.add(type.cast(array.opt(i)));
        }

        return set;
    }
}
package com.example.uniplanner.program;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.uniplanner.R;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CourseWindow extends PopupWindow {

    private final TextView course_header;

    private final TextView course_name;
    private final TextView course_level;
    private final TextView course_uoc;
    private final TextView course_terms;
    private final TextView course_description;

    private final LinearLayout prerequisite_shard;
    private final LinearLayout exclude_shard;
    private final LinearLayout equivalent_shard;

    private final TextView prerequisite_courses;
    private final TextView exclude_courses;
    private final TextView equivalent_courses;

    private final Button action;

    public CourseWindow(ViewGroup source) {
        super(LayoutInflater.from(source.getContext()).inflate(R.layout.window_course, source, false), 955, 1380);

        this.course_header = getContentView().findViewById(R.id.window_header);
        this.course_name = getContentView().findViewById(R.id.window_course_name);
        this.course_level = getContentView().findViewById(R.id.window_course_level);
        this.course_uoc = getContentView().findViewById(R.id.window_course_uoc);
        this.course_terms = getContentView().findViewById(R.id.window_course_terms);
        this.course_description = getContentView().findViewById(R.id.window_course_description);

        this.prerequisite_shard = getContentView().findViewById(R.id.prerequisite_shard);
        this.exclude_shard = getContentView().findViewById(R.id.exclude_shard);
        this.equivalent_shard = getContentView().findViewById(R.id.equivalent_shard);

        this.prerequisite_courses = getContentView().findViewById(R.id.window_course_prerequisite);
        this.exclude_courses = getContentView().findViewById(R.id.window_course_exclude);
        this.equivalent_courses = getContentView().findViewById(R.id.window_course_equivalent);

        this.action = getContentView().findViewById(R.id.window_action);

        getContentView().findViewById(R.id.window_cancel).setOnClickListener((view) -> dismiss());
    }

    private void onAction(View view, Function<View, Boolean> function) {
        if (!function.apply(view))
            return;

        dismiss();
    }

    public void setAction(String name, Function<View, Boolean> function) {
        action.setText(name);
        action.setOnClickListener((view) -> onAction(view, function));
    }

    public void setDescription(String description) {
        course_description.setText(description);
    }

    public void setEquivalentCourses(Set<String> equivalent) {
        if (!equivalent.isEmpty())
            equivalent_courses.setText(equivalent.stream().collect(Collectors.joining(", ")));
        else
            equivalent_shard.setVisibility(View.GONE);
    }

    public void setExclude(Set<String> exclude) {
        if (!exclude.isEmpty())
            exclude_courses.setText(exclude.stream().collect(Collectors.joining(", ")));
        else
            exclude_shard.setVisibility(View.GONE);
    }

    public void setHeader(String id) {
        course_header.setText(id);
    }

    public void setLevel(String level) {
        course_level.setText(level);
    }

    public void setName(String course) {
        course_name.setText(course);
    }

    public void setPrerequisite(Set<Set<String>> prerequisite) {
        switch (prerequisite.size()) {
            case 0:
                prerequisite_shard.setVisibility(View.GONE);
                break;

            case 1:
                prerequisite_courses.setText(prerequisite.stream().map((set) -> set.stream().collect(Collectors.joining(" AND "))).collect(Collectors.joining()));
                break;

            default:
                prerequisite_courses.setText("(" + prerequisite.stream().map((set) -> set.stream().collect(Collectors.joining(" AND "))).collect(Collectors.joining( ") OR (")) + ")");
                break;
        }
    }

    public void setTerms(String terms) {
        course_terms.setText(terms);
    }

    public void setUOC(String uoc) {
        course_uoc.setText(uoc);
    }
}
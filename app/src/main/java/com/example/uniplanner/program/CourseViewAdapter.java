package com.example.uniplanner.program;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uniplanner.R;
import com.example.uniplanner.program.structure.Course;
import com.example.uniplanner.program.structure.Course.CourseType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class CourseViewAdapter extends Adapter<CourseViewAdapter.CourseHolder> implements Filterable {

    private final CourseListActivity activity;

    private final CourseType type;
    private final List<Course> courses;

    private List<Course> filtered;

    public CourseViewAdapter(CourseListActivity activity, CourseType type, List<Course> courses) {
        this.activity = activity;
        this.type = type;
        this.courses = courses;
        this.filtered = new ArrayList<>(courses);
    }

    @Override
    public Filter getFilter() {
        return new CourseFilter();
    }

    @Override
    public int getItemCount() {
        return filtered.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, final int position) {
        Course course = filtered.get(position);

        holder.setItemTitle(course.getID() + " - " + course.getTitle());
        holder.setItemLevel("Level " + course.getLevel());
        holder.setItemUOC(course.getUOC() + " UOC");
        holder.setItemFaculty(course.getFaculty());

        holder.getCourseWindow().setHeader(course.getID());
        holder.getCourseWindow().setName(course.getTitle());

        holder.getCourseWindow().setLevel(String.valueOf(course.getLevel()));
        holder.getCourseWindow().setUOC(String.valueOf(course.getUOC()));
        holder.getCourseWindow().setTerms(Stream.of(1, 2, 3).filter((term) -> (course.isAvailable(term))).map((term) -> term.toString()).collect(Collectors.joining(", ")));

        holder.getCourseWindow().setPrerequisite(course.getPrerequisites());
        holder.getCourseWindow().setExclude(course.getExcluded());
        holder.getCourseWindow().setEquivalentCourses(course.getEquivalent());

        holder.getCourseWindow().setDescription(course.getDescription());

        holder.getCourseWindow().setAction("Select", (view) -> activity.onSelect(course.getID(), type));
    }

    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup group, int type) {
        return new CourseHolder(group, (LinearLayout) LayoutInflater.from(group.getContext()).inflate(R.layout.recycler_course, group, false));
    }

    public void onQuery(String query) {

    }

    public static class CourseHolder extends ViewHolder {

        private final CourseWindow window;

        private final TextView item_title;
        private final TextView item_level;
        private final TextView item_uoc;
        private final TextView item_faculty;

        public CourseHolder(ViewGroup source, LinearLayout item) {
            super(item);

            this.window = new CourseWindow(source);

            window.setElevation(25);

            this.item_title = item.findViewById(R.id.course_title);
            this.item_level = item.findViewById(R.id.course_level);
            this.item_uoc = item.findViewById(R.id.course_uoc);
            this.item_faculty = item.findViewById(R.id.course_faculty);

            item.setOnClickListener((view) -> window.showAtLocation(source, Gravity.CENTER, 0, -80));
        }

        public CourseWindow getCourseWindow() {
            return window;
        }

        public void setItemFaculty(String faculty) {
            item_faculty.setText(faculty);
        }

        public void setItemLevel(String level) {
            item_level.setText(level);
        }

        public void setItemTitle(String title) {
            item_title.setText(title);
        }

        public void setItemUOC(String uoc) {
            item_uoc.setText(uoc);
        }
    }

    public class CourseFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence sequence) {
            String query = sequence.toString().toLowerCase();

            if (!query.isEmpty())
                filtered = courses.stream().filter((course) -> course.getID().toLowerCase().contains(query) || course.getTitle().toLowerCase().contains(query)).collect(Collectors.toList());
            else
                filtered = new ArrayList<>(courses);

            FilterResults result = new FilterResults();

            result.values = filtered;

            return result;
        }

        @Override
        protected void publishResults(CharSequence sequence, FilterResults result) {
            filtered = (ArrayList) result.values;

            notifyDataSetChanged();
        }
    }
}
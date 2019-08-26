package com.example.uniplanner.collection.item;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.uniplanner.R;
import com.example.uniplanner.UniPlanner;
import com.example.uniplanner.persistence.AppDatabase;
import com.example.uniplanner.persistence.CourseSelection;
import com.example.uniplanner.program.CourseWindow;
import com.example.uniplanner.program.structure.Course;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemViewAdapter extends Adapter<ItemViewAdapter.ItemHolder> {

    private final UniPlanner planner;

    private final long id;

    private final int items;

    public ItemViewAdapter(UniPlanner planner, long id, int items) {
        this.planner = planner;
        this.id = id;
        this.items = items;
    }

    private int[] fromAbsolutePosition(int position) {
        int choice = (int) (position % Math.pow(3, 1));
        int term = (int) (((position - choice) / Math.pow(3, 1)) % Math.pow(3, 1));
        int year = (int) ((((position - choice) / Math.pow(3, 1) - term) / Math.pow(3, 1)) % Math.pow(3, 1));

        return new int[] { year + 1, term + 1, choice + 1 };
    }

    @Override
    public int getItemCount() {
        return items;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        int[] selection = fromAbsolutePosition(position);

        if (AppDatabase.get().getRepository().hasSelected(id, selection[0], selection[1], selection[2])) {
            CourseSelection selected = AppDatabase.get().getRepository().getSelection(id, selection[0], selection[1], selection[2]);

            Course course = planner.getProgram(R.string.mvp_program).getCourse(selected.course);

            holder.getCourseWindow().setHeader(course.getID());
            holder.getCourseWindow().setName(course.getTitle());

            holder.getCourseWindow().setLevel(String.valueOf(course.getLevel()));
            holder.getCourseWindow().setUOC(String.valueOf(course.getUOC()));
            holder.getCourseWindow().setTerms(Stream.of(1, 2, 3).filter((term) -> (course.isAvailable(term))).map((term) -> term.toString()).collect(Collectors.joining(", ")));

            holder.getCourseWindow().setPrerequisite(course.getPrerequisites());
            holder.getCourseWindow().setExclude(course.getExcluded());
            holder.getCourseWindow().setEquivalentCourses(course.getEquivalent());

            holder.getCourseWindow().setDescription(course.getDescription());

            holder.getCourseWindow().setAction("Drop", (view) -> { AppDatabase.get().getRepository().delete(selected); return true; });

            holder.setIcon(planner.getResources().getIdentifier("item" + String.format("%02d", position + 1), "drawable", planner.getPackageName()));
            holder.setLabel(selected.course);

            holder.setResponsive(true);

        } else {
            holder.setIcon(planner.getResources().getIdentifier("g_item" + String.format("%02d", position + 1), "drawable", planner.getPackageName()));
            holder.setLabel("");

            holder.setResponsive(false);
        }
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup group, int type) {
        return new ItemHolder(group, (LinearLayout) LayoutInflater.from(planner).inflate(R.layout.recycler_collectible, group, false));
    }

    public static class ItemHolder extends ViewHolder {

        private final CourseWindow window;

        private final ImageView icon;
        private final TextView label;

        private final View.OnClickListener listener;

        public ItemHolder(ViewGroup source, LinearLayout item) {
            super(item);

            this.window = new CourseWindow(source);

            window.setElevation(25);

            this.icon = item.findViewById(R.id.item_icon);
            this.label = item.findViewById(R.id.item_label);

            this.listener = (view) -> window.showAtLocation(source, Gravity.CENTER, 0, -80);
        }

        public CourseWindow getCourseWindow() {
            return window;
        }

        public void setIcon(int resource) {
            icon.setImageResource(resource);
        }

        public void setLabel(String text) {
            label.setText(text);
        }

        public void setResponsive(boolean respond) {
            if (respond)
                itemView.setOnClickListener(listener);
            else
                itemView.setOnClickListener(null);
        }
    }
}
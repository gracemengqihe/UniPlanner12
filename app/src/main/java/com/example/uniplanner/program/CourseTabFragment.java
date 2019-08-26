package com.example.uniplanner.program;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.uniplanner.R;
import com.example.uniplanner.UniPlanner;
import com.example.uniplanner.program.structure.Course.CourseType;
import com.example.uniplanner.program.structure.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CourseTabFragment extends Fragment {

    private CourseType type;

    public CourseType getType() {
        if (type == null)
            this.type = CourseType.valueOf(getArguments().getString("type", "UNAVAILABLE"));

        return type;
    }

    public static CourseTabFragment of(long id, int program, CourseType type) {
        CourseTabFragment fragment = new CourseTabFragment();

        Bundle arguments = new Bundle();

        arguments.putLong("id", id);
        arguments.putInt("program", program);
        arguments.putString("type", type.name());

        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_course, container,false);

        UniPlanner planner = (UniPlanner) getActivity().getApplicationContext();

        long id = getArguments().getLong("id");
        int program = getArguments().getInt("program");

        CourseViewAdapter adapter = new CourseViewAdapter((CourseListActivity) getActivity(), getType(), planner.getProgram(program).getCourses(getType(),planner.getProfile(id)));

        RecyclerView recycler = view.findViewById(R.id.course_recycler);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        ((SearchView) view.findViewById(R.id.course_search)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return true;
            }
        });

        return view;
    }
}
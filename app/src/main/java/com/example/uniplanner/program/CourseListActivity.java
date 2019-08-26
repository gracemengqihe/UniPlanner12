package com.example.uniplanner.program;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.uniplanner.R;
import com.example.uniplanner.UniPlanner;
import com.example.uniplanner.persistence.AppDatabase;
import com.example.uniplanner.program.structure.Course;
import com.example.uniplanner.program.structure.Course.CourseType;
import com.example.uniplanner.program.structure.Program;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class CourseListActivity extends AppCompatActivity {

    public void onCancel() {
        setResult(RESULT_CANCELED);

        finish();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_course_list);

        long id = getIntent().getLongExtra("id", 0);
        int program = getIntent().getIntExtra("program", 3979);

        CoursePageAdapter adapter = new CoursePageAdapter(getSupportFragmentManager());

        adapter.addPage(CourseTabFragment.of(id, program, CourseType.CORE));
        adapter.addPage(CourseTabFragment.of(id, program, CourseType.ELECTIVE));
        adapter.addPage(CourseTabFragment.of(id, program, CourseType.GENERAL));

        ViewPager pager = findViewById(R.id.course_pager);

        pager.setAdapter(adapter);

        ((TabLayout) findViewById(R.id.course_tab)).setupWithViewPager(pager);

        findViewById(R.id.floating_close).setOnClickListener((view) -> onCancel());
    }

    public boolean onSelect(String course, CourseType type) {
        long id = getIntent().getLongExtra("id", 0);
        int program_id = getIntent().getIntExtra("program", 3979);

        Program program = ((UniPlanner) getApplicationContext()).getProgram(program_id);
        Course choice = program.getCourse(course);

        if (!choice.is(CourseType.GENERAL)) {
            if (choice.getLevel() == 1 && AppDatabase.get().getRepository().getSelections(id).stream().filter((selection) -> program.getCourse(selection.course).getLevel() == 1).count() >=10) {
                Toast.makeText(this, "You can only select maximum ten level 1 courses", Toast.LENGTH_SHORT).show();
                return false;
            }

        } else {
            if (AppDatabase.get().getRepository().countByType(id, type.name()) >= 2) {
                Toast.makeText(this, "You can only select maximum two GE courses", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (AppDatabase.get().getRepository().isTaking(id, course)){
            Toast.makeText(this, "You have already selected this course", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!choice.getPrerequisites().isEmpty() && choice.getPrerequisites().stream().noneMatch((set) -> set.stream().allMatch((prerequisite) -> AppDatabase.get().getRepository().isTaking(id, prerequisite)))) {
            Toast.makeText(this, "You have to complete the prerequisite", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!choice.isAvailable(getIntent().getIntExtra("term", 1))) {
            Toast.makeText(this, "This course is not available on this term", Toast.LENGTH_SHORT).show();
            return false;
        }

        setResult(RESULT_OK, new Intent().putExtra("course", course).putExtra("type", type.name()));

        ((ViewPager) findViewById(R.id.course_pager)).getAdapter().notifyDataSetChanged();

        finish();

        return true;
    }

    private class CoursePageAdapter extends FragmentPagerAdapter {

        private final List<CourseTabFragment> fragments;

        public CoursePageAdapter(FragmentManager manager) {
            super(manager);
            this.fragments = new ArrayList<>();
        }

        public void addPage(CourseTabFragment page) {
            fragments.add(page);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).getType().title();
        }

        public void removePage(int position) {
            fragments.remove(position);
        }
    }
}

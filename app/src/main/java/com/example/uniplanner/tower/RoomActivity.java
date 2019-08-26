package com.example.uniplanner.tower;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniplanner.R;
import com.example.uniplanner.collection.CollectionActivity;
import com.example.uniplanner.persistence.AppDatabase;
import com.example.uniplanner.persistence.CourseSelection;
import com.example.uniplanner.program.CourseListActivity;

import androidx.appcompat.app.AppCompatActivity;

public class RoomActivity extends AppCompatActivity {

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        if (result != RESULT_OK)
            return;

        String course = data.getStringExtra("course");
        String type = data.getStringExtra("type");

        long id = getIntent().getLongExtra("id", 0);
        int year = getIntent().getIntExtra("year", 1);
        int term = getIntent().getIntExtra("term", 1);

        AppDatabase.get().getRepository().insert(new CourseSelection(id, year, term, request, course, type));

        setChest(id, year, term, request, true);

        if (AppDatabase.get().getRepository().isTaking(id, course))
            Toast.makeText(RoomActivity.this, "Congratulations! You received an item in your bag.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_room);

        long id = getIntent().getLongExtra("id", 0);
        int program = getIntent().getIntExtra("program", 0);
        int year = getIntent().getIntExtra("year", 1);
        int term = getIntent().getIntExtra("term", 1);

        ((TextView) findViewById(R.id.room)).setText("Year " + year + " - Term " + term);

        findViewById(R.id.chest_a).setOnClickListener(view -> openChest(id, program, year, term, 1));
        findViewById(R.id.chest_b).setOnClickListener(view -> openChest(id, program, year, term, 2));
        findViewById(R.id.chest_c).setOnClickListener(view -> openChest(id, program, year, term, 3));

        onChange(id, year, term);

        AppDatabase.get().getAccess().getSelectionsAsync(id).observe(this, (selections) -> onChange(id, year, term));

        findViewById(R.id.floating_bag).setOnClickListener((view) -> startActivity(new Intent(this, CollectionActivity.class).putExtra("id", id)));
    }

    public void onChange(long id, int year, int term) {
        for (int choice = 0; choice < 3; choice++)
            setChest(id, year, term, choice + 1, AppDatabase.get().getRepository().hasSelected(id, year, term, choice + 1));
    }

    public void openChest(long id, int program, int year, int term, int choice) {
        Intent intent = new Intent(this, CourseListActivity.class);

        intent.putExtra("id", id);
        intent.putExtra("program", program);
        intent.putExtra("year", year);
        intent.putExtra("term", term);
        intent.putExtra("choice", choice);

        startActivityForResult(intent, choice);
    }

    public void setChest(long id, int year, int term, int choice, boolean selected) {
        String image = (selected) ? "chest_opened" : "chest_closed";
        String course = (selected) ? AppDatabase.get().getRepository().getSelection(id, year, term, choice).course : "";

        int chest = new int[] { R.id.chest_a, R.id.chest_b, R.id.chest_c }[choice - 1];
        int label = new int[] { R.id.chest_label_a, R.id.chest_label_b, R.id.chest_label_c }[choice - 1];

        findViewById(chest).setBackgroundResource(getResources().getIdentifier(image, "drawable", getPackageName()));

        ((TextView) findViewById(label)).setText(course);
    }
}
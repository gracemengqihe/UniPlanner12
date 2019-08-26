package com.example.uniplanner.tower;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.uniplanner.R;
import com.example.uniplanner.collection.CollectionActivity;

import androidx.appcompat.app.AppCompatActivity;

public class LevelActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_level);

        long id = getIntent().getLongExtra("id", 0);
        int program = getIntent().getIntExtra("program", 0);
        int year = getIntent().getIntExtra("year", 1);

        ((TextView) findViewById(R.id.level)).setText("Year " + year);

        findViewById(R.id.room_1).setOnClickListener((view) -> enterRoom(id, program, year, 1));
        findViewById(R.id.room_2).setOnClickListener((view) -> enterRoom(id, program, year, 2));
        findViewById(R.id.room_3).setOnClickListener((view) -> enterRoom(id, program, year, 3));

        findViewById(R.id.floating_bag).setOnClickListener((view) -> startActivity(new Intent(this, CollectionActivity.class).putExtra("id", id)));
    }

    public void enterRoom(long id, int program, int year, int term) {
        Intent intent = new Intent(this, RoomActivity.class);

        intent.putExtra("id", id);
        intent.putExtra("program", program);
        intent.putExtra("year", year);
        intent.putExtra("term", term);

        startActivity(intent);
    }
}
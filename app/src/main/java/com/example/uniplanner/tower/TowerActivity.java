package com.example.uniplanner.tower;

import android.content.Intent;
import android.os.Bundle;

import com.example.uniplanner.R;
import com.example.uniplanner.collection.CollectionActivity;

import androidx.appcompat.app.AppCompatActivity;

public class TowerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tower);

        findViewById(R.id.level_1).setOnClickListener((view) -> reachLevel(1));
        findViewById(R.id.level_2).setOnClickListener((view) -> reachLevel(2));
        findViewById(R.id.level_3).setOnClickListener((view) -> reachLevel(3));

        findViewById(R.id.floating_bag).setOnClickListener((view) -> startActivity(new Intent(this, CollectionActivity.class).putExtra("id", Long.valueOf(R.string.dummy_id))));
    }

    public void reachLevel(int year) {
        Intent intent = new Intent(this, LevelActivity.class);

        intent.putExtra("id", Long.valueOf(R.string.dummy_id));
        intent.putExtra("program", Integer.valueOf(R.string.mvp_program));
        intent.putExtra("year", year);

        startActivity(intent);
    }
}

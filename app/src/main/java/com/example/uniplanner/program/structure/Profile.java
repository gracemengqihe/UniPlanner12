package com.example.uniplanner.program.structure;

import com.example.uniplanner.UniPlanner;
import com.example.uniplanner.persistence.CourseSelection;
import com.example.uniplanner.persistence.AppDatabase;

public class Profile {

    private final UniPlanner planner;

    public long id;

    public Profile(UniPlanner planner, long id) {
        this.planner = planner;
        this.id = id;
    }

    public long getID() {
        return id;
    }
}
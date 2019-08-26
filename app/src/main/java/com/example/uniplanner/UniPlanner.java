package com.example.uniplanner;

import android.app.Application;

import com.example.uniplanner.persistence.AppDatabase;
import com.example.uniplanner.program.structure.Profile;
import com.example.uniplanner.program.structure.Program;
import com.example.uniplanner.program.structure.ProgramLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UniPlanner extends Application {

    private Map<Integer, Program> programs;
    private Map<Long, Profile> profiles;

    public Profile getProfile(long id) {
        if (profiles == null)
            throw new IllegalStateException();

        return profiles.computeIfAbsent(id, (profile) -> new Profile(this, id));
    }

    public Set<Profile> getProfiles() {
        if (profiles == null)
            throw new IllegalStateException();

        return Collections.unmodifiableSet(new HashSet<>(profiles.values()));
    }

    public Program getProgram(int id) {
        if (programs == null)
            throw new IllegalStateException();

        return programs.get(id);
    }

    public Set<Program> getPrograms() {
        if (programs == null)
            throw new IllegalStateException();

        return Collections.unmodifiableSet(new HashSet<>(programs.values()));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppDatabase.get(this);

        this.programs = new HashMap<>();
        this.profiles = new HashMap<>();

        try {
            this.programs.put(R.string.mvp_program, ProgramLoader.load(new JSONObject(new BufferedReader(new InputStreamReader(getAssets().open("program_3979.json"))).lines().collect(Collectors.joining()))));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
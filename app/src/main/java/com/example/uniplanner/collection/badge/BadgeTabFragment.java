package com.example.uniplanner.collection.badge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniplanner.R;
import com.example.uniplanner.UniPlanner;
import com.example.uniplanner.persistence.AppDatabase;

public class BadgeTabFragment extends Fragment {

    public static BadgeTabFragment of(long id) {
        BadgeTabFragment fragment = new BadgeTabFragment();

        Bundle arguments = new Bundle();

        arguments.putLong("id", id);

        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        long id = getArguments().getLong("id", 0);

        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_recycler, container,false);

        BadgeViewAdapter adapter = new BadgeViewAdapter((UniPlanner) getActivity().getApplicationContext(), id, 3);

        view.setAdapter(adapter);
        view.setLayoutManager(new GridLayoutManager(getContext(), 3));

        AppDatabase.get().getAccess().getSelectionsAsync(id).observe(this, (selections) -> adapter.notifyDataSetChanged());

        return view;
    }
}

package com.example.uniplanner.collection.badge;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.uniplanner.R;
import com.example.uniplanner.UniPlanner;
import com.example.uniplanner.persistence.AppDatabase;

public class BadgeViewAdapter extends Adapter<BadgeViewAdapter.BadgeHolder> {

    private final UniPlanner planner;

    private final long id;

    private final int badges;

    public BadgeViewAdapter(UniPlanner planner, long id, int badges) {
        this.planner = planner;
        this.id = id;
        this.badges = badges;
    }

    @Override
    public int getItemCount() {
        return badges;
    }

    @Override
    public void onBindViewHolder(BadgeHolder holder, int position) {
        int taking = AppDatabase.get().getRepository().count(id);

        String badge = "g_badge";

        switch (position) {
            case 0:
                if (taking >= 9)
                    badge = "badge" + (position + 1);
                break;

            case 1:
                if (taking >= 18)
                    badge = "badge" + (position + 1);
                break;

            case 2:
                if (taking >= 24)
                    badge = "badge" + (position + 1);
                break;
        }

        holder.setIcon(planner.getResources().getIdentifier(badge, "drawable", planner.getPackageName()));
    }

    @Override
    public BadgeHolder onCreateViewHolder(ViewGroup group, int type) {
        return new BadgeHolder((LinearLayout) LayoutInflater.from(planner).inflate(R.layout.recycler_badge, group, false));
    }

    public static class BadgeHolder extends ViewHolder {

        private final ImageView icon;
        private final TextView label;

        public BadgeHolder(LinearLayout badge) {
            super(badge);
            this.icon = badge.findViewById(R.id.badge_icon);
            this.label = badge.findViewById(R.id.badge_label);
        }

        public void setIcon(int resource) {
            icon.setImageResource(resource);
        }

        public void setLabel(String text) {
            label.setText(text);
        }
    }
}
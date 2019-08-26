package com.example.uniplanner.collection;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.uniplanner.R;
import com.example.uniplanner.collection.badge.BadgeTabFragment;
import com.example.uniplanner.collection.item.ItemTabFragment;
import com.google.android.material.tabs.TabLayout;

public class CollectionActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_collection);

        CollectionPageAdapter adapter = new CollectionPageAdapter(getSupportFragmentManager());

        adapter.setItemTab(ItemTabFragment.of(getIntent().getLongExtra("id", 0)));
        adapter.setBadgeTab(BadgeTabFragment.of(getIntent().getLongExtra("id", 0)));

        ViewPager pager = (ViewPager) findViewById(R.id.collection_pager);

        pager.setAdapter(adapter);

        ((TabLayout) findViewById(R.id.collection_tab)).setupWithViewPager(pager);
    }

    private class CollectionPageAdapter extends FragmentPagerAdapter {

        private ItemTabFragment items;
        private BadgeTabFragment badges;

        public CollectionPageAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            return (position == 0) ? items : badges;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (position == 0) ? "Items" : "Badges";
        }

        public void setBadgeTab(BadgeTabFragment badges) {
            this.badges = badges;
        }

        public void setItemTab(ItemTabFragment items) {
            this.items = items;
        }
    }
}

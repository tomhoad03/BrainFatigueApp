package com.example.brainfatigueapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewStateAdapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> fragmentTitle = new ArrayList<>();

    public ViewStateAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lifecycle) {
        super(fm, lifecycle);
    }

    @NonNull
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentArrayList.add(fragment);
        fragmentTitle.add(title);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        // Needs to be updated...
        if (position == 0) {
            return new DashboardLeftFrag();
        }
        return new DashboardRightFrag();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

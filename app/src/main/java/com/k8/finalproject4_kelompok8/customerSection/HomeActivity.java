package com.k8.finalproject4_kelompok8.customerSection;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.customerSection.fragments.BookingsFragment;
import com.k8.finalproject4_kelompok8.customerSection.fragments.ExploreFragment;
import com.k8.finalproject4_kelompok8.customerSection.fragments.HomeFragment;
import com.k8.finalproject4_kelompok8.customerSection.fragments.SettingsFragment;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav;

    HomeFragment homeFragment;
    ExploreFragment exploreFragment;
    SettingsFragment settingsFragment;
    BookingsFragment bookingsFragment;

    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottom_nav= findViewById(R.id.nav_bottom);

        homeFragment= new HomeFragment();
        exploreFragment= new ExploreFragment();
        settingsFragment= new SettingsFragment();
        bookingsFragment= new BookingsFragment();

        currentFragment=homeFragment;

        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,currentFragment).commit();

        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if(item.getItemId()== R.id.nav_home){
                            changeFragment(homeFragment);
                            return true;
                        }
                        else if(item.getItemId()== R.id.nav_explore){
                            changeFragment(exploreFragment);
                            return true;
                        }
                        else if(item.getItemId()== R.id.nav_bookings){
                            changeFragment(bookingsFragment);
                            return true;
                        }
                        else if(item.getItemId()== R.id.nav_settings){
                            changeFragment(settingsFragment);
                            return true;
                        }
                        return false;
                    }
                };
        bottom_nav.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    private void changeFragment(Fragment fragment) {
        if (fragment == currentFragment) {
            return;
        }
        getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
        if (fragment.isAdded())
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        else
            getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, fragment, "fragments").commit();
        currentFragment = fragment;
    }
}
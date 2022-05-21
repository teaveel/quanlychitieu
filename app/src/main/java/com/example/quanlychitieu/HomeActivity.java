package com.example.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.os.Bundle;

import com.example.quanlychitieu.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity {

//    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    WalletFragment walletFragment = new WalletFragment();
    CalendarFragment calendarFragment = new CalendarFragment();
    ChartFragment chartFragment = new ChartFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        setFragment(walletFragment);
//        binding
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.walletTab:
                    setFragment(walletFragment);
                    break;
                case R.id.calendarTab:
                    setFragment(calendarFragment);
                    break;
                case R.id.chartTab:
                    setFragment(chartFragment);
                    break;
                case R.id.profileTab:
                    setFragment(profileFragment);
                    break;
            }
            return true;
        });
    }
    

    private void setFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
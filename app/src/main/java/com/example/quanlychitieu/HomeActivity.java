package com.example.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.walletTab);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder()

    }
    
    WalletFragment walletFragment = new WalletFragment();
    CalendarFragment calendarFragment = new CalendarFragment();
    ChartFragment chartFragment = new ChartFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.walletTab:
//                getSupportFragmentManager().beginTransaction().replace(R.id.cont, walletFragment).commit();
                return true;

            case R.id.calendarTab:
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, calendarFragment).commit();
                return true;

            case R.id.chartTab:
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, chartFragment).commit();
                return true;
        }
        return false;
    }
}
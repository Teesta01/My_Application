package com.example.myapplication;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigation;
    private View parent_view;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());
        navigation = findViewById(R.id.navigation);
        parent_view = findViewById(R.id.main_layout);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.navigation_search:
                        fragment = new SearchFragment();
                        break;

                    case R.id.navigation_cart:
                        fragment = new HomeFragment();
                        break;

                    case R.id.navigation_profile:
                        SelleLoginFragment s = new SelleLoginFragment();
                        SharedPreferences mPrefs = getSharedPreferences(s.ID_VALUE,0);
                        String str = mPrefs.getString(s.KEY, "");
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                        if (str.equals("seller")) {
                            fragment = new HomeFragment();
                        } else {
                            fragment = new LoginOrSignUp();
                        }

                        break;
                    case  R.id.navigation_more:
                        fragment = new HomeFragment();
                        break;
                }

                return loadFragment(fragment);
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        loadFragment(new HomeFragment());
        Toast.makeText(this, "please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce= false;
            }
        },2000);
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}

package com.example.daeding_jikding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Fragment home;
    Fragment find_circle;
    Fragment chat;
    Fragment setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = new home();
        find_circle = new find_circle();
        chat = new chat();
        setting = new setting();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, home).commit();
        //초기화면 설정


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()){
                        case R.id.tab_home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, home).commit();
                            return true;

                        case R.id.tab_find_circle:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, find_circle).commit();
                            return true;

                        case R.id.tab_chat:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, chat).commit();
                            return true;

                        case R.id.tab_setting:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, setting).commit();
                            return true;
                    }
                    return false;
                });

    }
}
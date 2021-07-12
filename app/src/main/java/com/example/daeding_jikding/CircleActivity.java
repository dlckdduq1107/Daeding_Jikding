package com.example.daeding_jikding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CircleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Circle c = (Circle)intent.getSerializableExtra("circle");

        Log.d("ssdf", c.getCircle_name());
    }
}

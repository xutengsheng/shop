package com.xts.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xts.shop.ui.fragment.MyFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl,new MyFragment())
                .commit();
    }
}

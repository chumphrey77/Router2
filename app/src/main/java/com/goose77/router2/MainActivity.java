package com.goose77.router2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.goose77.router2.support.Bootloader;

/**
 * Creates the bootloader objects and begins the bootup process of the router
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Bootloader(this);
    }
}

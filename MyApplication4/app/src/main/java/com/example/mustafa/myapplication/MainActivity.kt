package com.example.mustafa.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    public void openTeaList(View v);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
     void openTeaList(View v){
        Intent i = new Intent(this,Tea.class);
        startActivity(i);

    }
}

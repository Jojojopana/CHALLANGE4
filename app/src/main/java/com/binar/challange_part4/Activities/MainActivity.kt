package com.binar.challange_part4.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.challange_part4.R
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    companion object{
        const val SHARED_PREFERENCES = "sharedpreferences"
    }
}
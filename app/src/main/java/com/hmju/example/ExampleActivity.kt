package com.hmju.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment,ExampleFragment())
            commit()
        }
    }
}
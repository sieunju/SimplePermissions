package com.hmju.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, ExampleFragment())
            commit()
        }
    }
}
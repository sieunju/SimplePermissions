package com.hmju.example

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.hmju.permissions.SimplePermissions

class ExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        findViewById<Button>(R.id.button1).setOnClickListener {
            SimplePermissions(this)
                    .requestPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).build { isAllGranted, negativePermissions ->
                        finish()
                        startActivity(Intent(this,ExampleActivity::class.java))
                    }
        }
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragment,ExampleFragment())
//            commit()
//        }
    }
}
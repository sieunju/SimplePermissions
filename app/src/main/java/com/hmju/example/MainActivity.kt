package com.hmju.example

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.hmju.permissions.SimplePermissions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

		findViewById<Button>(R.id.permissionsButton).setOnClickListener {
			SimplePermissions(this)
				.requestPermissions(
					Manifest.permission.READ_PHONE_STATE,
					Manifest.permission.CAMERA
				)
				.build()
		}
    }
}
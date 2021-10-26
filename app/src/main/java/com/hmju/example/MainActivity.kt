package com.hmju.example

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.hmju.permissions.SimplePermissions
import com.hmju.permissions.model.PermissionsDialogUiModel
import com.hmju.permissions.ui.PermissionsDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button1).setOnClickListener {
            SimplePermissions(this)
                .requestPermissions(
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CAMERA
                )
                .build()
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            val uiModel = PermissionsDialogUiModel(
                dialogBg = R.drawable.bg_permissions_dialog
            )
            PermissionsDialog(this, uiModel)
                .setTitle("안녕하세요~~~~~")
                .setContents("내용입니다~..\n내용입니다.2222")
                .setPositiveButton("확인")
                .setNegativeButton("취소")
                .show {
                    Log.d("Permissions", "onClick $it")
                }
        }
    }
}
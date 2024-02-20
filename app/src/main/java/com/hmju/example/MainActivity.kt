package com.hmju.example

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import hmju.permissions.core.SPermissions
import hmju.permissions.dialog.SimplePermissions
import hmju.permissions.dialog.model.PermissionsDialogUiModel

internal class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button1).setOnClickListener {
            SimplePermissions(this)
                .addPermission(Manifest.permission.READ_PHONE_STATE)
                .addPermission(Manifest.permission.CAMERA)
                .addPermission(Manifest.permission.RECORD_AUDIO)
                .addPermission(Manifest.permission.BLUETOOTH)
                .negativeDialogContents("권한이 거부되었습니다.\n해당 서비스를 사용하실수 없습니다.")
                .negativeDialogLeftButton("취소")
                .negativeDialogRightButton("설정")
                .negativeDialogUiConfig(
                    PermissionsDialogUiModel(
                        buttonPositiveBgColor = R.color.pink,
                        buttonNegativeBgColor = R.color.gray
                    )
                )
                .build { isAllGranted, resultPermissionMap ->
                    Log.d(
                        "JLogger",
                        "모두 권한 승인 $isAllGranted\t결과값 권한 ${resultPermissionMap}"
                    )
                }
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            SPermissions(this)
                .addPermission(Manifest.permission.READ_PHONE_STATE)
                .addPermission(Manifest.permission.CAMERA)
                .addPermission(Manifest.permission.RECORD_AUDIO)
                .addPermission(Manifest.permission.BLUETOOTH)
                .build { b, map ->

                }
        }

        findViewById<Button>(R.id.button3).setOnClickListener {
            startActivity(Intent(this, ExampleActivity::class.java))
        }
    }
}
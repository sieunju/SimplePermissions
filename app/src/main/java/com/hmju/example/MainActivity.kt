package com.hmju.example

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hmju.permissions.SimplePermissions
import com.hmju.permissions.model.PermissionsDialogUiModel
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button1).setOnClickListener {
            SimplePermissions(this)
                .requestPermissions(
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.BLUETOOTH
                )
                .negativeDialogContents("권한이 거부되었습니다.\n해당 서비스를 사용하실수 없습니다.")
                .negativeDialogLeftButton("취소")
                .negativeDialogRightButton("설정")
                .negativeDialogUiConfig(
                    PermissionsDialogUiModel(
                        buttonPositiveBgColor = R.color.pink,
                        buttonNegativeBgColor = R.color.gray
                    )
                )
                .build { isAllGranted, negativePermissions ->
                    Log.d(
                        "JLogger",
                        "모두 권한 승인 $isAllGranted\t거부된 권한 ${Arrays.deepToString(negativePermissions)}"
                    )
                }
        }

        findViewById<Button>(R.id.button3).setOnClickListener {
            startActivity(Intent(this, ExampleActivity::class.java))
        }
    }
}
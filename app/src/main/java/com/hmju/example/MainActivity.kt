package com.hmju.example

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.hmju.permissions.SimplePermissions
import com.hmju.permissions.model.PermissionsDialogUiModel
import com.hmju.permissions.ui.PermissionsDialog
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
                    .negativeDialogPermissionsSetting(PermissionsDialog.POSITIVE)
                    .build { isAllGranted, negativePermissions ->
                        Log.d("MainActivity", "모두 승인 $isAllGranted  거부된 권한 ${Arrays.deepToString(negativePermissions)}")
                    }
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

        findViewById<Button>(R.id.button3).setOnClickListener {
            startActivity(Intent(this, ExampleActivity::class.java))
        }
    }
}
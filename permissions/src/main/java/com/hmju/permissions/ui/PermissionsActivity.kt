package com.hmju.permissions.ui

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.hmju.permissions.ExtraCode
import com.hmju.permissions.PermissionsListener
import com.hmju.permissions.R

/**
 * Description : 권한 Activity
 *
 * Created by hmju on 2021-10-25
 */
class PermissionsActivity : AppCompatActivity() {

    companion object {
        var listener: PermissionsListener? = null
    }

    private val permissionsResult = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        finish()
        listener?.onResult(permissions)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.permissions, R.anim.permissions)
        super.onCreate(savedInstanceState)
        intent.getStringArrayExtra(ExtraCode.PERMISSIONS)?.let {
            permissionsResult.launch(it)
        } ?: run {
            finish()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.permissions, R.anim.permissions)
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }
}
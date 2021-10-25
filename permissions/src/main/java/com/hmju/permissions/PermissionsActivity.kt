package com.hmju.permissions

import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

/**
 * Description : 권한 Activity
 *
 * Created by hmju on 2021-10-25
 */
class PermissionsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PermissionsActivity"
        private const val DEBUG = true
        fun LogD(msg: String) {
            if (DEBUG) {
                Log.d(TAG, msg)
            }
        }

        var listener: PermissionsListener? = null
    }

    private val permissionsResult = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        finish()
        listener?.onResult(permissions)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        intent.getStringArrayExtra(ExtraCode.PERMISSIONS)?.let {
            permissionsResult.launch(it)
        } ?: run {
            finish()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogD("onDestroy Before $listener")
        listener = null
        LogD("onDestroy After $listener")
    }
}
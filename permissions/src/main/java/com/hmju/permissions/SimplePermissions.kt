package com.hmju.permissions

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * Description : 간단하게 권한에 대해 처리할수 있는 SimplePermissions Class
 *
 * Created by hmju on 2021-10-25
 */
class SimplePermissions(private val context: Context) {

    companion object {
        private const val TAG = "SimplePermissions"
        private const val DEBUG = true
        fun LogD(msg: String) {
            if (DEBUG) {
                Log.d(TAG, msg)
            }
        }
    }

    init {
        // TODO Application Context 인지 검사
    }

    private var requestPermissions: Array<String>? = null
    private var negativeDialogTitle: String? = null
    private var negativeDialogContent: String? = null
    private var negativeLeftButton: String? = null
    private var negativeRightButton: String? = null

    fun requestPermissions(vararg permissions: String): SimplePermissions {
        this.requestPermissions = Array(permissions.size) { idx ->
            permissions[idx]
        }
        return this
    }

    fun negativeDialogTitle(@StringRes id: Int): SimplePermissions {
        if (id == -1) return this
        negativeDialogTitle = context.getString(id)
        return this
    }

    fun negativeDialogTitle(title: String): SimplePermissions {
        negativeDialogTitle = title
        return this
    }

    fun negativeDialogContents(@StringRes id : Int): SimplePermissions {
        if (id == -1) return this
        negativeDialogContent = context.getString(id)
        return this
    }

    fun negativeDialogContents(contents: String): SimplePermissions {
        negativeDialogContent = contents
        return this
    }

    fun build() {
        if (requestPermissions == null) {
            throw NullPointerException("권한은 필수 값입니다.")
        }

        val extraData = PermissionsExtra(
            this.requestPermissions!!
        )

        if (context is Activity) {
            Intent(context,PermissionsActivity::class.java).apply {
                putExtra(ExtraCode.PERMISSIONS,requestPermissions)
                context.startActivity(this)
            }
            PermissionsActivity.listener = object : PermissionsListener {
                override fun onResult(permissions: Map<String, Boolean>) {
                    LogD("Callback Result $permissions")
                }
            }
        } else if (context is Fragment) {

        } else {
            throw IllegalArgumentException("Not Application Context.. Activity or Fragment Context ")
        }
    }
}
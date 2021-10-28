package com.hmju.permissions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.annotation.IntRange
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.hmju.permissions.model.PermissionsDialogUiModel
import com.hmju.permissions.model.PermissionsExtra
import com.hmju.permissions.ui.PermissionsActivity
import com.hmju.permissions.ui.PermissionsDialog

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

    private var requestPermissions: Array<String>? = null
    private var negativeDialogTitle: String? = null
    private var negativeDialogContents: String? = null
    private var negativeDialogLeftButtonTxt: String? = null
    private var negativeDialogRightButtonTxt: String? = null
    private var negativeDialogPermissionsSettingWhich: Int = -1

    private var dialogConfig: PermissionsDialogUiModel? = null

    fun requestPermissions(vararg permissions: String): SimplePermissions {
        this.requestPermissions = Array(permissions.size) { idx ->
            permissions[idx]
        }
        return this
    }

    private fun initDialogConfig() {
        if (dialogConfig == null) {
            dialogConfig = PermissionsDialogUiModel()
        }
    }

    /**
     * 권한 거부시 나타내는 팝업 제목 설정
     * @param id String Resource Id
     */
    fun negativeDialogTitle(@StringRes id: Int): SimplePermissions {
        if (id == -1) return this
        return negativeDialogTitle(context.getString(id))
    }

    /**
     * 권한 거부시 나타내는 팝업 제목 설정
     * @param text 문자열
     */
    fun negativeDialogTitle(text: String): SimplePermissions {
        negativeDialogTitle = text
        return this
    }


    /**
     * 권한 거부시 나타내는 팝업 내용 설정
     * @param id String Resource Id
     */
    fun negativeDialogContents(@StringRes id: Int): SimplePermissions {
        if (id == -1) return this
        return negativeDialogContents(context.getString(id))
    }

    /**
     * 권한 거부시 나타내는 팝업 내용 설정
     * @param text 문자열
     */
    fun negativeDialogContents(text: String): SimplePermissions {
        negativeDialogContents = text
        return this
    }

    /**
     * 권한 거부시 나타내는 팝업 왼쪽 버튼 문자열 설정
     * @param id String Resource Id
     */
    fun negativeDialogLeftButton(@StringRes id: Int): SimplePermissions {
        if (id == -1) return this
        return negativeDialogLeftButton(context.getString(id))
    }

    /**
     * 권한 거부시 나타내는 팝업 왼쪽 버튼 문자열 설정
     * @param text 문자열
     */
    fun negativeDialogLeftButton(text: String): SimplePermissions {
        negativeDialogLeftButtonTxt = text
        return this
    }

    /**
     * 권한 거부시 나타내는 팝업 오른쪽 버튼 문자열 설정
     * @param id String Resource Id
     */
    fun negativeDialogRightButton(@StringRes id: Int): SimplePermissions {
        if (id == -1) return this
        return negativeDialogRightButton(context.getString(id))
    }

    /**
     * 권한 거부시 나타내는 팝업 오른쪽 버튼 문자열 설정
     * @param text 문자열
     */
    fun negativeDialogRightButton(text: String): SimplePermissions {
        negativeDialogRightButtonTxt = text
        return this
    }

    /**
     * 권한 거부시 나타내는 팝업 UI 설정 처리 함수
     * @param config PermissionsDialogUiModel
     */
    fun negativeDialogUiConfig(config: PermissionsDialogUiModel): SimplePermissions {
        dialogConfig = config
        return this
    }

    fun negativeDialogPermissionsSetting(@IntRange(from = 1, to = 2) which: Int): SimplePermissions {
        negativeDialogPermissionsSettingWhich = which
        return this
    }

    private fun movePermissionsSetting(context: Context) {
        try {
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:${context.packageName}")).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        } catch (ex: Exception) {
            LogD("Error $ex")
        }
    }

    /**
     * Build
     * @param callback {전부다 승인한 경우 true, 아닌경우 false}, 거부된 권한들
     */
    fun build(callback: (Boolean, Array<String>) -> Unit) {
        if (requestPermissions == null) {
            throw NullPointerException("권한은 필수 값입니다.")
        }

        if (context is Activity) {
            Intent(context, PermissionsActivity::class.java).apply {
                putExtra(ExtraCode.PERMISSIONS, requestPermissions)
                context.startActivity(this)
            }
            val negativeList = ArrayList<String>()
            PermissionsActivity.listener = object : PermissionsListener {
                override fun onResult(permissions: Map<String, Boolean>) {
                    LogD("Callback Result $permissions")
                    for (key in permissions.keys) {
                        if (permissions[key] == false) {
                            negativeList.add(key)
                        }
                    }
                    
                    val isAllGranted = negativeList.size == 0

                    // 모두 승인인 경우
                    if(isAllGranted) {
                        callback(isAllGranted, negativeList.toTypedArray())
                    } else {
                        // 권한 거부시 나타내는 팝업 제목 or 내용 둘중하나라도 값이 있는 경우
                        if (!negativeDialogTitle.isNullOrEmpty() || !negativeDialogContents.isNullOrEmpty()) {
                            PermissionsDialog(context, if (dialogConfig == null) PermissionsDialogUiModel() else dialogConfig!!)
                                    .setTitle(negativeDialogTitle)
                                    .setContents(negativeDialogContents)
                                    .setNegativeButton(negativeDialogLeftButtonTxt)
                                    .setPositiveButton(negativeDialogRightButtonTxt)
                                    .show { which ->
                                        if (negativeDialogPermissionsSettingWhich == -1) {
                                            callback(isAllGranted, negativeList.toTypedArray())
                                        } else if (which == negativeDialogPermissionsSettingWhich) {
                                            callback(isAllGranted, negativeList.toTypedArray())
                                            movePermissionsSetting(context)
                                        } else {
                                            callback(isAllGranted, negativeList.toTypedArray())
                                        }
                                    }
                        } else {
                            callback(isAllGranted, negativeList.toTypedArray())
                        }
                    }
                }
            }
        } else {
            throw IllegalArgumentException("Not Application Context.. Activity Context ")
        }
    }
}
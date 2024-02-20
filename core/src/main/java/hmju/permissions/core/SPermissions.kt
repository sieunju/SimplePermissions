package hmju.permissions.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import hmju.permissions.core.internal.ExtraCode
import hmju.permissions.core.internal.PermissionsActivity
import hmju.permissions.core.internal.PermissionsListener

/**
 * Description : 간단하게 권한에 대해 처리할수 있는 SPermissions Class
 *
 * Created by juhongmin on 2022/12/06
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
open class SPermissions {
    protected val context: Context

    constructor(context: Context) {
        this.context = context
    }

    constructor(fragment: Fragment) {
        this.context = fragment.requireContext()
    }

    constructor(activity: Activity) {
        this.context = activity.baseContext
    }

    companion object {
        internal var listener: PermissionsListener? = null
    }

    protected val requestPermissions: MutableSet<String> = mutableSetOf()

    open fun addPermission(permission: String): SPermissions {
        requestPermissions.add(permission)
        return this
    }

    /**
     * Request Permissions
     * @param permissions Manifest.CAMERA, Manifest.STORAGE ...
     */
    open fun addPermissions(permissions: List<String>): SPermissions {
        requestPermissions.addAll(permissions)
        return this
    }

    /**
     * 껍데기인 권한 액티비티 실행 함수
     * @param negativePermissions 거부된 권한들
     */
    protected fun movePermissionsActivity(negativePermissions: List<String>) {
        Intent(context, PermissionsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(ExtraCode.PERMISSIONS, negativePermissions.toTypedArray())
            context.startActivity(this)
        }
    }

    /**
     * Build 이후 setListener
     */
    protected fun buildAndSetListener(callback: () -> Unit) {
        // Single Listener
        if (listener != null) listener = null

        listener = object : PermissionsListener {
            override fun onResult() {
                callback()
            }
        }
    }

    /**
     * Build
     * @param callback {전부다 승인한 경우 true, 아닌경우 false}, 요청한 권한에 대한 결과 값
     */
    open fun build(callback: (Boolean, Map<String, Boolean>) -> Unit) {
        if (requestPermissions.isEmpty()) throw NullPointerException("권한은 필수 값입니다.")

        // 권한 거부인것들만 권한 팝업 처리하도록
        val negativePermissions = requestPermissions
            .filter {
                ContextCompat.checkSelfPermission(
                    context,
                    it
                ) == PackageManager.PERMISSION_DENIED
            }

        if (negativePermissions.isEmpty()) {
            callback(true, mapOf())
        } else {
            movePermissionsActivity(negativePermissions)

            // Single Listener
            if (listener != null) listener = null

            listener = object : PermissionsListener {
                override fun onResult() {
                    val resultPermissions = hashMapOf<String, Boolean>()
                    var isAllGrated = true
                    requestPermissions.forEach { permission ->
                        val permissionGranted = ContextCompat.checkSelfPermission(
                            context,
                            permission
                        ) == PackageManager.PERMISSION_GRANTED
                        resultPermissions[permission] = permissionGranted
                        // 하나라도 거부된 경우
                        if (!permissionGranted) {
                            isAllGrated = false
                        }
                    }

                    callback(
                        isAllGrated,
                        resultPermissions
                    )
                    listener = null
                }
            }
        }
    }
}
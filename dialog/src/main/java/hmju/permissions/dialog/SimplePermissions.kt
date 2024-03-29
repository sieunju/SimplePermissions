package hmju.permissions.dialog

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import hmju.permissions.core.SPermissions
import hmju.permissions.dialog.model.PermissionsDialogModel
import hmju.permissions.dialog.model.PermissionsDialogUiModel

/**
 * Description : 간단하게 권한에 대해 처리할수 있는 SimplePermissions Class
 *
 * Created by hmju on 2021-10-25
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class SimplePermissions : SPermissions {

    constructor(context: Context) : super(context)
    constructor(fragment: Fragment) : super(fragment.requireContext())
    constructor(activity: Activity) : super(activity.baseContext)

    private var dialogModel: PermissionsDialogModel? = null
    private var dialogUiModel: PermissionsDialogUiModel? = null

    override fun addPermissions(permissions: List<String>): SimplePermissions {
        requestPermissions.addAll(permissions)
        return this
    }

    override fun addPermission(permission: String): SimplePermissions {
        requestPermissions.add(permission)
        return this
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
        initDialogConfig().title = text
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
        initDialogConfig().contents = text
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
        initDialogConfig().leftButton = text
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
        initDialogConfig().rightButton = text
        return this
    }

    /**
     * 권한 거부시 나타내는 팝업 UI 설정 처리 함수
     * @param config PermissionsDialogUiModel
     */
    fun negativeDialogUiConfig(config: PermissionsDialogUiModel): SimplePermissions {
        dialogUiModel = config
        return this
    }

    /**
     * Dialog Config Getter.
     */
    private fun initDialogConfig(): PermissionsDialogModel {
        if (dialogModel == null) {
            dialogModel = PermissionsDialogModel()
        }
        return dialogModel!!
    }

    /**
     * Build
     * @param callback {전부다 승인한 경우 true, 아닌경우 false}, 요청한 권한에 대한 결과 값
     */
    override fun build(callback: (Boolean, Map<String, Boolean>) -> Unit) {
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

            buildAndSetListener {
                val resultPermissionMap = hashMapOf<String, Boolean>()
                var isAllGrated = true
                requestPermissions.forEach { permission ->
                    val permissionGranted = ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                    resultPermissionMap[permission] = permissionGranted
                    // 하나라도 거부된 경우
                    if (!permissionGranted) {
                        isAllGrated = false
                    }
                }

                if (dialogModel == null) {
                    callback(
                        isAllGrated,
                        resultPermissionMap
                    )
                } else {
                    PermissionsDialog(context, dialogUiModel ?: PermissionsDialogUiModel())
                        .setTitle(dialogModel?.title)
                        .setContents(dialogModel?.contents)
                        .setNegativeButton(dialogModel?.leftButton)
                        .setPositiveButton(dialogModel?.rightButton)
                        .show {
                            callback(
                                isAllGrated,
                                resultPermissionMap
                            )
                        }
                }
            }
        }
    }
}
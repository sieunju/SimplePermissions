package com.hmju.permissions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.annotation.IntRange
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.hmju.permissions.model.PermissionsDialogModel
import com.hmju.permissions.model.PermissionsDialogUiModel
import com.hmju.permissions.ui.PermissionsActivity
import com.hmju.permissions.ui.PermissionsDialog

/**
 * Description : 간단하게 권한에 대해 처리할수 있는 SimplePermissions Class
 *
 * Created by hmju on 2021-10-25
 */
class SimplePermissions(private val context: Context) {

	companion object {
		internal var listener: PermissionsListener? = null
	}

	private var requestPermissions: Array<String>? = null

	private var dialogModel: PermissionsDialogModel? = null
	private var dialogUiModel: PermissionsDialogUiModel? = null

	fun requestPermissions(vararg permissions: String): SimplePermissions {
		this.requestPermissions = Array(permissions.size) { idx ->
			permissions[idx]
		}
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
	 * 권한 페이지 이동시 필요한 버튼 위치
	 * 왼쪽 오른쪽 버튼
	 */
	@Deprecated(
            message = "권한 페이지에서 허용 -> 거부 상태를 하면 리스너가 " +
                    "Null 로 변경이 되므로 권한 설정 페이지는 라이브러리에서 처리하는 것이 아닌 외부에서 직접적으로 " +
                    "처리해야 합니다.", level = DeprecationLevel.ERROR
    )
	fun negativeDialogPermissionsSetting(
            @IntRange(
                    from = 1,
                    to = 2
            ) which: Int
    ): SimplePermissions {
		initDialogConfig().apply {
			isSettingWhich = which
			packageName = context.packageName
		}
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
	 * @param callback {전부다 승인한 경우 true, 아닌경우 false}, 거부된 권한들
	 */
	fun build(callback: (Boolean, Array<String>) -> Unit) {
		if (requestPermissions == null) throw NullPointerException("권한은 필수 값입니다.")

		// 권한 거부인것들만 권한 팝업 처리하도록
		val negativePermissions = requestPermissions!!
				.filter {
					ContextCompat.checkSelfPermission(
                            context,
                            it
                    ) == PackageManager.PERMISSION_DENIED
				}

		if (negativePermissions.isEmpty()) {
			callback(true, emptyArray())
		} else {
			if (context is Activity) {
				Intent(context, PermissionsActivity::class.java).apply {
					putExtra(ExtraCode.PERMISSIONS, negativePermissions.toTypedArray())
					context.startActivity(this)
				}

				// Single Listener
				if (listener != null) listener = null

				listener = object : PermissionsListener {
					override fun onResult() {
						val resultNegativePermissions = requestPermissions!!.filter {
							ContextCompat.checkSelfPermission(
                                    context,
                                    it
                            ) == PackageManager.PERMISSION_DENIED
						}

						if (dialogModel == null) {
							callback(
                                    resultNegativePermissions.isEmpty(),
                                    resultNegativePermissions.toTypedArray()
                            )
						} else {
							PermissionsDialog(context, dialogUiModel ?: PermissionsDialogUiModel())
									.setTitle(dialogModel?.title)
									.setContents(dialogModel?.contents)
									.setNegativeButton(dialogModel?.leftButton)
									.setPositiveButton(dialogModel?.rightButton)
									.show {
										callback(
                                                resultNegativePermissions.isEmpty(),
                                                resultNegativePermissions.toTypedArray()
                                        )
									}
						}
					}
				}
			} else {
				throw IllegalArgumentException("Not Application Context.. Activity Context ")
			}
		}
	}
}
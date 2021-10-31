package com.hmju.permissions.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.hmju.permissions.ExtraCode
import com.hmju.permissions.SimplePermissions
import com.hmju.permissions.model.PermissionsDialogModel
import com.hmju.permissions.model.PermissionsDialogUiModel

/**
 * Description : 권한 Activity
 *
 * Created by hmju on 2021-10-25
 */
class PermissionsActivity : AppCompatActivity() {

	/**
	 * 권한 팝업 노출후 결과값 받는 부분
	 */
	private val permissionsResult = registerForActivityResult(
			ActivityResultContracts.RequestMultiplePermissions()
	) { _ ->
		handleCallback()
	}

	/**
	 * 권한 설정 페이지 진입후 결과값 리턴 받는 부분
	 */
	private val settingsResult = registerForActivityResult(
			ActivityResultContracts.StartActivityForResult()
	) { _ ->
		handleCallback()
	}

	private val requestPermissions : Array<String>? by lazy {
		intent.getStringArrayExtra(ExtraCode.PERMISSIONS)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		overridePendingTransition(0, 0)
		super.onCreate(savedInstanceState)
		permissionsResult.launch(requestPermissions)
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		// outState.putStringArray(ExtraCode.PERMISSIONS,requestPermissions)
	}

	/**
	 * 권한 결과에 맞게 처리하는 함수
	 * @param permissions 권한 결과에 대한 Result 값
	 */
	private fun handlePermissions(permissions: Map<String, Boolean>) {
		val negativeList = arrayListOf<String>()
		for (key in permissions.keys) {
			if (permissions[key] == false) {
				negativeList.add(key)
			}
		}

		val isAllGranted = negativeList.size == 0

		// 모두 승인한 상태인경우
		if (isAllGranted) {
			handleCallback()
		} else {
			// 거절시 다이얼로그 팝업
			intent.getParcelableExtra<PermissionsDialogModel>(ExtraCode.NEGATIVE_DIALOG)?.let { dialogModel ->
				PermissionsDialog(
						this,
						intent.getParcelableExtra(ExtraCode.NEGATIVE_UI_DIALOG)
								?: PermissionsDialogUiModel())
						.setTitle(dialogModel.title)
						.setContents(dialogModel.contents)
						.setNegativeButton(dialogModel.leftButton)
						.setPositiveButton(dialogModel.rightButton)
						.show { which ->
							// 설정 화면으로 이동.
							if (which == dialogModel.isSettingWhich) {
								if (!movePermissionsSetting(dialogModel.packageName)) {
									handleCallback()
								}
							} else {
								handleCallback()
							}
						}
			} ?: run {
				handleCallback()
			}
		}
	}

	/**
	 * SimplePermissions 클래스에 리스너 전달 처리 함수
	 */
	private fun handleCallback() {
		finish()
		Handler(Looper.getMainLooper()).post {
			SimplePermissions.listener?.onResult()
		}
	}

	/**
	 * 권한 페이지 이동
	 * @param packageName AppModule Package Name
	 * @return true 정상적으로 권한페이지로 이동처리된 경우, false 권한 페이지로 이동이 안된 경우
	 */
	private fun movePermissionsSetting(packageName: String?): Boolean {
		if (packageName.isNullOrEmpty()) return false

		return try {
			Intent(
					Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
					Uri.parse("package:${packageName}")
			).apply {
				addCategory(Intent.CATEGORY_DEFAULT)
				settingsResult.launch(this)
			}
			true
		} catch (ex: Exception) {
			false
		}
	}

	override fun finish() {
		super.finish()
		overridePendingTransition(0, 0)
	}
}
package com.hmju.permissions.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.hmju.permissions.ExtraCode
import com.hmju.permissions.SimplePermissions

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
	 * SimplePermissions 클래스에 리스너 전달 처리 함수
	 */
	private fun handleCallback() {
		finish()
		Handler(Looper.getMainLooper()).post {
			SimplePermissions.listener?.onResult()
		}
	}

	override fun finish() {
		super.finish()
		overridePendingTransition(0, 0)
	}
}
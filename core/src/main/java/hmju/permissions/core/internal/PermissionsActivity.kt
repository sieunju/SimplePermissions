package hmju.permissions.core.internal

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import hmju.permissions.core.SPermissions

/**
 * Description : 권한 Activity
 *
 * Created by hmju on 2021-10-25
 */
internal class PermissionsActivity : AppCompatActivity() {

    /**
     * 권한 팝업 노출후 결과값 받는 부분
     */
    private val permissionsResult = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { handleCallback() }

    private val requestPermissions: Array<String>? by lazy {
        intent.getStringArrayExtra(ExtraCode.PERMISSIONS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        permissionsResult.launch(requestPermissions)
    }

    /**
     * SimplePermissions 클래스에 리스너 전달 처리 함수
     */
    private fun handleCallback() {
        finish()
        Handler(Looper.getMainLooper()).post {
            SPermissions.listener?.onResult()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }
}
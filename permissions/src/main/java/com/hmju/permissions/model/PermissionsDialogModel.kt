package com.hmju.permissions.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Description : 권한 거부시 노출하는 팝업의 데이터 모델
 *
 * Created by juhongmin on 10/31/21
 */
@Parcelize
data class PermissionsDialogModel(
    var title: String? = null,
    var contents: String? = null,
    var leftButton: String? = null,
    var rightButton: String? = null,
    var isSettingWhich: Int = -1,
    var packageName : String? = null
) : Parcelable
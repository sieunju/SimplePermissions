package com.hmju.permissions.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Description : 권한 관련 데이터 모델
 *
 * Created by hmju on 2021-10-25
 */
@Parcelize
data class PermissionsExtra(
    val permissions : Array<String>,
    val negativeDialogTitle : String? = null,
    val negativeDialogContent : String? = null,
    val negativeLeftButton: String? = null,
    val negativeRightButton : String? = null
) : Parcelable {
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PermissionsExtra

        if (!permissions.contentEquals(other.permissions)) return false
        if (negativeDialogTitle != other.negativeDialogTitle) return false
        if (negativeDialogContent != other.negativeDialogContent) return false
        if (negativeLeftButton != other.negativeLeftButton) return false
        if (negativeRightButton != other.negativeRightButton) return false

        return true
    }

    override fun hashCode(): Int {
        var result = permissions.contentHashCode()
        result = 31 * result + (negativeDialogTitle?.hashCode() ?: 0)
        result = 31 * result + (negativeDialogContent?.hashCode() ?: 0)
        result = 31 * result + (negativeLeftButton?.hashCode() ?: 0)
        result = 31 * result + (negativeRightButton?.hashCode() ?: 0)
        return result
    }
}
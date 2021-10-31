package com.hmju.permissions.model

import android.os.Parcelable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.hmju.permissions.R
import com.hmju.permissions.extension.dp
import kotlinx.parcelize.Parcelize

/**
 * Description : 권한 거부시 나타내는 팝업 데이터 모델 클래스
 *
 * Created by hmju on 2021-10-26
 */

@Parcelize
data class PermissionsDialogUiModel(
		var titleTopMargin: Int = 20.dp,
		var titleSideMargin: Int = 20.dp,
		@ColorRes var titleTxtColor: Int = android.R.color.black,
		var titleTxtSize: Int = 20,
		var contentsTopMargin: Int = 10.dp,
		var contentsSideMargin: Int = 20.dp,
		@ColorRes var contentsTxtColor: Int = android.R.color.black,
		var contentsTxtSize: Int = 16,
		@DrawableRes var dialogBg: Int = View.NO_ID,
		var dialogCorner : Int = 10.dp,
		var buttonTopMargin: Int = 10.dp,
		var buttonVerticalPadding: Int = 10.dp,
		var buttonHorizontalPadding: Int = 10.dp,
		var buttonCorner: Float = 0F.dp,
		var buttonTxtSize: Int = 14,
		@ColorRes var buttonNegativeTxtColor: Int = android.R.color.white,
		@ColorRes var buttonNegativeBgColor: Int = android.R.color.darker_gray,
		@ColorRes var buttonPositiveTxtColor: Int = android.R.color.white,
		@ColorRes var buttonPositiveBgColor: Int = android.R.color.holo_blue_dark
) : Parcelable
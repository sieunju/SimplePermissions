package com.hmju.permissions.model

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.hmju.permissions.extension.dp

/**
 * Description : 권한 거부시 나타내는 팝업 데이터 모델 클래스
 *
 * Created by hmju on 2021-10-26
 */
data class PermissionsDialogUiModel(
    val titleTopMargin: Int = 20.dp,
    val titleSideMargin: Int = 20.dp,
    @ColorRes val titleTxtColor: Int = android.R.color.black,
    val contentsTopMargin: Int = 10.dp,
    val contentsSideMargin: Int = 20.dp,
    @ColorRes val contentsTxtColor: Int = android.R.color.black,
    val dialogSideMargin: Float = 40F.dp,
    val dialogCorner: Float = 20F.dp,
    @DrawableRes val dialogBg: Int = View.NO_ID,
    val buttonTopMargin : Int = 10.dp,
    val buttonVerticalPadding: Int = 10.dp,
    val buttonHorizontalPadding: Int = 10.dp,
    val buttonCorner: Float = 0F.dp,
    @ColorRes val buttonNegativeTxtColor: Int = android.R.color.white,
    @ColorRes val buttonNegativeBgColor: Int = android.R.color.darker_gray,
    @ColorRes val buttonPositiveTxtColor: Int = android.R.color.white,
    @ColorRes val buttonPositiveBgColor: Int = android.R.color.holo_blue_light
)
package hmju.permissions.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import hmju.permissions.dialog.model.PermissionsDialogUiModel
import hmju.widget.view.CustomTextView

/**
 * Description : 권한 거부시 안내 Dialog
 *
 * Created by hmju on 2021-10-25
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class PermissionsDialog(
    private val context: Context,
    private val uiModel: PermissionsDialogUiModel
) {

    companion object {
        const val POSITIVE: Int = 1
        const val NEGATIVE: Int = 2
    }

    private val rootView: View by lazy { View.inflate(context, R.layout.dialog_permissions, null) }
    private val tvTitle: TextView by lazy { rootView.findViewById(R.id.tvTitle) }
    private val tvContents: TextView by lazy { rootView.findViewById(R.id.tvContents) }
    private val llButtons: LinearLayoutCompat by lazy { rootView.findViewById(R.id.llButtons) }
    private val tvNegative: CustomTextView by lazy { rootView.findViewById(R.id.tvNegative) }
    private val tvPositive: CustomTextView by lazy { rootView.findViewById(R.id.tvPositive) }

    private lateinit var dialog: Dialog

    private fun getColor(@ColorRes id: Int): Int = ContextCompat.getColor(context, id)

    /**
     * 제목 변경
     * @param text 변경하고 싶은 문자열
     */
    fun setTitle(text: String?): PermissionsDialog {
        if (!text.isNullOrEmpty()) {
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = HtmlCompat.fromHtml(
                text.replace("\n", "<br>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
        return this
    }

    /**
     * 내용 변경
     * @param text 변경하고 싶은 문자열
     */
    fun setContents(text: String?): PermissionsDialog {
        if (!text.isNullOrEmpty()) {
            tvContents.visibility = View.VISIBLE
            tvContents.text = HtmlCompat.fromHtml(
                text.replace("\n", "<br>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
        return this
    }

    /**
     * 확인 버튼 텍스트 변경
     * @param text 변경하고 싶은 문자열
     */
    fun setPositiveButton(text: String?): PermissionsDialog {
        if (!text.isNullOrEmpty()) {
            tvPositive.visibility = View.VISIBLE
            tvPositive.text = HtmlCompat.fromHtml(
                text.replace("\n", "<br>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
        return this
    }

    /**
     * 취소 버튼 텍스트 변경
     * @param text 변경하고 싶은 문자열
     */
    fun setNegativeButton(text: String?): PermissionsDialog {
        if (!text.isNullOrEmpty()) {
            tvNegative.visibility = View.VISIBLE
            tvNegative.text = HtmlCompat.fromHtml(
                text.replace("\n", "<br>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
        return this
    }

    private fun initDialog() {
        tvTitle.setTextColor(getColor(uiModel.titleTxtColor))
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, uiModel.titleTxtSize.toFloat())
        tvTitle.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = uiModel.titleTopMargin
            leftMargin = uiModel.titleSideMargin
            rightMargin = uiModel.titleSideMargin
        }

        tvContents.setTextColor(getColor(uiModel.contentsTxtColor))
        tvContents.setTextSize(TypedValue.COMPLEX_UNIT_DIP, uiModel.contentsTxtSize.toFloat())
        tvContents.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = uiModel.contentsTopMargin
            leftMargin = uiModel.contentsSideMargin
            rightMargin = uiModel.contentsSideMargin
        }

        llButtons.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = uiModel.buttonTopMargin
        }

        tvNegative.updatePadding(
            uiModel.buttonHorizontalPadding,
            uiModel.buttonVerticalPadding,
            uiModel.buttonHorizontalPadding,
            uiModel.buttonVerticalPadding
        )
        tvNegative.setEnableTxtColor(getColor(uiModel.buttonNegativeTxtColor))
        tvNegative.setTextSize(TypedValue.COMPLEX_UNIT_DIP, uiModel.buttonTxtSize.toFloat())
        tvNegative.setEnableDrawable(
            getColor(uiModel.buttonNegativeBgColor),
            uiModel.buttonCorner,
            View.NO_ID,
            View.NO_ID
        )

        tvPositive.updatePadding(
            uiModel.buttonHorizontalPadding,
            uiModel.buttonVerticalPadding,
            uiModel.buttonHorizontalPadding,
            uiModel.buttonVerticalPadding
        )
        tvPositive.setEnableTxtColor(getColor(uiModel.buttonPositiveTxtColor))
        tvPositive.setTextSize(TypedValue.COMPLEX_UNIT_DIP, uiModel.buttonTxtSize.toFloat())
        tvPositive.setEnableDrawable(
            getColor(uiModel.buttonPositiveBgColor),
            uiModel.buttonCorner,
            View.NO_ID,
            View.NO_ID
        )
    }

    /**
     * Dialog Show
     */
    fun show(callback: (Int) -> Unit) {
        initDialog()

        val builder = if (uiModel.dialogBg == View.NO_ID) AlertDialog.Builder(context)
        else AlertDialog.Builder(context, R.style.PermissionsDialog)
        builder.setView(rootView)
        builder.setCancelable(false)

        if (tvNegative.visibility == View.VISIBLE) {
            tvNegative.setOnClickListener {
                dismiss()
                callback(NEGATIVE)
            }
        }

        if (tvPositive.visibility == View.VISIBLE) {
            tvPositive.setOnClickListener {
                dismiss()
                callback(POSITIVE)
            }
        }

        dialog = builder.create().apply {
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    if (uiModel.dialogBg == View.NO_ID) {
                        window?.setBackgroundDrawable(GradientDrawable(
                            GradientDrawable.Orientation.BL_TR,
                            intArrayOf(Color.WHITE, Color.WHITE)
                        ).apply {
                            cornerRadius = uiModel.dialogCorner.toFloat()
                        })
                    } else {
                        window?.setBackgroundDrawableResource(uiModel.dialogBg)
                    }
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    show()
                } catch (ex: WindowManager.BadTokenException) {
                    Log.d("JLOGGER","ERROR ${context} $ex")
                }
            },0)
        }
    }

    /**
     * Dialog Dismiss
     */
    fun dismiss() {
        try {
            dialog.dismiss()
        } catch (ex: Exception) {

        }
    }
}
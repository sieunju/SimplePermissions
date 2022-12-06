package hmju.permissions.dialog.internal

import android.content.res.Resources
import android.util.TypedValue

/**
 * Convert Dp to Int
 * ex. 5.dp
 */
internal val Int.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

/**
 * Convert Dp to Float
 * ex. 5F.dp
 */
internal val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
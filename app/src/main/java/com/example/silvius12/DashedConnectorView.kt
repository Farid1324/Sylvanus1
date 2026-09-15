package com.example.silvius12

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

/**
 * Orange dashed connector running down the badge gutter of the
 * "What you will do" screen.
 *
 * The view is laid out behind the badge column: its horizontal centre
 * lines up with the centre of the badges, and its top/bottom margins
 * (@dimen/step_connector_height) put its ends on the centres of badge 1
 * and badge 4. Each of the three segments bows to the left, matching the
 * design mockup.
 */
class DashedConnectorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private fun dp(value: Float) = value * resources.displayMetrics.density

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.orange_primary)
        style = Paint.Style.STROKE
        strokeWidth = dp(2.5f)
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        pathEffect = DashPathEffect(floatArrayOf(dp(6f), dp(5f)), 0f)
    }

    private val path = Path()

    init {
        // DashPathEffect on an arbitrary path is only reliable on a software layer
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()
        if (w <= 0f || h <= 0f) return

        val cx = w / 2f
        val bow = dp(13f)
        val third = h / 3f

        path.reset()
        path.moveTo(cx, 0f)
        for (i in 0 until 3) {
            val top = third * i
            val bottom = third * (i + 1)
            path.cubicTo(
                cx - bow, top + third * 0.30f,
                cx - bow, bottom - third * 0.30f,
                cx, bottom
            )
        }

        canvas.drawPath(path, paint)
    }
}

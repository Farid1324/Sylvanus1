package com.example.silvius12

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

/**
 * The teal masthead, dissolving into the page through a soft wave.
 *
 * There is no hard edge anywhere. The panel is painted as a row of vertical
 * strips, and each strip carries its own gradient:
 *
 *     deep teal  ->  brand teal  ->  eased fade to fully transparent
 *
 * The tail is a smootherstep ramp (zero slope at both ends) sampled over
 * [TAIL_STOPS] stops rather than one linear leg. A linear alpha ramp starts
 * and stops abruptly enough to read as two faint lines across the page; easing
 * both ends makes the fade begin and finish imperceptibly.
 *
 * The turning point of every strip's gradient sits on the wave curve at that
 * x position, so the transition follows the wave while still being a long,
 * soft fade. A single vertical gradient cannot do both: anchored to one line
 * it is soft but flat, anchored to the curve it needs per-column control.
 *
 * The view is deliberately taller than the masthead - [FADE_EXTRA_DP] of extra
 * height below it - so the fade has room to reach zero before the view ends.
 * Without that headroom the gradient clips and the bottom of the view becomes
 * the hard line the fade was meant to remove.
 */
class WaveHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private companion object {
        const val REF_W = 360f
        const val REF_H = 208f

        /** Extra height under the masthead for the fade to finish in. */
        const val FADE_EXTRA_DP = 140f

        /** How far the fade reaches above and below the curve. Long, because
         *  the ask is a gradual dissolve rather than a visible edge. */
        const val FADE_UP_DP = 58f
        const val FADE_DOWN_DP = 104f

        /** Stops used to approximate the eased tail. */
        const val TAIL_STOPS = 12

        /** Wave amplitude multiplier. */
        const val AMPLITUDE = 2.2f

        /** Amplifying about the mean would drop the trough into the content
         *  below, so the whole curve is lifted to keep the lowest point where
         *  it was: the crest rises, the trough stays put. */
        const val CURVE_SHIFT_DP = -8f

        /** Vertical strips used to follow the curve. */
        const val STRIPS = 96

        val CURVE = floatArrayOf(
            0f, 193.5f,
            12f, 194.5f, 24f, 196f, 36f, 196.2f,
            58f, 196.8f, 80f, 194f, 100f, 193.2f,
            116f, 192.6f, 136f, 193f, 152f, 196.6f,
            166f, 199.8f, 178f, 204.8f, 194f, 205.8f,
            208f, 206.6f, 224f, 203.2f, 248f, 202.4f,
            272f, 201.8f, 296f, 205f, 320f, 203.6f,
            336f, 202.6f, 348f, 200f, 360f, 197f
        )

        /** Mean of the curve, used as the axis the amplitude scales about. */
        const val CURVE_MEAN = 199.2f
    }

    private val d = resources.displayMetrics.density
    private val fadeExtra = FADE_EXTRA_DP * d
    private val fadeUp = FADE_UP_DP * d
    private val curveShift = CURVE_SHIFT_DP * d
    private val fadeDown = FADE_DOWN_DP * d

    private val deep = ContextCompat.getColor(context, R.color.brand_teal_deep)
    private val teal = ContextCompat.getColor(context, R.color.brand_teal_light)
    private val clear = ContextCompat.getColor(context, R.color.brand_teal_light) and 0x00FFFFFF

    // No anti-aliasing: the strips tile edge to edge, and AA would blend each
    // seam. They must not overlap either - the fill is semi-transparent, so any
    // overlap doubles the alpha and the seams show as vertical banding.
    private val paint = Paint().apply { style = Paint.Style.FILL }

    private var shaders: Array<Shader?> = arrayOfNulls(STRIPS)
    private var stripW = 0f

    /** Curve y for a given x, in view pixels. */
    private fun curveAt(xRef: Float, sx: Float, sy: Float): Float {
        var i = 2
        var x0 = CURVE[0]
        var y0 = CURVE[1]
        while (i < CURVE.size) {
            val x3 = CURVE[i + 4]
            if (xRef <= x3 || i + 6 >= CURVE.size) {
                val x1 = CURVE[i]; val y1 = CURVE[i + 1]
                val x2 = CURVE[i + 2]; val y2 = CURVE[i + 3]
                val y3 = CURVE[i + 5]
                // x is close enough to linear across a segment for this purpose
                val t = ((xRef - x0) / (x3 - x0)).coerceIn(0f, 1f)
                val u = 1f - t
                val y = u * u * u * y0 + 3f * u * u * t * y1 + 3f * u * t * t * y2 + t * t * t * y3
                return (CURVE_MEAN + (y - CURVE_MEAN) * AMPLITUDE) * sy + curveShift
            }
            x0 = x3; y0 = CURVE[i + 5]
            i += 6
        }
        return CURVE_MEAN * sy + curveShift
    }

    override fun onSizeChanged(w: Int, h: Int, ow: Int, oh: Int) {
        super.onSizeChanged(w, h, ow, oh)
        if (w <= 0 || h <= 0) return
        val sx = w / REF_W
        val sy = (h - fadeExtra) / REF_H
        stripW = w.toFloat() / STRIPS
        for (i in 0 until STRIPS) {
            val cx = (i + 0.5f) * stripW / sx
            val cy = curveAt(cx, sx, sy)
            val end = cy + fadeDown
            val mid = ((cy - fadeUp) / end).coerceIn(0.01f, 0.98f)

            // deep -> light across the solid part, then an eased alpha tail
            val colors = IntArray(2 + TAIL_STOPS)
            val stops = FloatArray(2 + TAIL_STOPS)
            colors[0] = deep; stops[0] = 0f
            colors[1] = teal; stops[1] = mid
            val rgb = teal and 0x00FFFFFF
            for (k in 1..TAIL_STOPS) {
                val t = k / TAIL_STOPS.toFloat()
                // smootherstep: flat at both ends, so neither the start nor the
                // end of the fade lands on a detectable line
                val e = t * t * t * (t * (t * 6f - 15f) + 10f)
                val a = ((1f - e) * 255f).toInt().coerceIn(0, 255)
                colors[1 + k] = (a shl 24) or rgb
                stops[1 + k] = mid + (1f - mid) * t
            }
            shaders[i] = LinearGradient(
                0f, 0f, 0f, end, colors, stops, Shader.TileMode.CLAMP
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        if (w <= 0f || h <= 0f) return
        for (i in 0 until STRIPS) {
            paint.shader = shaders[i] ?: continue
            // integer boundaries shared exactly with the neighbouring strip:
            // no gap, no overlap, nothing to blend
            val x0 = Math.round(i * w / STRIPS).toFloat()
            val x1 = Math.round((i + 1) * w / STRIPS).toFloat()
            canvas.drawRect(x0, 0f, x1, h, paint)
        }
    }
}

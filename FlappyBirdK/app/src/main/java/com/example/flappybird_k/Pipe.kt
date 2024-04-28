package com.example.flappybird_k

import android.graphics.Bitmap
import android.graphics.Canvas
import java.util.Random

class Pipe(x: Float, y: Float, width: Int, height: Int) : BaseObject(x, y, width, height) {
    init {
        speed = 10 * Constants.SCREEN_WIDTH / 1080
    }

    fun draw(canvas: Canvas) {
        if (bm != null) {
            x -= speed.toFloat() // Update the x position based on the speed
            canvas.drawBitmap(bm!!, x, y, null) // Draw the pipe at the updated position
        }
    }


    fun randomY() {
        val r = Random()
        y = (r.nextInt(0 + height / 4 + 1) - height / 4).toFloat()
    }

    override var bm: Bitmap? = null
        get() = super.bm
        set(bm) {
            field = Bitmap.createScaledBitmap(bm!!, width, height, true)
        }

    companion object {
        var speed: Int = 0
    }
}

package com.example.flappybird_k

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix

class Bird : BaseObject() {
    private var arrbms = ArrayList<Bitmap>()
    private var count = 0
    private val vFlap = 5
    private var idCurrentBitmap = 0
    @JvmField
    var drop = 0f

    fun draw(canvas: Canvas) {
        drop()
        bm?.let { canvas.drawBitmap(it, x, y, null) }
    }

    private fun drop() {
        drop += 0.6.toFloat()
        y += drop
    }

    fun getArrbms(): ArrayList<Bitmap> {
        return arrbms
    }

    fun setArrbms(arrbms: ArrayList<Bitmap>) {
        this.arrbms = arrbms
        for (i in arrbms.indices) {
            this.arrbms[i] =
                Bitmap.createScaledBitmap(this.arrbms[i], width, height, true)
        }
    }

    override var bm: Bitmap?
        get() {
            count++
            if (count == vFlap) {
                for (i in arrbms.indices) {
                    if (i == arrbms.size - 1) {
                        idCurrentBitmap = 0
                        break
                    } else if (idCurrentBitmap == i) {
                        idCurrentBitmap = i + 1
                        break
                    }
                }
                count = 0
            }
            if (drop < 0) {
                val matrix = Matrix()
                matrix.postRotate(-25f)
                return Bitmap.createBitmap(
                    arrbms[idCurrentBitmap],
                    0,
                    0,
                    arrbms[idCurrentBitmap].width,
                    arrbms[idCurrentBitmap].height,
                    matrix,
                    true
                )
            } else if (drop >= 0) {
                val matrix = Matrix()
                if (drop >= 0) {
                    matrix.postRotate(-25 + drop * 2)
                } else {
                    matrix.postRotate(45f)
                }
                return Bitmap.createBitmap(
                    arrbms[idCurrentBitmap],
                    0,
                    0,
                    arrbms[idCurrentBitmap].width,
                    arrbms[idCurrentBitmap].height,
                    matrix,
                    true
                )
            }
            return arrbms[idCurrentBitmap]
        }
        set(bm) {
            super.bm = bm
        }
}

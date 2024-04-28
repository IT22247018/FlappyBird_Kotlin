package com.example.flappybird_k

import android.graphics.Bitmap
import android.graphics.Rect

open class BaseObject {
    @JvmField
    var x = 0f
    @JvmField
    var y = 0f
    @JvmField
    var width = 0
    @JvmField
    var height = 0
    open var bm: Bitmap? = null
    @JvmField
    protected var rect: Rect? = null

    constructor()
    constructor(x: Float, y: Float, width: Int, height: Int) {
        this.x = x
        this.y = y
        this.height = height
        this.width = width
    }

    fun getRect(): Rect {
        return Rect(x.toInt(), y.toInt(), x.toInt() + width, y.toInt() + height)
    }

    fun setRect(rect: Rect?) {
        this.rect = rect
    }
}

package com.example.barbariangold

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import es.uji.vj1229.framework.SpriteSheet

object Assets {
    private const val SPRITE_WALL_SIZE = 32
    private const val SPRITE_CHARACTER_SIZE = 128
    private var wallBitmap: Bitmap? = null
    private var wallSpriteSheet: SpriteSheet? = null
    var wallV: Bitmap? = null
    var wallH: Bitmap? = null
    var wallWASD: Bitmap? = null
    var wallWAS: Bitmap? = null
    var wallASD: Bitmap? = null
    var wallWSD: Bitmap? = null
    var wallWAD: Bitmap? = null
    var wallWD: Bitmap? = null
    var wallWA: Bitmap? = null
    var wallAS: Bitmap? = null
    var wallSD: Bitmap? = null
    var walld: Bitmap? = null
    var walls: Bitmap? = null
    var walla: Bitmap? = null
    var wallw: Bitmap? = null

    private var princessBitmap: Bitmap? = null
    private var princessSpriteSheet: SpriteSheet? = null
    var princess: Bitmap? = null

    fun createAssets(context: Context, cellSize: Int) {
        val resources = context.resources
        wallBitmap?.recycle()
        wallBitmap = BitmapFactory.decodeResource(resources, R.drawable.walls)
        wallSpriteSheet = SpriteSheet(wallBitmap, SPRITE_WALL_SIZE, SPRITE_WALL_SIZE).apply {
            wallV?.recycle()
            wallV = getScaledSprite(0, 0, cellSize, cellSize)
            wallH?.recycle()
            wallH = getScaledSprite(0, 1, cellSize, cellSize)
            wallWASD?.recycle()
            wallWASD = getScaledSprite(0, 2, cellSize, cellSize)
            wallWAS?.recycle()
            wallWAS = getScaledSprite(0, 3, cellSize, cellSize)
            wallASD?.recycle()
            wallASD = getScaledSprite(0, 4, cellSize, cellSize)
            wallWSD?.recycle()
            wallWSD = getScaledSprite(0, 5, cellSize, cellSize)
            wallWAD?.recycle()
            wallWAD = getScaledSprite(0, 6, cellSize, cellSize)
            wallWD?.recycle()
            wallWD = getScaledSprite(0, 7, cellSize, cellSize)
            wallWA?.recycle()
            wallWA = getScaledSprite(0, 8, cellSize, cellSize)
            wallAS?.recycle()
            wallAS = getScaledSprite(0, 9, cellSize, cellSize)
            wallSD?.recycle()
            wallSD = getScaledSprite(0, 10, cellSize, cellSize)
            walld?.recycle()
            walld = getScaledSprite(0, 11, cellSize, cellSize)
            walls?.recycle()
            walls = getScaledSprite(0, 12, cellSize, cellSize)
            walla?.recycle()
            walla = getScaledSprite(0, 13, cellSize, cellSize)
            wallw?.recycle()
            wallw = getScaledSprite(0, 14, cellSize, cellSize)
        }
        princessBitmap?.recycle()
        princessBitmap = BitmapFactory.decodeResource(resources, R.drawable.princess)
        princessSpriteSheet = SpriteSheet(princessBitmap, SPRITE_CHARACTER_SIZE, SPRITE_CHARACTER_SIZE).apply {
            princess?.recycle()
            princess = getScaledSprite(2, 0, cellSize, cellSize)
        }
    }
}
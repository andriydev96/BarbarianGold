package com.example.barbariangold

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.SpriteSheet

object Assets {
    private const val ANIMATION_FRAMES = 10
    private const val ANIMATION_DURATION = 0.75f
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
    var princessAnimatedBitmapS : AnimatedBitmap? = null
    var princessAnimatedBitmapA : AnimatedBitmap? = null
    var princessAnimatedBitmapD : AnimatedBitmap? = null
    var princessAnimatedBitmapW : AnimatedBitmap? = null
    var princessS1: Bitmap? = null
    var princessS2: Bitmap? = null
    var princessS3: Bitmap? = null
    var princessA1: Bitmap? = null
    var princessA2: Bitmap? = null
    var princessA3: Bitmap? = null
    var princessD1: Bitmap? = null
    var princessD2: Bitmap? = null
    var princessD3: Bitmap? = null
    var princessW1: Bitmap? = null
    var princessW2: Bitmap? = null
    var princessW3: Bitmap? = null

    private var princessPowerUpBitmap: Bitmap? = null
    private var princessPowerUpSpriteSheet: SpriteSheet? = null
    var princessPowerUpAnimatedBitmapS : AnimatedBitmap? = null
    var princessPowerUpAnimatedBitmapA : AnimatedBitmap? = null
    var princessPowerUpAnimatedBitmapD : AnimatedBitmap? = null
    var princessPowerUpAnimatedBitmapW : AnimatedBitmap? = null
    var princessPowerUpS1: Bitmap? = null
    var princessPowerUpS2: Bitmap? = null
    var princessPowerUpS3: Bitmap? = null
    var princessPowerUpA1: Bitmap? = null
    var princessPowerUpA2: Bitmap? = null
    var princessPowerUpA3: Bitmap? = null
    var princessPowerUpD1: Bitmap? = null
    var princessPowerUpD2: Bitmap? = null
    var princessPowerUpD3: Bitmap? = null
    var princessPowerUpW1: Bitmap? = null
    var princessPowerUpW2: Bitmap? = null
    var princessPowerUpW3: Bitmap? = null

    private var monster1Bitmap: Bitmap? = null
    private var monster1SpriteSheet: SpriteSheet? = null
    var monster1AnimatedBitmapS : AnimatedBitmap? = null
    var monster1AnimatedBitmapA : AnimatedBitmap? = null
    var monster1AnimatedBitmapD : AnimatedBitmap? = null
    var monster1AnimatedBitmapW : AnimatedBitmap? = null
    var monster1S1: Bitmap? = null
    var monster1S2: Bitmap? = null
    var monster1S3: Bitmap? = null
    var monster1A1: Bitmap? = null
    var monster1A2: Bitmap? = null
    var monster1A3: Bitmap? = null
    var monster1D1: Bitmap? = null
    var monster1D2: Bitmap? = null
    var monster1D3: Bitmap? = null
    var monster1W1: Bitmap? = null
    var monster1W2: Bitmap? = null
    var monster1W3: Bitmap? = null

    private var monster2Bitmap: Bitmap? = null
    private var monster2SpriteSheet: SpriteSheet? = null
    var monster2AnimatedBitmapS : AnimatedBitmap? = null
    var monster2AnimatedBitmapA : AnimatedBitmap? = null
    var monster2AnimatedBitmapD : AnimatedBitmap? = null
    var monster2AnimatedBitmapW : AnimatedBitmap? = null
    var monster2S1: Bitmap? = null
    var monster2S2: Bitmap? = null
    var monster2S3: Bitmap? = null
    var monster2A1: Bitmap? = null
    var monster2A2: Bitmap? = null
    var monster2A3: Bitmap? = null
    var monster2D1: Bitmap? = null
    var monster2D2: Bitmap? = null
    var monster2D3: Bitmap? = null
    var monster2W1: Bitmap? = null
    var monster2W2: Bitmap? = null
    var monster2W3: Bitmap? = null

    private var monster3Bitmap: Bitmap? = null
    private var monster3SpriteSheet: SpriteSheet? = null
    var monster3AnimatedBitmapS : AnimatedBitmap? = null
    var monster3AnimatedBitmapA : AnimatedBitmap? = null
    var monster3AnimatedBitmapD : AnimatedBitmap? = null
    var monster3AnimatedBitmapW : AnimatedBitmap? = null
    var monster3S1: Bitmap? = null
    var monster3S2: Bitmap? = null
    var monster3S3: Bitmap? = null
    var monster3A1: Bitmap? = null
    var monster3A2: Bitmap? = null
    var monster3A3: Bitmap? = null
    var monster3D1: Bitmap? = null
    var monster3D2: Bitmap? = null
    var monster3D3: Bitmap? = null
    var monster3W1: Bitmap? = null
    var monster3W2: Bitmap? = null
    var monster3W3: Bitmap? = null

    private var monster4Bitmap: Bitmap? = null
    private var monster4SpriteSheet: SpriteSheet? = null
    var monster4AnimatedBitmapS : AnimatedBitmap? = null
    var monster4AnimatedBitmapA : AnimatedBitmap? = null
    var monster4AnimatedBitmapD : AnimatedBitmap? = null
    var monster4AnimatedBitmapW : AnimatedBitmap? = null
    var monster4S1: Bitmap? = null
    var monster4S2: Bitmap? = null
    var monster4S3: Bitmap? = null
    var monster4A1: Bitmap? = null
    var monster4A2: Bitmap? = null
    var monster4A3: Bitmap? = null
    var monster4D1: Bitmap? = null
    var monster4D2: Bitmap? = null
    var monster4D3: Bitmap? = null
    var monster4W1: Bitmap? = null
    var monster4W2: Bitmap? = null
    var monster4W3: Bitmap? = null

    private var itemsBitmap: Bitmap? = null
    private var itemsSpriteSheet: SpriteSheet? = null
    var gold: Bitmap? = null
    var potion: Bitmap? = null

    fun createAssets(context: Context, cellSize: Int) {
        val resources = context.resources
        wallBitmap?.recycle()
        wallBitmap = BitmapFactory.decodeResource(resources, R.drawable.walls)
        wallSpriteSheet = SpriteSheet(wallBitmap, wallBitmap!!.height, wallBitmap!!.width/15).apply {
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
            walla?.recycle()
            walla = getScaledSprite(0, 11, cellSize, cellSize)
            walls?.recycle()
            walls = getScaledSprite(0, 12, cellSize, cellSize)
            walld?.recycle()
            walld = getScaledSprite(0, 13, cellSize, cellSize)
            wallw?.recycle()
            wallw = getScaledSprite(0, 14, cellSize, cellSize)
        }

        princessBitmap?.recycle()
        princessBitmap = BitmapFactory.decodeResource(resources, R.drawable.princess)
        princessSpriteSheet = SpriteSheet(princessBitmap, princessBitmap!!.height/4, princessBitmap!!.width/3).apply {
            princessS1?.recycle()
            princessS1 = getScaledSprite(0, 0, cellSize, cellSize)
            princessS2?.recycle()
            princessS2 = getScaledSprite(0, 1, cellSize, cellSize)
            princessS3?.recycle()
            princessS3 = getScaledSprite(0, 2, cellSize, cellSize)
            princessA1?.recycle()
            princessA1 = getScaledSprite(1, 0, cellSize, cellSize)
            princessA2?.recycle()
            princessA2 = getScaledSprite(1, 1, cellSize, cellSize)
            princessA3?.recycle()
            princessA3 = getScaledSprite(1, 2, cellSize, cellSize)
            princessD1?.recycle()
            princessD1 = getScaledSprite(2, 0, cellSize, cellSize)
            princessD2?.recycle()
            princessD2 = getScaledSprite(2, 1, cellSize, cellSize)
            princessD3?.recycle()
            princessD3 = getScaledSprite(2, 2, cellSize, cellSize)
            princessW1?.recycle()
            princessW1 = getScaledSprite(3, 0, cellSize, cellSize)
            princessW2?.recycle()
            princessW2 = getScaledSprite(3, 1, cellSize, cellSize)
            princessW3?.recycle()
            princessW3 = getScaledSprite(3, 2, cellSize, cellSize)
        }
        princessAnimatedBitmapS?.recycle()
        princessAnimatedBitmapS = AnimatedBitmap(ANIMATION_DURATION, princessS1, princessS2, princessS3)
        princessAnimatedBitmapA?.recycle()
        princessAnimatedBitmapA = AnimatedBitmap(ANIMATION_DURATION, princessA1, princessA2, princessA3)
        princessAnimatedBitmapD?.recycle()
        princessAnimatedBitmapD = AnimatedBitmap(ANIMATION_DURATION, princessD1, princessD2, princessD3)
        princessAnimatedBitmapW?.recycle()
        princessAnimatedBitmapW = AnimatedBitmap(ANIMATION_DURATION, princessW1, princessW2, princessW3)

        princessPowerUpBitmap?.recycle()
        princessPowerUpBitmap = BitmapFactory.decodeResource(resources, R.drawable.princess_power_up)
        princessPowerUpSpriteSheet = SpriteSheet(princessPowerUpBitmap, princessPowerUpBitmap!!.height/4, princessPowerUpBitmap!!.width/3).apply {
            princessPowerUpS1?.recycle()
            princessPowerUpS1 = getScaledSprite(0, 0, cellSize, cellSize)
            princessPowerUpS2?.recycle()
            princessPowerUpS2 = getScaledSprite(0, 1, cellSize, cellSize)
            princessPowerUpS3?.recycle()
            princessPowerUpS3 = getScaledSprite(0, 2, cellSize, cellSize)
            princessPowerUpA1?.recycle()
            princessPowerUpA1 = getScaledSprite(1, 0, cellSize, cellSize)
            princessPowerUpA2?.recycle()
            princessPowerUpA2 = getScaledSprite(1, 1, cellSize, cellSize)
            princessPowerUpA3?.recycle()
            princessPowerUpA3 = getScaledSprite(1, 2, cellSize, cellSize)
            princessPowerUpD1?.recycle()
            princessPowerUpD1 = getScaledSprite(2, 0, cellSize, cellSize)
            princessPowerUpD2?.recycle()
            princessPowerUpD2 = getScaledSprite(2, 1, cellSize, cellSize)
            princessPowerUpD3?.recycle()
            princessPowerUpD3 = getScaledSprite(2, 2, cellSize, cellSize)
            princessPowerUpW1?.recycle()
            princessPowerUpW1 = getScaledSprite(3, 0, cellSize, cellSize)
            princessPowerUpW2?.recycle()
            princessPowerUpW2 = getScaledSprite(3, 1, cellSize, cellSize)
            princessPowerUpW3?.recycle()
            princessPowerUpW3 = getScaledSprite(3, 2, cellSize, cellSize)
        }
        princessPowerUpAnimatedBitmapS?.recycle()
        princessPowerUpAnimatedBitmapS = AnimatedBitmap(ANIMATION_DURATION, princessPowerUpS1, princessPowerUpS2, princessPowerUpS3)
        princessPowerUpAnimatedBitmapA?.recycle()
        princessPowerUpAnimatedBitmapA = AnimatedBitmap(ANIMATION_DURATION, princessPowerUpA1, princessPowerUpA2, princessPowerUpA3)
        princessPowerUpAnimatedBitmapD?.recycle()
        princessPowerUpAnimatedBitmapD = AnimatedBitmap(ANIMATION_DURATION, princessPowerUpD1, princessPowerUpD2, princessPowerUpD3)
        princessPowerUpAnimatedBitmapW?.recycle()
        princessPowerUpAnimatedBitmapW = AnimatedBitmap(ANIMATION_DURATION, princessPowerUpW1, princessPowerUpW2, princessPowerUpW3)

        monster1Bitmap?.recycle()
        monster1Bitmap = BitmapFactory.decodeResource(resources, R.drawable.monster1)
        monster1SpriteSheet = SpriteSheet(monster1Bitmap, monster1Bitmap!!.height/4, monster1Bitmap!!.width/3).apply {
            monster1S1?.recycle()
            monster1S1 = getScaledSprite(0, 0, cellSize, cellSize)
            monster1S2?.recycle()
            monster1S2 = getScaledSprite(0, 1, cellSize, cellSize)
            monster1S3?.recycle()
            monster1S3 = getScaledSprite(0, 2, cellSize, cellSize)
            monster1A1?.recycle()
            monster1A1 = getScaledSprite(1, 0, cellSize, cellSize)
            monster1A2?.recycle()
            monster1A2 = getScaledSprite(1, 1, cellSize, cellSize)
            monster1A3?.recycle()
            monster1A3 = getScaledSprite(1, 2, cellSize, cellSize)
            monster1D1?.recycle()
            monster1D1 = getScaledSprite(2, 0, cellSize, cellSize)
            monster1D2?.recycle()
            monster1D2 = getScaledSprite(2, 1, cellSize, cellSize)
            monster1D3?.recycle()
            monster1D3 = getScaledSprite(2, 2, cellSize, cellSize)
            monster1W1?.recycle()
            monster1W1 = getScaledSprite(3, 0, cellSize, cellSize)
            monster1W2?.recycle()
            monster1W2 = getScaledSprite(3, 1, cellSize, cellSize)
            monster1W3?.recycle()
            monster1W3 = getScaledSprite(3, 2, cellSize, cellSize)
        }
        monster1AnimatedBitmapS?.recycle()
        monster1AnimatedBitmapS = AnimatedBitmap(ANIMATION_DURATION, monster1S1, monster1S2, monster1S3)
        monster1AnimatedBitmapA?.recycle()
        monster1AnimatedBitmapA = AnimatedBitmap(ANIMATION_DURATION, monster1A1, monster1A2, monster1A3)
        monster1AnimatedBitmapD?.recycle()
        monster1AnimatedBitmapD = AnimatedBitmap(ANIMATION_DURATION, monster1D1, monster1D2, monster1D3)
        monster1AnimatedBitmapW?.recycle()
        monster1AnimatedBitmapW = AnimatedBitmap(ANIMATION_DURATION, monster1W1, monster1W2, monster1W3)

        monster2Bitmap?.recycle()
        monster2Bitmap = BitmapFactory.decodeResource(resources, R.drawable.monster2)
        monster2SpriteSheet = SpriteSheet(monster2Bitmap, monster2Bitmap!!.height/4, monster2Bitmap!!.width/3).apply {
            monster2S1?.recycle()
            monster2S1 = getScaledSprite(0, 0, cellSize, cellSize)
            monster2S2?.recycle()
            monster2S2 = getScaledSprite(0, 1, cellSize, cellSize)
            monster2S3?.recycle()
            monster2S3 = getScaledSprite(0, 2, cellSize, cellSize)
            monster2A1?.recycle()
            monster2A1 = getScaledSprite(1, 0, cellSize, cellSize)
            monster2A2?.recycle()
            monster2A2 = getScaledSprite(1, 1, cellSize, cellSize)
            monster2A3?.recycle()
            monster2A3 = getScaledSprite(1, 2, cellSize, cellSize)
            monster2D1?.recycle()
            monster2D1 = getScaledSprite(2, 0, cellSize, cellSize)
            monster2D2?.recycle()
            monster2D2 = getScaledSprite(2, 1, cellSize, cellSize)
            monster2D3?.recycle()
            monster2D3 = getScaledSprite(2, 2, cellSize, cellSize)
            monster2W1?.recycle()
            monster2W1 = getScaledSprite(3, 0, cellSize, cellSize)
            monster2W2?.recycle()
            monster2W2 = getScaledSprite(3, 1, cellSize, cellSize)
            monster2W3?.recycle()
            monster2W3 = getScaledSprite(3, 2, cellSize, cellSize)
        }
        monster2AnimatedBitmapS?.recycle()
        monster2AnimatedBitmapS = AnimatedBitmap(ANIMATION_DURATION, monster2S1, monster2S2, monster2S3)
        monster2AnimatedBitmapA?.recycle()
        monster2AnimatedBitmapA = AnimatedBitmap(ANIMATION_DURATION, monster2A1, monster2A2, monster2A3)
        monster2AnimatedBitmapD?.recycle()
        monster2AnimatedBitmapD = AnimatedBitmap(ANIMATION_DURATION, monster2D1, monster2D2, monster2D3)
        monster2AnimatedBitmapW?.recycle()
        monster2AnimatedBitmapW = AnimatedBitmap(ANIMATION_DURATION, monster2W1, monster2W2, monster2W3)

        monster3Bitmap?.recycle()
        monster3Bitmap = BitmapFactory.decodeResource(resources, R.drawable.monster3)
        monster3SpriteSheet = SpriteSheet(monster3Bitmap, monster3Bitmap!!.height/4, monster3Bitmap!!.width/3).apply {
            monster3S1?.recycle()
            monster3S1 = getScaledSprite(0, 0, cellSize, cellSize)
            monster3S2?.recycle()
            monster3S2 = getScaledSprite(0, 1, cellSize, cellSize)
            monster3S3?.recycle()
            monster3S3 = getScaledSprite(0, 2, cellSize, cellSize)
            monster3A1?.recycle()
            monster3A1 = getScaledSprite(1, 0, cellSize, cellSize)
            monster3A2?.recycle()
            monster3A2 = getScaledSprite(1, 1, cellSize, cellSize)
            monster3A3?.recycle()
            monster3A3 = getScaledSprite(1, 2, cellSize, cellSize)
            monster3D1?.recycle()
            monster3D1 = getScaledSprite(2, 0, cellSize, cellSize)
            monster3D2?.recycle()
            monster3D2 = getScaledSprite(2, 1, cellSize, cellSize)
            monster3D3?.recycle()
            monster3D3 = getScaledSprite(2, 2, cellSize, cellSize)
            monster3W1?.recycle()
            monster3W1 = getScaledSprite(3, 0, cellSize, cellSize)
            monster3W2?.recycle()
            monster3W2 = getScaledSprite(3, 1, cellSize, cellSize)
            monster3W3?.recycle()
            monster3W3 = getScaledSprite(3, 2, cellSize, cellSize)
        }
        monster3AnimatedBitmapS?.recycle()
        monster3AnimatedBitmapS = AnimatedBitmap(ANIMATION_DURATION, monster3S1, monster3S2, monster3S3)
        monster3AnimatedBitmapA?.recycle()
        monster3AnimatedBitmapA = AnimatedBitmap(ANIMATION_DURATION, monster3A1, monster3A2, monster3A3)
        monster3AnimatedBitmapD?.recycle()
        monster3AnimatedBitmapD = AnimatedBitmap(ANIMATION_DURATION, monster3D1, monster3D2, monster3D3)
        monster3AnimatedBitmapW?.recycle()
        monster3AnimatedBitmapW = AnimatedBitmap(ANIMATION_DURATION, monster3W1, monster3W2, monster3W3)

        monster4Bitmap?.recycle()
        monster4Bitmap = BitmapFactory.decodeResource(resources, R.drawable.monster4)
        monster4SpriteSheet = SpriteSheet(monster4Bitmap, monster4Bitmap!!.height/4, monster4Bitmap!!.width/3).apply {
            monster4S1?.recycle()
            monster4S1 = getScaledSprite(0, 0, cellSize, cellSize)
            monster4S2?.recycle()
            monster4S2 = getScaledSprite(0, 1, cellSize, cellSize)
            monster4S3?.recycle()
            monster4S3 = getScaledSprite(0, 2, cellSize, cellSize)
            monster4A1?.recycle()
            monster4A1 = getScaledSprite(1, 0, cellSize, cellSize)
            monster4A2?.recycle()
            monster4A2 = getScaledSprite(1, 1, cellSize, cellSize)
            monster4A3?.recycle()
            monster4A3 = getScaledSprite(1, 2, cellSize, cellSize)
            monster4D1?.recycle()
            monster4D1 = getScaledSprite(2, 0, cellSize, cellSize)
            monster4D2?.recycle()
            monster4D2 = getScaledSprite(2, 1, cellSize, cellSize)
            monster4D3?.recycle()
            monster4D3 = getScaledSprite(2, 2, cellSize, cellSize)
            monster4W1?.recycle()
            monster4W1 = getScaledSprite(3, 0, cellSize, cellSize)
            monster4W2?.recycle()
            monster4W2 = getScaledSprite(3, 1, cellSize, cellSize)
            monster4W3?.recycle()
            monster4W3 = getScaledSprite(3, 2, cellSize, cellSize)
        }
        monster4AnimatedBitmapS?.recycle()
        monster4AnimatedBitmapS = AnimatedBitmap(ANIMATION_DURATION, monster4S1, monster4S2, monster4S3)
        monster4AnimatedBitmapA?.recycle()
        monster4AnimatedBitmapA = AnimatedBitmap(ANIMATION_DURATION, monster4A1, monster4A2, monster4A3)
        monster4AnimatedBitmapD?.recycle()
        monster4AnimatedBitmapD = AnimatedBitmap(ANIMATION_DURATION, monster4D1, monster4D2, monster4D3)
        monster4AnimatedBitmapW?.recycle()
        monster4AnimatedBitmapW = AnimatedBitmap(ANIMATION_DURATION, monster4W1, monster4W2, monster4W3)

        itemsBitmap?.recycle()
        itemsBitmap = BitmapFactory.decodeResource(resources, R.drawable.items)
        itemsSpriteSheet = SpriteSheet(itemsBitmap, itemsBitmap!!.height/8, itemsBitmap!!.width/12).apply {
            gold?.recycle()
            gold = getScaledSprite(6, 9, cellSize/2, cellSize/2)
            potion?.recycle()
            potion = getScaledSprite(2, 1, cellSize, cellSize)
        }
    }

    private fun createAnimation(index: Int, cellSize: Int): AnimatedBitmap {
        val frames = Array<Bitmap>(ANIMATION_FRAMES) {
            val size = cellSize * (it + 1) / ANIMATION_FRAMES
            val sprite = princessSpriteSheet!!.getScaledSprite(0, index, size, size)
            val x = (cellSize - size) / 2f
            with (Graphics(cellSize, cellSize)) {
                drawBitmap(sprite, x, x)
                frameBuffer
            }
        }
        return AnimatedBitmap(ANIMATION_DURATION, false, *frames)
    }
}
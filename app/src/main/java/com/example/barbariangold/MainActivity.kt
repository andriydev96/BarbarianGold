package com.example.barbariangold

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import com.example.barbariangold.controller.Controller
import com.example.barbariangold.model.LevelMap
import com.example.barbariangold.model.Model
import com.example.barbariangold.model.Model.SoundPlayer
import com.example.barbariangold.model.Princess
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.jvilar.barbariangold.model.Direction
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics

class MainActivity : GameActivity(), SoundPlayer {
    var cellSize : Float = 0F
    var xOffset : Float = 0F
    var yOffset : Float = 0F

    var width = 0
    var height = 0
    var horizontalCells = 1
    var verticalCells = 1

    private val model = Model(this, this)
    var map = LevelMap()
    var score = 0
    private lateinit var graphics: Graphics
    lateinit var soundPool: SoundPool
    lateinit var gameLevel : GameLevel

    private var princessAnimation: AnimatedBitmap? = null
    private var princessPowerUpAnimation: AnimatedBitmap? = null
    private var monster1Animation: AnimatedBitmap? = null
    private var monster2Animation: AnimatedBitmap? = null
    private var monster3Animation: AnimatedBitmap? = null
    private var monster4Animation: AnimatedBitmap? = null
    var winId = 0
    var looseId = 0
    var coinId = 0
    var potionId = 0
    var killId = 0
    var walkId = 0
    var gameBeatenId = 0
    var gameOverId = 0
    var walkSoundStreamId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landscapeFullScreenOnCreate()
        prepareSoundPool(this)
    }

    override fun buildGameController() = Controller(this, model)

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        if (this.width == width && this.height == height)
            return
        this.width = width
        this.height = height

        if (cellSize != Math.min(width.toFloat() / horizontalCells, height.toFloat() / verticalCells)) rescaleGrid()
    }

    override fun onDrawingRequested(): Bitmap? {
        graphics.clear(Color.BLACK)
        drawGameScreen(gameLevel)
        drawScore("Score: $score")
        drawLife(gameLevel.princess)
        if (model.gameCompleted) drawNewGamePlusScreen()
        else if (model.gameOver) drawGameOverScreen()
        else if (model.gamePaused) drawPauseScreen()

        return graphics.frameBuffer
    }

    //Used to update the current animation for each character
    fun update(deltaTime: Float) {
        princessAnimation?.update(deltaTime)
        princessAnimation = when (gameLevel.princess.direction){
            Direction.DOWN -> Assets.princessAnimatedBitmapS
            Direction.LEFT -> Assets.princessAnimatedBitmapA
            Direction.RIGHT -> Assets.princessAnimatedBitmapD
            Direction.UP -> Assets.princessAnimatedBitmapW
            Direction.STOP -> Assets.princessAnimatedBitmapS
        }
        if (princessAnimation!!.isEnded)
            princessAnimation!!.restart()

        princessPowerUpAnimation?.update(deltaTime)
        princessPowerUpAnimation = when (gameLevel.princess.direction){
            Direction.DOWN -> Assets.princessPowerUpAnimatedBitmapS
            Direction.LEFT -> Assets.princessPowerUpAnimatedBitmapA
            Direction.RIGHT -> Assets.princessPowerUpAnimatedBitmapD
            Direction.UP -> Assets.princessPowerUpAnimatedBitmapW
            Direction.STOP -> Assets.princessPowerUpAnimatedBitmapS
        }
        if (princessPowerUpAnimation!!.isEnded)
            princessPowerUpAnimation!!.restart()

        monster1Animation?.update(deltaTime)
        monster1Animation = when (gameLevel.monsters[0].direction){
            Direction.DOWN -> Assets.monster1AnimatedBitmapS
            Direction.LEFT -> Assets.monster1AnimatedBitmapA
            Direction.RIGHT -> Assets.monster1AnimatedBitmapD
            Direction.UP -> Assets.monster1AnimatedBitmapW
            Direction.STOP -> Assets.monster1AnimatedBitmapS
        }
        if (monster1Animation!!.isEnded)
            monster1Animation!!.restart()

        monster2Animation?.update(deltaTime)
        monster2Animation = when (gameLevel.monsters[1].direction){
            Direction.DOWN -> Assets.monster2AnimatedBitmapS
            Direction.LEFT -> Assets.monster2AnimatedBitmapA
            Direction.RIGHT -> Assets.monster2AnimatedBitmapD
            Direction.UP -> Assets.monster2AnimatedBitmapW
            Direction.STOP -> Assets.monster2AnimatedBitmapS
        }
        if (monster2Animation!!.isEnded)
            monster2Animation!!.restart()

        monster3Animation?.update(deltaTime)
        monster3Animation = when (gameLevel.monsters[2].direction){
            Direction.DOWN -> Assets.monster3AnimatedBitmapS
            Direction.LEFT -> Assets.monster3AnimatedBitmapA
            Direction.RIGHT -> Assets.monster3AnimatedBitmapD
            Direction.UP -> Assets.monster3AnimatedBitmapW
            Direction.STOP -> Assets.monster3AnimatedBitmapS
        }
        if (monster3Animation!!.isEnded)
            monster3Animation!!.restart()

        monster4Animation?.update(deltaTime)
        monster4Animation = when (gameLevel.monsters[3].direction){
            Direction.DOWN -> Assets.monster4AnimatedBitmapS
            Direction.LEFT -> Assets.monster4AnimatedBitmapA
            Direction.RIGHT -> Assets.monster4AnimatedBitmapD
            Direction.UP -> Assets.monster4AnimatedBitmapW
            Direction.STOP -> Assets.monster4AnimatedBitmapS
        }
        if (monster4Animation!!.isEnded)
            monster4Animation!!.restart()
    }

    //Divides the screen into [horizontalCells] x [verticalCells] cells, and scales the assets accordingly.
    fun rescaleGrid(){
        cellSize = Math.min(width.toFloat() / horizontalCells, height.toFloat() / verticalCells)
        Assets.createAssets(this, (cellSize + (width.toFloat()/height.toFloat())).toInt())
        graphics = Graphics(width, height)
    }

    //Draws the game screen and all of its items and entities in real time
    private fun drawGameScreen(gameLevel : GameLevel){
        horizontalCells = gameLevel.maze.nCols
        verticalCells = gameLevel.maze.nRows
        if (cellSize != Math.min(width.toFloat() / horizontalCells, height.toFloat() / verticalCells)) {
            cellSize = Math.min(width.toFloat() / horizontalCells, height.toFloat() / verticalCells)
            xOffset = (width - horizontalCells * cellSize) / 2.0f
            yOffset = (height - verticalCells * cellSize) / 2.0f
            rescaleGrid()
        }

        with(graphics) {
            if (gameLevel.princess.powerUp) clear(Color.argb(1, 169, 143, 178)) else clear(Color.argb(1, 43, 38, 50))

            //Draws the maze
            for (h in 0 until horizontalCells){
                for (w in 0 until verticalCells){
                    val cell = gameLevel.maze[w, h]
                    if (cell.type == CellType.WALL){
                        if (cell.hasWall(Direction.UP) && cell.hasWall(Direction.RIGHT) && cell.hasWall(Direction.DOWN) && cell.hasWall(Direction.LEFT))
                            graphics.drawBitmap(Assets.wallWASD, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.UP) && cell.hasWall(Direction.LEFT) && cell.hasWall(Direction.DOWN))
                            graphics.drawBitmap(Assets.wallWAS, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.RIGHT) && cell.hasWall(Direction.DOWN) && cell.hasWall(Direction.LEFT))
                            graphics.drawBitmap(Assets.wallASD, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.UP) && cell.hasWall(Direction.DOWN) && cell.hasWall(Direction.RIGHT))
                            graphics.drawBitmap(Assets.wallWSD, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.UP) && cell.hasWall(Direction.RIGHT) && cell.hasWall(Direction.LEFT))
                            graphics.drawBitmap(Assets.wallWAD, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.UP) && cell.hasWall(Direction.RIGHT))
                            graphics.drawBitmap(Assets.wallWD, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.UP) && cell.hasWall(Direction.LEFT))
                            graphics.drawBitmap(Assets.wallWA, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.LEFT) && cell.hasWall(Direction.DOWN))
                            graphics.drawBitmap(Assets.wallAS, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.DOWN) && cell.hasWall(Direction.RIGHT))
                            graphics.drawBitmap(Assets.wallSD, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.UP) && cell.hasWall(Direction.DOWN))
                            graphics.drawBitmap(Assets.wallV, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.RIGHT) && cell.hasWall(Direction.LEFT))
                            graphics.drawBitmap(Assets.wallH, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.LEFT))
                            graphics.drawBitmap(Assets.walld, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.DOWN))
                            graphics.drawBitmap(Assets.walls, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.RIGHT))
                            graphics.drawBitmap(Assets.walla, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (cell.hasWall(Direction.UP))
                            graphics.drawBitmap(Assets.wallw, cellSize * h + xOffset,cellSize * w + yOffset)
                    } else if (cell.type == CellType.DOOR){
                        if (cell.hasWall(Direction.UP) || cell.hasWall(Direction.DOWN) ||
                                gameLevel.maze[w + Direction.UP.row, h + Direction.UP.col].type == CellType.DOOR ||
                                gameLevel.maze[w + Direction.DOWN.row, h + Direction.DOWN.col].type == CellType.DOOR)
                                    graphics.drawLine(cellSize * (h + 0.5F) + xOffset,cellSize * w + yOffset, cellSize * (h + 0.5F) + xOffset,cellSize * (w + 1F) + yOffset, 15F, Color.GRAY)
                        if (cell.hasWall(Direction.RIGHT) || cell.hasWall(Direction.LEFT) ||
                                gameLevel.maze[w + Direction.RIGHT.row, h + Direction.RIGHT.col].type == CellType.DOOR ||
                                gameLevel.maze[w + Direction.LEFT.row, h + Direction.LEFT.col].type == CellType.DOOR)
                            graphics.drawLine(cellSize * h + xOffset,cellSize * (w + 0.5F) + yOffset, cellSize * (h + 1F) + xOffset,cellSize * (w + 0.5F) + yOffset, 15F, Color.GRAY)
                    }
                    //else if (cell.type != CellType.EMPTY) graphics.drawRect(cellSize * h + xOffset, cellSize * w + yOffset, cellSize, cellSize, Color.GRAY)
                }
            }

            //Draws the coins
            for (h in 0 until horizontalCells) {
                for (w in 0 until verticalCells) {
                    val cell = gameLevel.maze[w, h]
                    if (cell.type == CellType.GOLD && !cell.used){
                        graphics.drawCircle((cellSize * h + xOffset) + cellSize/2,(cellSize * w + yOffset) + cellSize/2, cellSize/7, Color.YELLOW)
                        graphics.drawCircle((cellSize * h + xOffset) + cellSize/2,(cellSize * w + yOffset) + cellSize/2, cellSize/15, Color.WHITE)
                        //graphics.drawBitmap(Assets.gold, (cellSize * h + xOffset) + cellSize/4,(cellSize * w + yOffset) + cellSize/4)
                    }
                }
            }

            //Draws the potions
            for (h in 0 until horizontalCells) {
                for (w in 0 until verticalCells) {
                    val cell = gameLevel.maze[w, h]
                    if (cell.type == CellType.POTION && !cell.used){
                        //graphics.drawCircle((cellSize * h + xOffset) + cellSize/2,(cellSize * w + yOffset) + cellSize/2, cellSize/10, Color.YELLOW)
                        graphics.drawBitmap(Assets.potion, cellSize * h + xOffset,cellSize * w + yOffset)
                    }
                }
            }

            //Draws the princess
            if (gameLevel.princess.powerUp){
                when (gameLevel.princess.direction){
                    Direction.DOWN -> graphics.drawBitmap(Assets.princessPowerUpAnimatedBitmapS?.currentFrame, cellSize * gameLevel.princess.x + xOffset,cellSize * gameLevel.princess.y + yOffset)
                    Direction.LEFT -> graphics.drawBitmap(Assets.princessPowerUpAnimatedBitmapA?.currentFrame, cellSize * gameLevel.princess.x + xOffset,cellSize * gameLevel.princess.y + yOffset)
                    Direction.RIGHT -> graphics.drawBitmap(Assets.princessPowerUpAnimatedBitmapD?.currentFrame, cellSize * gameLevel.princess.x + xOffset,cellSize * gameLevel.princess.y + yOffset)
                    Direction.UP -> graphics.drawBitmap(Assets.princessPowerUpAnimatedBitmapW?.currentFrame, cellSize * gameLevel.princess.x + xOffset,cellSize * gameLevel.princess.y + yOffset)
                    Direction.STOP -> graphics.drawBitmap(Assets.princessPowerUpS2, cellSize * gameLevel.princess.x + xOffset,cellSize * gameLevel.princess.y + yOffset)
                }
            } else {
                when (gameLevel.princess.direction){
                    Direction.DOWN -> graphics.drawBitmap(Assets.princessAnimatedBitmapS?.currentFrame, cellSize * gameLevel.princess.x + xOffset,cellSize * gameLevel.princess.y + yOffset)
                    Direction.LEFT -> graphics.drawBitmap(Assets.princessAnimatedBitmapA?.currentFrame, cellSize * gameLevel.princess.x + xOffset,cellSize * gameLevel.princess.y + yOffset)
                    Direction.RIGHT -> graphics.drawBitmap(Assets.princessAnimatedBitmapD?.currentFrame, cellSize * gameLevel.princess.x + xOffset,cellSize * gameLevel.princess.y + yOffset)
                    Direction.UP -> graphics.drawBitmap(Assets.princessAnimatedBitmapW?.currentFrame, cellSize * gameLevel.princess.x + xOffset,cellSize * gameLevel.princess.y + yOffset)
                    Direction.STOP -> graphics.drawBitmap(Assets.princessS2, cellSize * gameLevel.princess.x + xOffset,cellSize * gameLevel.princess.y + yOffset)
                }
            }


            //Draws the monsters if alive
            for (i in model.monsters.indices){
                if (model.monsters[i].alive){
                    when (i) {
                        0 -> when (gameLevel.monsters[i].direction) {
                            Direction.DOWN -> graphics.drawBitmap(Assets.monster1AnimatedBitmapS?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.LEFT -> graphics.drawBitmap(Assets.monster1AnimatedBitmapA?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.RIGHT -> graphics.drawBitmap(Assets.monster1AnimatedBitmapD?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.UP -> graphics.drawBitmap(Assets.monster1AnimatedBitmapW?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.STOP -> graphics.drawBitmap(Assets.monster1S2, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                        }
                        1 -> when (gameLevel.monsters[i].direction) {
                            Direction.DOWN -> graphics.drawBitmap(Assets.monster2AnimatedBitmapS?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.LEFT -> graphics.drawBitmap(Assets.monster2AnimatedBitmapA?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.RIGHT -> graphics.drawBitmap(Assets.monster2AnimatedBitmapD?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.UP -> graphics.drawBitmap(Assets.monster2AnimatedBitmapW?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.STOP -> graphics.drawBitmap(Assets.monster2S2, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                        }
                        2 -> when (gameLevel.monsters[i].direction) {
                            Direction.DOWN -> graphics.drawBitmap(Assets.monster3AnimatedBitmapS?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.LEFT -> graphics.drawBitmap(Assets.monster3AnimatedBitmapA?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.RIGHT -> graphics.drawBitmap(Assets.monster3AnimatedBitmapD?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.UP -> graphics.drawBitmap(Assets.monster3AnimatedBitmapW?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.STOP -> graphics.drawBitmap(Assets.monster3S2, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                        }
                        else -> when (gameLevel.monsters[i].direction) {
                            Direction.DOWN -> graphics.drawBitmap(Assets.monster4AnimatedBitmapS?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.LEFT -> graphics.drawBitmap(Assets.monster4AnimatedBitmapA?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.RIGHT -> graphics.drawBitmap(Assets.monster4AnimatedBitmapD?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.UP -> graphics.drawBitmap(Assets.monster4AnimatedBitmapW?.currentFrame, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                            Direction.STOP -> graphics.drawBitmap(Assets.monster4S2, cellSize * gameLevel.monsters[i].x + xOffset, cellSize * gameLevel.monsters[i].y + yOffset)
                        }
                    }
                }
            }
        }
    }

    //Draws the score text
    private fun drawScore(text: String){
        with(graphics){
            setTextColor(Color.YELLOW)
            setTextSize(40)
            setTextAlign(Paint.Align.LEFT)
            drawText(width.toFloat()*0.025F,height.toFloat()*0.05F,text)
        }
    }

    //Draws the remaining life hearts
    private fun drawLife(princess: Princess){
        with(graphics){
            setTextColor(Color.RED)
            setTextSize(40)
            setTextAlign(Paint.Align.RIGHT)
            var life = ""
            for (heart in 1..princess.life){
                life = "$life♥"
            }
            drawText(width.toFloat()*0.98F,height.toFloat()*0.05F,life)
        }
    }

    //Draws the screen that appears at the start of a level or when the player respawns AKA when the game is in pause state
    private fun drawPauseScreen(){
        with(graphics){
            drawRect(0F, height/2.5F - 25F, width.toFloat()+1F, height/6F + 50F, Color.WHITE)
            drawRect(0F, height/2.5F, width.toFloat()+1F, height/6F, Color.BLACK)
            setTextColor(Color.YELLOW)
            setTextSize(95)
            setTextAlign(Paint.Align.CENTER)
            drawText(width/2F,height/2F + 20F, "TOUCH OR SWIPE ANYWHERE TO START!")
        }
    }

    //Draws the screen that appears when the player looses the game AKA when hearts reach zero
    private fun drawGameOverScreen(){
        with(graphics){
            drawRect(0F, height/2.5F - 25F, width.toFloat()+1F, height/6F + 50F, Color.WHITE)
            drawRect(0F, height/2.5F, width.toFloat()+1F, height/6F, Color.BLACK)
            setTextColor(Color.YELLOW)
            setTextSize(100)
            setTextAlign(Paint.Align.CENTER)
            drawText(width/2F,height/2F, "GAME OVER")
            setTextSize(25)
            drawText(width/2F,height/2F + 45F, "Touch anywhere to restart!")
        }
    }

    //Draws the screen that appears when the player beats the game
    private fun drawNewGamePlusScreen(){
        with(graphics){
            drawRect(0F, height/4F - 25F, width.toFloat()+1F, height/2F + 50F, Color.WHITE)
            drawRect(0F, height/4F, width.toFloat()+1F, height/2F, Color.BLACK)
            setTextColor(Color.YELLOW)
            setTextSize(100)
            setTextAlign(Paint.Align.CENTER)
            drawText(width/2F,height/2F - 165F, "THANKS FOR PLAYING!")
            setTextSize(50)
            drawText(width/2F,height/2F - 105F, "You have beaten the game, but your journey doesn't end here...")
            setTextColor(Color.WHITE)
            drawText(width/2F,height/2F + 45F, "Swipe UP")
            drawText(width/2F,height/2F + 180F, "Swipe DOWN")
            setTextColor(Color.YELLOW)
            setTextSize(30)
            drawText(width/2F,height/2F - 50F, "Welcome to New Game + mode. You can restart your adventure choosing one of this perks: ")
            drawText(width/2F,height/2F + 80F, "Reenter the maze increasing your score by 2500 for each remaining ♥")
            drawText(width/2F,height/2F + 215F, "Reenter the maze cutting your score in half but gaining +2 ♥ (max 7!)")
        }
    }

    //Builds the SoundPool
    private fun prepareSoundPool(context: Context) {
        val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        soundPool = SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(attributes)
                .build()
        winId = soundPool.load(context, R.raw.win, 0)
        looseId = soundPool.load(context, R.raw.loose, 0)
        coinId = soundPool.load(context, R.raw.coin, 0)
        potionId = soundPool.load(context, R.raw.potion, 0)
        killId = soundPool.load(context, R.raw.kill, 0)
        walkId = soundPool.load(context, R.raw.walk, 0)
        gameBeatenId = soundPool.load(context, R.raw.game_beaten, 0)
        gameOverId = soundPool.load(context, R.raw.game_over, 0)
    }

    //Plays a sound given it's ID
    override fun playSound(soundId : Int) {
        soundPool.play(soundId, 0.8f, 0.8f, 0, 0, 1f)
    }
}
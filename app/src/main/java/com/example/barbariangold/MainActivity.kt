package com.example.barbariangold

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.example.barbariangold.controller.Controller
import com.example.barbariangold.model.Model
import es.uji.jvilar.barbariangold.model.Cell
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.jvilar.barbariangold.model.Direction
import es.uji.jvilar.barbariangold.model.Maze
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import kotlin.math.hypot
import kotlin.math.roundToInt

class MainActivity : GameActivity() {
    private var cellSize : Float = 0F
    private var xOffset : Float = 0F
    private var yOffset : Float = 0F

    var width = 0
    var height = 0
    var horizontalCells = 10
    var verticalCells = 10
    var time : Float = 0F

    private val model = Model()
    private lateinit var graphics: Graphics
    lateinit var maze : Maze

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landscapeFullScreenOnCreate()
    }

    override fun buildGameController() = Controller(this, model)

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        if (this.width == width && this.height == height)
            return
        this.width = width
        this.height = height

        rescaleGrid()
    }

    override fun onDrawingRequested(): Bitmap? {
        graphics.clear(Color.BLACK)
        drawMaze(maze)
        drawText("Delta Time: $time")


        return graphics.frameBuffer
    }

    //Divides the screen into [horizontalCells] x [verticalCells] cells, and scales the assets accordingly.
    fun rescaleGrid(){
        cellSize = Math.min(width.toFloat() / horizontalCells, height.toFloat() / verticalCells)
        cellSize /= 1.25F
        Assets.createAssets(this, cellSize.toInt())
        graphics = Graphics(width, height)
    }

    //Visually represents the grid that the screen has been divided into. Used for testing purposes.
    fun drawGrid(horizontalCells: Int, verticalCells: Int) {
        cellSize = Math.min(width.toFloat() / horizontalCells, height.toFloat() / verticalCells)
        cellSize /= 1.25F
        xOffset  = (width - horizontalCells*cellSize) / 2.0f
        yOffset = (height - verticalCells*cellSize) / 2.0f

        with(graphics) {
            for (w in 0 until horizontalCells){
                for (h in 0 until verticalCells){
                    drawLine(cellSize * w + xOffset, cellSize * h + yOffset, cellSize * (w+1) + xOffset, cellSize * h + yOffset, 1F, Color.RED)
                    drawLine(cellSize * (w+1) + xOffset, cellSize * h + yOffset, cellSize * (w+1) + xOffset, cellSize * (h+1) + yOffset, 1F, Color.RED)
                    drawLine(cellSize * (w+1) + xOffset, cellSize * (h+1) + yOffset, cellSize * w + xOffset, cellSize * (h+1) + yOffset, 1F, Color.RED)
                    drawLine(cellSize * w + xOffset, cellSize * (h+1) + yOffset, cellSize * w + xOffset, cellSize * h + yOffset, 1F, Color.RED)
                    drawLine(cellSize * w + xOffset, cellSize * h + yOffset, cellSize * (w+1) + xOffset, cellSize * (h+1) + yOffset, 1F, Color.GRAY)
                    drawLine(cellSize * (w+1) + xOffset, cellSize * h + yOffset, cellSize * w + xOffset, cellSize * (h+1) + yOffset, 1F, Color.GRAY)
                }
                graphics.drawBitmap(Assets.wallV, cellSize * 0 + xOffset,cellSize * 0 + yOffset)
                graphics.drawBitmap(Assets.wallH, cellSize * 1 + xOffset,cellSize * 0 + yOffset)
                graphics.drawBitmap(Assets.wallWASD, cellSize * 2 + xOffset,cellSize * 0 + yOffset)
                graphics.drawBitmap(Assets.wallWAS, cellSize * 3 + xOffset,cellSize * 0 + yOffset)
                graphics.drawBitmap(Assets.wallASD, cellSize * 4 + xOffset,cellSize * 0 + yOffset)
                graphics.drawBitmap(Assets.wallWSD, cellSize * 5 + xOffset,cellSize * 0 + yOffset)
                graphics.drawBitmap(Assets.wallWAD, cellSize * 6 + xOffset,cellSize * 0 + yOffset)
                graphics.drawBitmap(Assets.wallWD, cellSize * 7 + xOffset,cellSize * 0 + yOffset)
                graphics.drawBitmap(Assets.wallWA, cellSize * 8 + xOffset,cellSize * 0 + yOffset)
                graphics.drawBitmap(Assets.wallAS, cellSize * 9 + xOffset,cellSize * 0 + yOffset)
                graphics.drawBitmap(Assets.wallSD, cellSize * 0 + xOffset,cellSize * 1 + yOffset)
                graphics.drawBitmap(Assets.walld, cellSize * 1 + xOffset,cellSize * 1 + yOffset)
                graphics.drawBitmap(Assets.walls, cellSize * 2 + xOffset,cellSize * 1 + yOffset)
                graphics.drawBitmap(Assets.walla, cellSize * 3 + xOffset,cellSize * 1 + yOffset)
                graphics.drawBitmap(Assets.wallw, cellSize * 4 + xOffset,cellSize * 1 + yOffset)
            }
        }
    }

    fun drawMaze(maze: Maze){
        horizontalCells = maze.nCols
        verticalCells = maze.nRows
        cellSize = Math.min(width.toFloat() / horizontalCells, height.toFloat() / verticalCells)
        cellSize /= 1.25F
        xOffset  = (width - horizontalCells*cellSize) / 2.0f
        yOffset = (height - verticalCells*cellSize) / 2.0f
        rescaleGrid()

        with(graphics) {
            clear(Color.BLACK)
            for (h in 0 until horizontalCells){
                for (w in 0 until verticalCells){
                    if (maze[w,h].type == CellType.WALL){
                        if (maze[w,h].hasWall(Direction.UP) && maze[w,h].hasWall(Direction.RIGHT) && maze[w,h].hasWall(Direction.DOWN) && maze[w,h].hasWall(Direction.LEFT))
                            graphics.drawBitmap(Assets.wallWASD, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.UP) && maze[w,h].hasWall(Direction.LEFT) && maze[w,h].hasWall(Direction.DOWN))
                            graphics.drawBitmap(Assets.wallWAS, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.RIGHT) && maze[w,h].hasWall(Direction.DOWN) && maze[w,h].hasWall(Direction.LEFT))
                            graphics.drawBitmap(Assets.wallASD, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.UP) && maze[w,h].hasWall(Direction.DOWN) && maze[w,h].hasWall(Direction.RIGHT))
                            graphics.drawBitmap(Assets.wallWSD, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.UP) && maze[w,h].hasWall(Direction.RIGHT) && maze[w,h].hasWall(Direction.LEFT))
                            graphics.drawBitmap(Assets.wallWAD, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.UP) && maze[w,h].hasWall(Direction.RIGHT))
                            graphics.drawBitmap(Assets.wallWD, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.UP) && maze[w,h].hasWall(Direction.LEFT))
                            graphics.drawBitmap(Assets.wallWA, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.LEFT) && maze[w,h].hasWall(Direction.DOWN))
                            graphics.drawBitmap(Assets.wallAS, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.DOWN) && maze[w,h].hasWall(Direction.RIGHT))
                            graphics.drawBitmap(Assets.wallSD, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.UP) && maze[w,h].hasWall(Direction.DOWN))
                            graphics.drawBitmap(Assets.wallV, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.RIGHT) && maze[w,h].hasWall(Direction.LEFT))
                            graphics.drawBitmap(Assets.wallH, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.LEFT))
                            graphics.drawBitmap(Assets.walld, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.DOWN))
                            graphics.drawBitmap(Assets.walls, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.RIGHT))
                            graphics.drawBitmap(Assets.walla, cellSize * h + xOffset,cellSize * w + yOffset)
                        else if (maze[w,h].hasWall(Direction.UP))
                            graphics.drawBitmap(Assets.wallw, cellSize * h + xOffset,cellSize * w + yOffset)
                    }
                }
            }
        }
    }

    fun drawCharacters(){

    }

    //Draws given text
    fun drawText(text: String){
        with(graphics){
            setTextColor(Color.WHITE)
            drawText(50f,50f,text)
        }
    }
}
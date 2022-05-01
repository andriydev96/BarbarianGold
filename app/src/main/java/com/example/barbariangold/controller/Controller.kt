package com.example.barbariangold.controller

import com.example.barbariangold.GameLevel
import com.example.barbariangold.MainActivity
import com.example.barbariangold.R
import com.example.barbariangold.model.Model
import es.uji.jvilar.barbariangold.controller.GestureDetector
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.jvilar.barbariangold.model.Direction
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler
import kotlin.math.roundToInt

class Controller(private val view: MainActivity, private val model: Model) : IGameController {
    init {
        startLevel(GameLevel(view.map.level1, model.princess, model.monsters))
    }

    private val gestureDetector = GestureDetector()

    override fun onUpdate(deltaTime: Float, touchEvents: List<TouchHandler.TouchEvent>) {
        for (event in touchEvents) {
            if (event.type == TouchHandler.TouchType.TOUCH_DOWN) {
                gestureDetector.onTouchDown(event.x.toFloat()/view.width, event.y.toFloat()/view.height)
            }
            if (event.type == TouchHandler.TouchType.TOUCH_UP) {
                if (model.gamePaused) model.gamePaused = false
                else if (model.gameOver || model.gameCompleted) onGameEnd(gestureDetector)
                if (gestureDetector.onTouchUp(event.x.toFloat() / view.width, event.y.toFloat() / view.height) == GestureDetector.Gestures.SWIPE) {
                    changePrincessDirection(gestureDetector)
                }
            }
        }
        model.update(deltaTime)
        view.update(deltaTime)
        checkGameState(view.gameLevel)
    }

    //Configures and starts a given level
    fun startLevel(gameLevel : GameLevel){
        gameLevel.maze.reset()
        model.gamePaused = true
        model.princess.direction = Direction.STOP
        model.gold = 0
        model.princess.x = gameLevel.maze.origin.col.toFloat()
        model.princess.y = gameLevel.maze.origin.row.toFloat()

        for (i in model.monsters.indices){
            model.monsters[i].alive = true
            model.monsters[i].direction = Direction.STOP
            model.monsters[i].x = gameLevel.maze.enemyOrigins[i].col.toFloat()
            model.monsters[i].y = gameLevel.maze.enemyOrigins[i].row.toFloat()
        }

        view.gameLevel = gameLevel
        if (view.width > 0) view.rescaleGrid()
    }

    //Checks if the conditions to start/end a level are met
    fun checkGameState(gameLevel : GameLevel){
        if (model.gold >= gameLevel.maze.gold){
            model.princess.powerUp = false
            view.soundPool.stop(view.walkSoundStreamId)
            when (model.level){
                1 -> {
                    view.soundPool.play(view.winId, 0.6f, 0.8f, 0, 0, 1f)
                    startLevel(GameLevel(view.map.level2, model.princess, model.monsters))
                    model.level++ }
                2 -> {
                    view.soundPool.play(view.winId, 0.6f, 0.8f, 0, 0, 1f)
                    startLevel(GameLevel(view.map.level3, model.princess, model.monsters))
                    model.level++ }
                3 -> {
                    view.soundPool.play(view.winId, 0.6f, 0.8f, 0, 0, 1f)
                    startLevel(GameLevel(view.map.level4, model.princess, model.monsters))
                    model.level++ }
                4 -> {
                    view.soundPool.play(view.winId, 0.6f, 0.8f, 0, 0, 1f)
                    startLevel(GameLevel(view.map.level5, model.princess, model.monsters))
                    model.level++ }
                5 -> {
                    if (!model.gameCompleted) view.soundPool.play(view.winId, 0.6f, 0.8f, 0, 0, 1f)
                    model.gameCompleted = true
                }
            }
        }
    }

    //Changes the direction of the Princess to match the direction that user has swept to (if possible, of course!)
    fun changePrincessDirection(gesture: GestureDetector){
        val currentCellX = model.princess.x.roundToInt()
        val currentCellY = model.princess.y.roundToInt()
        if (model.princess.direction != gesture.direction && !(view.gameLevel.maze[currentCellY,currentCellX].hasWall(gesture.direction) || view.gameLevel.maze[currentCellY + gesture.direction.row, currentCellX + gesture.direction.col].type == CellType.DOOR)){
            model.toCenter(currentCellX, currentCellY)
            if (model.princess.direction == Direction.STOP) view.walkSoundStreamId = view.soundPool.play(view.walkId, 0.35f, 0.35f, 0, -1, 0.65f)
            model.princess.direction = gesture.direction
        }
    }

    fun onGameEnd(gesture: GestureDetector){
        if (model.gameOver){
            startLevel(GameLevel(view.map.level1, model.princess, model.monsters))
            model.level = 1
            view.score = 0
            model.princess.life = 3
            model.gameOver = false
        } else if (model.gameCompleted){
            if (gesture.direction == Direction.UP){
                startLevel(GameLevel(view.map.level1, model.princess, model.monsters))
                model.level = 1
                view.score += (model.princess.life * 2500)
                model.gameCompleted = false
            } else if (gesture.direction == Direction.DOWN){
                startLevel(GameLevel(view.map.level1, model.princess, model.monsters))
                model.level = 1
                view.score /= 2
                model.princess.life += 2
                if (model.princess.life > 7) model.princess.life = 7
                model.gameCompleted = false
            }
        }
    }
}
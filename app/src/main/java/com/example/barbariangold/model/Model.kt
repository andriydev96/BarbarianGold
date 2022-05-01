package com.example.barbariangold.model

import com.example.barbariangold.MainActivity
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.jvilar.barbariangold.model.Direction
import es.uji.jvilar.barbariangold.model.Maze
import java.lang.Math.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.random.Random.Default.nextInt

class Model(val view: MainActivity, private val soundPlayer: SoundPlayer) {
    private val PRINCESS_SPEED = 5F
    private val MONSTER_SPEED = 3.5F
    val princess = Princess()
    val monsters = arrayOf(Monster(), Monster(), Monster(), Monster())
    var gold = 0
    var level = 1
    var timer = 0F
    var currentTime = 0F
    var gamePaused = true
    var gameOver = false
    var gameCompleted = false

    interface SoundPlayer{
        fun playSound(soundId: Int)
    }

    fun update(deltaTime: Float) {
        if (!gamePaused && !gameOver && !gameCompleted){
            moveMonsters(deltaTime)
            movePrincess(deltaTime)
            powerUpMode(false)
            currentTime += deltaTime
        }
    }

    //Moves the princess in the direction she is facing to and smoothly stops her if she reaches a wall
    private fun movePrincess(deltaTime: Float){
        princess.x += PRINCESS_SPEED * deltaTime * princess.direction.col
        princess.y += PRINCESS_SPEED * deltaTime * princess.direction.row
        val currentCellX =
                if (princess.direction == Direction.RIGHT || princess.direction == Direction.UP)
                    floor(princess.x).toInt()
                else
                    ceil(princess.x).toInt()
        val currentCellY =
                if (princess.direction == Direction.LEFT  || princess.direction == Direction.DOWN)
                    floor(princess.y).toInt()
                else
                    ceil(princess.y).toInt()
        if (view.gameLevel.maze[currentCellY,currentCellX].hasWall(princess.direction) || view.gameLevel.maze[currentCellY + princess.direction.row, currentCellX + princess.direction.col].type == CellType.DOOR){
            princess.direction = Direction.STOP
            view.soundPool.stop(view.walkSoundStreamId)
            toCenter(currentCellX, currentCellY)
        }
        checkCollisions(currentCellX, currentCellY)
    }

    //Controls the movement and direction of the monsters
    private fun moveMonsters(deltaTime: Float){
        for (i in monsters.indices){
            if (monsters[i].alive){
                val currentCellX =
                        if (monsters[i].direction == Direction.RIGHT || monsters[i].direction == Direction.UP)
                            floor(monsters[i].x).toInt()
                        else
                            ceil(monsters[i].x).toInt()
                val currentCellY =
                        if (monsters[i].direction == Direction.LEFT  || monsters[i].direction == Direction.DOWN)
                            floor(monsters[i].y).toInt()
                        else
                            ceil(monsters[i].y).toInt()

                if (monsters[i].direction == Direction.STOP){
                    val list = getMonsterPossibleDirections(monsters[i])
                    val randomIndex = nextInt(list.size)
                    monsters[i].direction = list[randomIndex]
                } else {
                    monsters[i].x += MONSTER_SPEED * deltaTime * monsters[i].direction.col
                    monsters[i].y += MONSTER_SPEED * deltaTime * monsters[i].direction.row

                    if (view.gameLevel.maze[currentCellY,currentCellX].hasWall(monsters[i].direction)){
                        monsters[i].direction = Direction.STOP
                        toCenter(currentCellX, currentCellY, i)
                    }
                }
            }
        }
    }

    //Gets an array with the possible directions that a monster can move to in its current position
    fun getMonsterPossibleDirections(monster : Monster) : ArrayList<Direction>{
        val currentCellX = monster.x.toInt()
        val currentCellY = monster.y.toInt()
        val directionList = listOf(Direction.UP, Direction.RIGHT, Direction.LEFT, Direction.DOWN)
        val possibleDirections = ArrayList<Direction>()
        for (direction in directionList){
            if (!view.gameLevel.maze[currentCellY, currentCellX].hasWall(direction) && direction != monster.direction.opposite())
                possibleDirections.add(direction)
            else if (!view.gameLevel.maze[currentCellY, currentCellX].hasWall(direction) && monster.direction == Direction.STOP)
                possibleDirections.add(direction)
        }
        return possibleDirections
    }

    //Centers the princess to accurately fit in the cell she is currently in
    fun toCenter(currentCellX : Int, currentCellY : Int){
        princess.x = currentCellX.toFloat()
        princess.y = currentCellY.toFloat()
    }

    //Centers a monster (given his index) to accurately fit in the cell she is currently in
    fun toCenter(currentCellX : Int, currentCellY : Int, i: Int){
        monsters[i].x = currentCellX.toFloat()
        monsters[i].y = currentCellY.toFloat()
    }

    //Checks if the princess is colliding with an object or enemy and executes the consequences if she does
    private fun checkCollisions(currentCellX: Int, currentCellY: Int){
        //If the princess collides with an item...
        if (view.gameLevel.maze[currentCellY, currentCellX].type == CellType.GOLD && !view.gameLevel.maze[currentCellY, currentCellX].used) {
            view.gameLevel.maze[currentCellY, currentCellX].used = true
            gold++
            view.score+=100
            soundPlayer.playSound(view.coinId)
        } else if (view.gameLevel.maze[currentCellY, currentCellX].type == CellType.POTION && !view.gameLevel.maze[currentCellY, currentCellX].used) {
            view.gameLevel.maze[currentCellY, currentCellX].used = true
            powerUpMode(true)
            view.score += 500
            soundPlayer.playSound(view.potionId)
        }

        //If the princess collides with an enemy...
        for (i in monsters.indices){
            if (monsters[i].alive) {
                 if(abs(princess.x - monsters[i].x) < 0.4F && abs(princess.y - monsters[i].y) < 0.4F) {
                     if (princess.powerUp){
                         monsters[i].alive = false
                         view.score += 2500
                         soundPlayer.playSound(view.killId)
                     } else {
                         soundPlayer.playSound(view.looseId)
                         view.soundPool.stop(view.walkSoundStreamId)
                         if (princess.life > 1) gamePaused = true else gameOver = true
                         princess.life--
                         princess.direction = Direction.STOP
                         princess.x = view.gameLevel.maze.origin.col.toFloat()
                         princess.y = view.gameLevel.maze.origin.row.toFloat()
                         for (i in monsters.indices)
                             if (monsters[i].alive) {
                                 monsters[i].direction = Direction.STOP
                                 monsters[i].x = view.gameLevel.maze.enemyOrigins[i].col.toFloat()
                                 monsters[i].y = view.gameLevel.maze.enemyOrigins[i].row.toFloat()
                             }
                     }
                 }
            }
        }
    }

    //Activates power up mode and a 10 second timer if gets true, if not, checks if its time for an active power up to end
    fun powerUpMode(got : Boolean){
        if (got){
            princess.powerUp = true
            timer = currentTime + 10.0F
        } else {
            if (currentTime > timer)
                princess.powerUp = false
        }
    }
}
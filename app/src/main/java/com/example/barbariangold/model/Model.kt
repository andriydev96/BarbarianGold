package com.example.barbariangold.model

import android.util.Log
import com.example.barbariangold.MainActivity
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.jvilar.barbariangold.model.Direction
import es.uji.jvilar.barbariangold.model.Position
import java.lang.Math.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.random.Random.Default.nextInt

class Model(private val view: MainActivity, private val soundPlayer: SoundPlayer) {
    private val PRINCESS_SPEED = 5F
    private val MONSTER_SPEED = 3.5F
    private val PATROL = 0
    private val CHASE = 1
    private val PANIC = 2
    val princess = Princess()
    var currentPrincessCellX = 0
    var currentPrincessCellY = 0
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
        currentPrincessCellX =
                if (princess.direction == Direction.RIGHT || princess.direction == Direction.UP)
                    floor(princess.x).toInt()
                else
                    ceil(princess.x).toInt()
        currentPrincessCellY =
                if (princess.direction == Direction.LEFT  || princess.direction == Direction.DOWN)
                    floor(princess.y).toInt()
                else
                    ceil(princess.y).toInt()
        if (view.gameLevel.maze[currentPrincessCellY,currentPrincessCellX].hasWall(princess.direction) || view.gameLevel.maze[currentPrincessCellY + princess.direction.row, currentPrincessCellX + princess.direction.col].type == CellType.DOOR){
            princess.direction = Direction.STOP
            view.soundPool.stop(view.walkSoundStreamId)
            toCenter(currentPrincessCellX, currentPrincessCellY)
        }
        checkCollisions(currentPrincessCellX, currentPrincessCellY)
    }

    //Manages the movement, direction and behavior of the monsters
    private fun moveMonsters(deltaTime: Float){
        for (i in monsters.indices){
            if (monsters[i].alive){
                var currentCellX =
                        if (monsters[i].direction == Direction.RIGHT || monsters[i].direction == Direction.UP)
                            floor(monsters[i].x).toInt()
                        else
                            ceil(monsters[i].x).toInt()
                var currentCellY =
                        if (monsters[i].direction == Direction.LEFT  || monsters[i].direction == Direction.DOWN)
                            floor(monsters[i].y).toInt()
                        else
                            ceil(monsters[i].y).toInt()

                if (monsters[i].direction == Direction.STOP){
                    currentCellX = monsters[i].x.roundToInt()
                    currentCellY = monsters[i].y.roundToInt()
                }

                if (princess.powerUp) monsters[i].behavior = PANIC else monsters[i].behavior = PATROL
                if (monsters[i].behavior == PATROL && (abs(currentCellX - currentPrincessCellX) + abs(currentCellY - currentPrincessCellY) < 12)) monsters[i].behavior = CHASE
                else if (monsters[i].behavior == CHASE && (abs(currentCellX - currentPrincessCellX) + abs(currentCellY - currentPrincessCellY) > 20)) monsters[i].behavior = PATROL
                Log.d("PATHFINDING", "${monsters[i]} ${monsters[i].targetCell}")

                if (monsters[i].behavior == PATROL){
                    if (monsters[i].targetCell == null || (currentCellY == monsters[i].targetCell?.row && currentCellX == monsters[i].targetCell?.col)){
                        val possibleDirections = getPossiblePatrolDirections(Position(currentCellY, currentCellX), i)
                        val possiblePositions = getPossiblePositions(Position(currentCellY, currentCellX), possibleDirections)
                        val randomIndex = if (possiblePositions.size != 0) nextInt(possiblePositions.size) else -1
                        monsters[i].targetCell = if (randomIndex != -1) possiblePositions[randomIndex] else Position(currentCellY, currentCellX)
                        when {
                            (monsters[i].targetCell!!.row - currentCellY) < 0 -> {
                                if (monsters[i].direction != Direction.UP) {
                                    if (monsters[i].direction.opposite() != Direction.UP) toCenter(currentCellX, currentCellY, i)
                                    monsters[i].direction = Direction.UP
                                } else
                                    monsters[i].direction = Direction.UP
                            }
                            (monsters[i].targetCell!!.row - currentCellY) > 0 -> {
                                if (monsters[i].direction != Direction.DOWN) {
                                    if (monsters[i].direction.opposite() != Direction.DOWN) toCenter(currentCellX, currentCellY, i)
                                    monsters[i].direction = Direction.DOWN
                                } else
                                    monsters[i].direction = Direction.DOWN
                            }
                            (monsters[i].targetCell!!.col - currentCellX) < 0 -> {
                                if (monsters[i].direction != Direction.LEFT) {
                                    if (monsters[i].direction.opposite() != Direction.LEFT) toCenter(currentCellX, currentCellY, i)
                                    monsters[i].direction = Direction.LEFT
                                } else
                                    monsters[i].direction = Direction.LEFT
                            }
                            (monsters[i].targetCell!!.col - currentCellX) > 0 -> {
                                if (monsters[i].direction != Direction.RIGHT) {
                                    if (monsters[i].direction.opposite() != Direction.RIGHT) toCenter(currentCellX, currentCellY, i)
                                    monsters[i].direction = Direction.RIGHT
                                } else
                                    monsters[i].direction = Direction.RIGHT
                            }
                        }
                    }
                    monsters[i].x += MONSTER_SPEED * deltaTime * monsters[i].direction.col
                    monsters[i].y += MONSTER_SPEED * deltaTime * monsters[i].direction.row

                } else {
                    val startPosition = Position(currentCellY, currentCellX)
                    val targetPosition = if (monsters[i].behavior == CHASE) Position(currentPrincessCellY, currentPrincessCellX) else Position(view.gameLevel.maze.enemyOrigins[i].row, view.gameLevel.maze.enemyOrigins[i].col)
                    val openSet = ArrayList<Position>()
                    val closedSet = ArrayList<Position>()
                    openSet.add(startPosition)

                    if (currentCellY == targetPosition.row && currentCellX == targetPosition.col){
                        monsters[i].direction = Direction.STOP
                    } else {
                        while (openSet.count() > 0) {
                            val currentCell = openSet[0]
                            openSet.removeAt(0)
                            closedSet.add(currentCell)

                            if (currentCell.row == targetPosition.row && currentCell.col == targetPosition.col) {
                                break
                            }

                            val possibleDirections = getPossibleDirections(currentCell, i)
                            val possiblePositions = getPossiblePositions(currentCell, possibleDirections)

                            for (j in possiblePositions.indices) {
                                if (possiblePositions[j] !in closedSet && possiblePositions[j] !in openSet) {
                                    possiblePositions[j].G = abs((currentCell.row - possiblePositions[j].row)) + abs((currentCell.col - possiblePositions[j].col))
                                    possiblePositions[j].H = abs((targetPosition.row - possiblePositions[j].row)) + abs((targetPosition.col - possiblePositions[j].col))
                                    possiblePositions[j].F = possiblePositions[j].G!! + possiblePositions[j].H!!
                                    possiblePositions[j].parent = currentCell
                                    openSet.add(possiblePositions[j])
                                }
                            }

                            openSet.sortBy { it.F }
                            if (openSet.count() >= 2 && openSet[0].F == openSet[1].F)
                                openSet.sortBy { it.H }
                        }

                        val route = traceRoute(closedSet[closedSet.count() - 1])
                        monsters[i].targetCell = route[route.count() - 2]
                        when {
                            (monsters[i].targetCell!!.row - currentCellY) < 0 -> {
                                if (monsters[i].direction != Direction.UP) {
                                    if (monsters[i].direction.opposite() != Direction.UP) toCenter(currentCellX, currentCellY, i)
                                    monsters[i].direction = Direction.UP
                                } else
                                    monsters[i].direction = Direction.UP
                            }
                            (monsters[i].targetCell!!.row - currentCellY) > 0 -> {
                                if (monsters[i].direction != Direction.DOWN) {
                                    if (monsters[i].direction.opposite() != Direction.DOWN) toCenter(currentCellX, currentCellY, i)
                                    monsters[i].direction = Direction.DOWN
                                } else
                                    monsters[i].direction = Direction.DOWN
                            }
                            (monsters[i].targetCell!!.col - currentCellX) < 0 -> {
                                if (monsters[i].direction != Direction.LEFT) {
                                    if (monsters[i].direction.opposite() != Direction.LEFT) toCenter(currentCellX, currentCellY, i)
                                    monsters[i].direction = Direction.LEFT
                                } else
                                    monsters[i].direction = Direction.LEFT
                            }
                            (monsters[i].targetCell!!.col - currentCellX) > 0 -> {
                                if (monsters[i].direction != Direction.RIGHT) {
                                    if (monsters[i].direction.opposite() != Direction.RIGHT) toCenter(currentCellX, currentCellY, i)
                                    monsters[i].direction = Direction.RIGHT
                                } else
                                    monsters[i].direction = Direction.RIGHT
                            }
                        }

                        monsters[i].x += MONSTER_SPEED * deltaTime * monsters[i].direction.col
                        monsters[i].y += MONSTER_SPEED * deltaTime * monsters[i].direction.row
                    }
                }
            }
        }
    }

    //Gets an array with the possible directions that a monster can move to in its current position
    fun getPossibleDirections(position: Position, i: Int) : ArrayList<Direction>{
        val directionList = listOf(Direction.UP, Direction.RIGHT, Direction.LEFT, Direction.DOWN)
        val possibleDirections = ArrayList<Direction>()
        for (direction in directionList){
            if (!view.gameLevel.maze[position.row, position.col].hasWall(direction) /*&& direction != monsters[i].direction.opposite()*/)
                possibleDirections.add(direction)
            else if (!view.gameLevel.maze[position.row, position.col].hasWall(direction) && monsters[i].direction == Direction.STOP)
                possibleDirections.add(direction)
        }
        return possibleDirections
    }

    //Gets an array with the possible directions that a monster can move to in its current position EXCLUDING the opposite direction it is currently facing
    fun getPossiblePatrolDirections(position: Position, i: Int) : ArrayList<Direction>{
        val directionList = listOf(Direction.UP, Direction.RIGHT, Direction.LEFT, Direction.DOWN)
        val possibleDirections = ArrayList<Direction>()
        for (direction in directionList){
            if (!view.gameLevel.maze[position.row, position.col].hasWall(direction) && direction != monsters[i].direction.opposite())
                possibleDirections.add(direction)
            else if (!view.gameLevel.maze[position.row, position.col].hasWall(direction) && monsters[i].direction == Direction.STOP)
                possibleDirections.add(direction)
        }
        if (possibleDirections.isEmpty()) possibleDirections.add(monsters[i].direction.opposite())
        return possibleDirections
    }

    //Gets an array with the positions a monster can move to given its list of possible directions
    fun getPossiblePositions(position: Position, directionList : ArrayList<Direction>) : ArrayList<Position>{
        val possiblePositions = ArrayList<Position>()
        for (j in directionList.indices){
            possiblePositions.add(Position(position.row + directionList[j].row, position.col + directionList[j].col))
        }
        return possiblePositions
    }

    //Builds an array with the positions a monster has to move to in order to reach its goal (called after A* algorithm calculates the most optimal route)
    fun traceRoute(lastCell: Position): ArrayList<Position> {
        val route = ArrayList<Position>()
        route.add(lastCell)
        while (route[route.count()-1].parent != null){
            route.add(route[route.count()-1].parent!!)
        }
        return route
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

    //Checks if the princess is colliding with an item or an enemy and executes the consequences if she does
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
                         view.soundPool.stop(view.walkSoundStreamId)
                         if (princess.life > 1) {
                             gamePaused = true
                             soundPlayer.playSound(view.looseId)
                         }
                         else {
                             view.playSound(view.gameOverId)
                             gameOver = true
                         }
                         princess.life--
                         princess.direction = Direction.STOP
                         princess.x = view.gameLevel.maze.origin.col.toFloat()
                         princess.y = view.gameLevel.maze.origin.row.toFloat()
                         for (i in monsters.indices)
                             if (monsters[i].alive) {
                                 monsters[i].direction = Direction.STOP
                                 monsters[i].targetCell = null
                                 monsters[i].x = view.gameLevel.maze.enemyOrigins[i].col.toFloat()
                                 monsters[i].y = view.gameLevel.maze.enemyOrigins[i].row.toFloat()
                             }
                     }
                 }
            }
        }
    }

    //Activates power up mode and sets a 10 second timer if gets true, if not, checks if its time for an active power up to end
    fun powerUpMode(got : Boolean){
        if (got){
            princess.powerUp = true
            timer = currentTime + 10.0F
        } else {
            if (currentTime > timer) {
                if (princess.powerUp)
                    for (i in monsters.indices)
                        monsters[i].targetCell = null
                princess.powerUp = false
            }
        }
    }
}
package com.example.barbariangold.controller

import android.util.Log
import com.example.barbariangold.Assets
import com.example.barbariangold.MainActivity
import com.example.barbariangold.model.Model
import es.uji.jvilar.barbariangold.model.Maze
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler

class Controller(private val view: MainActivity, private val model: Model) : IGameController {
    override fun onUpdate(deltaTime: Float, touchEvents: List<TouchHandler.TouchEvent>) {
        for (event in touchEvents)
            if (event.type == TouchHandler.TouchType.TOUCH_UP) {
                Log.d("APP-ACTION", "Touched screen + $deltaTime")
                startLevel(model.level2)
            }
        view.time = deltaTime
    }

     init {
         startLevel(model.level1)
     }

    //Configures and starts a given level
    fun startLevel(maze : Maze){
        model.princess.x = maze.origin.row.toFloat()
        model.princess.y = maze.origin.col.toFloat()
        view.maze = maze
    }
}
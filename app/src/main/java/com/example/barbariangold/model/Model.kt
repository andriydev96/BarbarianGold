package com.example.barbariangold.model

import com.example.barbariangold.MainActivity
import es.uji.jvilar.barbariangold.model.Maze

class Model {
    val princess = Princess()
    val level1 = Maze(
            arrayOf(
                    "#######",
                    "#O.P..#",
                    "#######",
                    "#HH HH#",
                    "#######"
            )
    )
    val level2 = Maze(
            arrayOf(
                    "#######",
                    "#O.P..#",
                    "#######",
                    "#HH HH#",
                    "#######",
                    "#.###.#",
                    "#######"
            )
    )
    fun update(deltaTime: Float) {

    }

}
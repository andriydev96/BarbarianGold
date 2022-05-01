package com.example.barbariangold

import com.example.barbariangold.model.Monster
import com.example.barbariangold.model.Princess
import es.uji.jvilar.barbariangold.model.Maze

data class GameLevel(val maze: Maze, val princess: Princess, val monsters : Array<Monster>)
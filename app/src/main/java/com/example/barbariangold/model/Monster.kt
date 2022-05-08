package com.example.barbariangold.model

import es.uji.jvilar.barbariangold.model.Direction
import es.uji.jvilar.barbariangold.model.Position

class Monster {
    var x : Float = 0.0f
    var y : Float = 0.0f
    var direction : Direction = Direction.STOP
    var alive : Boolean = true
    var behavior : Int = 0 // 0 = PATROL, 1 = CHASE, 2 = PANIC
    var targetCell : Position? = null
}
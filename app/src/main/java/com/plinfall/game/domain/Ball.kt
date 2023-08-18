package com.plinfall.game.domain

data class Ball(
    var position: Pair<Float,Float>,
    var isFlying: Boolean,
    var verticalLine: Int = 1,
    var horizontalPosition: Int
)

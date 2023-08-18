package com.plinfall.game.ui.plinko_fall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plinfall.game.core.library.random
import com.plinfall.game.domain.Ball
import com.plinfall.game.domain.DotsPosition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class PlinkoFallViewModel: ViewModel() {
    private var gameScope = CoroutineScope(Dispatchers.Default)
    private val _balls = MutableLiveData<List<Ball>>(emptyList())
    val balls: LiveData<List<Ball>> = _balls

    private val _bidValue = MutableLiveData(1)
    val bidValue: LiveData<Int> = _bidValue

    private val _win = MutableLiveData(1 to false)
    val win: LiveData<Pair<Int, Boolean>> = _win

    var balanceCallback: ((Double) -> Unit)? = null

    fun spawnBall(x: Float, y: Float) {
        val ball = Ball(
            position = x to y,
            isFlying = true,
            horizontalPosition = 1 random 3
        )
        val newList = _balls.value!!.toMutableList()
        newList.add(ball)
        _balls.postValue(newList)
    }

    fun stop() {
        gameScope.cancel()
    }

    fun clickRight() {
        if (_bidValue.value!! + 1 <= 10) {
            _bidValue.postValue(_bidValue.value!! + 1)
        }
    }

    fun clickLeft() {
        if (_bidValue.value!! - 1 >= 1) {
            _bidValue.postValue(_bidValue.value!! - 1)
        }
    }

    fun letBallsFall(
        topLimit: Int,
        horizontalDistanceBetweenDots: Int,
        verticaDistanceBetweenDots: Int,
        dotsPosition: DotsPosition,
        ballSize: Int
    ) {
        gameScope = CoroutineScope(Dispatchers.Default)
        gameScope.launch {
            while (true) {
                delay(300)
                val currentList = _balls.value!!
                val newList = mutableListOf<Ball>()
                currentList.forEach { ball ->
                    if (ball.isFlying) {
                        if (ball.position.second + 50 + ballSize <= topLimit) {
                            ball.position = ball.position.first to ball.position.second + 50
                        } else {
                            ball.isFlying = false
                            ball.horizontalPosition = ball.horizontalPosition
                            val ballX = when (ball.horizontalPosition) {
                                1 -> dotsPosition.d1
                                2 -> dotsPosition.d1 + (horizontalDistanceBetweenDots * 1)
                                else -> dotsPosition.d1 + (horizontalDistanceBetweenDots * 2)
                            }
                            ball.position = ballX.toFloat() to (topLimit - ballSize).toFloat()
                        }
                        newList.add(ball)
                    } else {
                        when (ball.verticalLine) {
                            1 -> {
                                val ballX = when (ball.horizontalPosition) {
                                    1 -> dotsPosition.d2
                                    2 -> dotsPosition.d2 + (horizontalDistanceBetweenDots * 1)
                                    3 -> dotsPosition.d2 + (horizontalDistanceBetweenDots * 2)
                                    else -> dotsPosition.d2 + (horizontalDistanceBetweenDots * 3)
                                }
                                ball.position =
                                    ballX.toFloat() to (topLimit - ballSize).toFloat() + verticaDistanceBetweenDots * 1
                                ball.verticalLine = 2
                                when (ball.horizontalPosition) {

                                    else -> {
                                        ball.horizontalPosition =
                                            if (Random.nextBoolean()) ball.horizontalPosition + 1 else ball.horizontalPosition
                                    }
                                }
                                newList.add(ball)
                            }

                            2 -> {
                                val ballX = when (ball.horizontalPosition) {
                                    1 -> dotsPosition.d3
                                    2 -> dotsPosition.d3 + (horizontalDistanceBetweenDots * 1)
                                    3 -> dotsPosition.d3 + (horizontalDistanceBetweenDots * 2)
                                    4 -> dotsPosition.d3 + (horizontalDistanceBetweenDots * 3)
                                    else -> dotsPosition.d3 + (horizontalDistanceBetweenDots * 4)
                                }
                                ball.position =
                                    ballX.toFloat() to (topLimit - ballSize).toFloat() + verticaDistanceBetweenDots * 2
                                ball.verticalLine = 3
                                when (ball.horizontalPosition) {
                                    else -> {
                                        ball.horizontalPosition =
                                            if (Random.nextBoolean()) ball.horizontalPosition else ball.horizontalPosition + 1
                                    }
                                }
                                newList.add(ball)
                            }

                            3 -> {
                                val ballX = when (ball.horizontalPosition) {
                                    1 -> dotsPosition.d4
                                    2 -> dotsPosition.d4 + (horizontalDistanceBetweenDots * 1)
                                    3 -> dotsPosition.d4 + (horizontalDistanceBetweenDots * 2)
                                    4 -> dotsPosition.d4 + (horizontalDistanceBetweenDots * 3)
                                    5 -> dotsPosition.d4 + (horizontalDistanceBetweenDots * 4)
                                    else -> dotsPosition.d4 + (horizontalDistanceBetweenDots * 5)
                                }
                                ball.position =
                                    ballX.toFloat() to (topLimit - ballSize).toFloat() + verticaDistanceBetweenDots * 3
                                ball.verticalLine = 4
                                when (ball.horizontalPosition) {
                                    else -> {
                                        ball.horizontalPosition =
                                            if (Random.nextBoolean()) ball.horizontalPosition + 1 else ball.horizontalPosition
                                    }
                                }
                                newList.add(ball)
                            }

                            4 -> {
                                val ballX = when (ball.horizontalPosition) {
                                    1 -> dotsPosition.d5
                                    2 -> dotsPosition.d5 + (horizontalDistanceBetweenDots * 1)
                                    3 -> dotsPosition.d5 + (horizontalDistanceBetweenDots * 2)
                                    4 -> dotsPosition.d5 + (horizontalDistanceBetweenDots * 3)
                                    5 -> dotsPosition.d5 + (horizontalDistanceBetweenDots * 4)
                                    6 -> dotsPosition.d5 + (horizontalDistanceBetweenDots * 5)
                                    else -> dotsPosition.d5 + (horizontalDistanceBetweenDots * 6)
                                }
                                ball.position =
                                    ballX.toFloat() to (topLimit - ballSize).toFloat() + verticaDistanceBetweenDots * 4
                                ball.verticalLine = 5
                                when (ball.horizontalPosition) {
                                    1 -> {
                                        if (Random.nextBoolean()) ball.horizontalPosition else ball.horizontalPosition + 1
                                    }
                                    7 -> {
                                        ball.horizontalPosition =
                                            if (Random.nextBoolean()) ball.horizontalPosition else ball.horizontalPosition + 1
                                    }

                                    else -> {
                                        ball.horizontalPosition =
                                            if (Random.nextBoolean()) ball.horizontalPosition else ball.horizontalPosition + 1
                                    }
                                }
                                newList.add(ball)
                            }

                            5 -> {
                                val ballX = when (ball.horizontalPosition) {
                                    1 -> dotsPosition.d6
                                    2 -> dotsPosition.d6 + (horizontalDistanceBetweenDots * 1)
                                    3 -> dotsPosition.d6 + (horizontalDistanceBetweenDots * 2)
                                    4 -> dotsPosition.d6 + (horizontalDistanceBetweenDots * 3)
                                    5 -> dotsPosition.d6 + (horizontalDistanceBetweenDots * 4)
                                    6 -> dotsPosition.d6 + (horizontalDistanceBetweenDots * 5)
                                    7 -> dotsPosition.d6 + (horizontalDistanceBetweenDots * 6)
                                    else -> dotsPosition.d6 + (horizontalDistanceBetweenDots * 7)
                                }
                                ball.position =
                                    ballX.toFloat() to (topLimit - ballSize).toFloat() + verticaDistanceBetweenDots * 5
                                ball.verticalLine = 6
                                when (ball.horizontalPosition) {
                                    8 -> {
                                        ball.horizontalPosition = 7
                                    }

                                    1 -> {
                                        ball.horizontalPosition = 1
                                    }

                                    else -> {
                                        ball.horizontalPosition =
                                            if (Random.nextBoolean()) ball.horizontalPosition - 1 else ball.horizontalPosition
                                    }
                                }
                                newList.add(ball)
                            }

                            6 -> {
                                val ballX = when (ball.horizontalPosition) {
                                    1 -> dotsPosition.d7
                                    2 -> dotsPosition.d7 + (horizontalDistanceBetweenDots * 1)
                                    3 -> dotsPosition.d7 + (horizontalDistanceBetweenDots * 2)
                                    4 -> dotsPosition.d7 + (horizontalDistanceBetweenDots * 3)
                                    5 -> dotsPosition.d7 + (horizontalDistanceBetweenDots * 4)
                                    6 -> dotsPosition.d7 + (horizontalDistanceBetweenDots * 5)
                                    else -> dotsPosition.d7 + (horizontalDistanceBetweenDots * 6)
                                }
                                ball.position =
                                    ballX.toFloat() to (topLimit - ballSize).toFloat() + verticaDistanceBetweenDots * 6
                                ball.verticalLine = 7
                                when (ball.horizontalPosition) {
                                    1 -> {
                                        if (Random.nextBoolean()) ball.horizontalPosition else ball.horizontalPosition + 1
                                    }
                                    7 -> {
                                        ball.horizontalPosition =
                                            if (Random.nextBoolean()) ball.horizontalPosition else ball.horizontalPosition + 1
                                    }

                                    else -> {
                                        ball.horizontalPosition =
                                            if (Random.nextBoolean()) ball.horizontalPosition else ball.horizontalPosition + 1
                                    }
                                }
                                newList.add(ball)
                            }

                            7 -> {
                                val ballX = when (ball.horizontalPosition) {
                                    1 -> dotsPosition.d8
                                    2 -> dotsPosition.d8 + (horizontalDistanceBetweenDots * 1)
                                    3 -> dotsPosition.d8 + (horizontalDistanceBetweenDots * 2)
                                    4 -> dotsPosition.d8 + (horizontalDistanceBetweenDots * 3)
                                    5 -> dotsPosition.d8 + (horizontalDistanceBetweenDots * 4)
                                    6 -> dotsPosition.d8 + (horizontalDistanceBetweenDots * 5)
                                    7 -> dotsPosition.d8 + (horizontalDistanceBetweenDots * 6)
                                    else -> dotsPosition.d8 + (horizontalDistanceBetweenDots * 7)
                                }
                                ball.position =
                                    ballX.toFloat() to (topLimit - ballSize).toFloat() + verticaDistanceBetweenDots * 7
                                ball.verticalLine = 8
                                when (ball.horizontalPosition) {
                                    1 -> {
                                        ball.horizontalPosition = 1
                                    }

                                    8 -> {
                                        ball.horizontalPosition = 7
                                    }

                                    else -> {
                                        ball.horizontalPosition =
                                            if (Random.nextBoolean()) ball.horizontalPosition - 1 else ball.horizontalPosition
                                    }
                                }
                                newList.add(ball)
                            }

                            8 -> {
                                val ballX = when (ball.horizontalPosition) {
                                    1 -> dotsPosition.d9
                                    2 -> dotsPosition.d9 + (horizontalDistanceBetweenDots * 1)
                                    3 -> dotsPosition.d9 + (horizontalDistanceBetweenDots * 2)
                                    4 -> dotsPosition.d9 + (horizontalDistanceBetweenDots * 3)
                                    5 -> dotsPosition.d9 + (horizontalDistanceBetweenDots * 4)
                                    6 -> dotsPosition.d9 + (horizontalDistanceBetweenDots * 5)
                                    else -> dotsPosition.d9 + (horizontalDistanceBetweenDots * 6)
                                }
                                ball.position =
                                    ballX.toFloat() to (topLimit - ballSize).toFloat() + verticaDistanceBetweenDots * 8
                                ball.verticalLine = 9
                                when (ball.horizontalPosition) {
                                    1 -> {
                                        if (Random.nextBoolean()) ball.horizontalPosition else ball.horizontalPosition + 1
                                    }
                                    7 -> {
                                        ball.horizontalPosition = 6
                                    }

                                    else -> {
                                        ball.horizontalPosition =
                                            if (Random.nextBoolean()) ball.horizontalPosition else ball.horizontalPosition - 1
                                    }
                                }
                                newList.add(ball)
                            }

                            else -> {
                                val multiplier = when (ball.horizontalPosition) {
                                    1 -> 3.0
                                    2 -> 0.8
                                    3 -> 1.0
                                    4 -> 0.5
                                    5 -> 1.5
                                    else -> 5.0
                                }
                                balanceCallback?.invoke(multiplier)
                                viewModelScope.launch {
                                    val bidValue = when (bidValue.value!!) {
                                        1 -> 10
                                        2 -> 25
                                        3 -> 50
                                        4 -> 100
                                        5 -> 200
                                        6 -> 500
                                        7 -> 1000
                                        8 -> 2500
                                        9 -> 5000
                                        else -> 10000
                                    }
                                    _win.postValue((bidValue * multiplier).toInt() to true)
                                    delay(500)
                                    _win.postValue(1 to false)
                                }
                            }
                        }
                    }
                }
                _balls.postValue(newList)
            }
        }
    }
}
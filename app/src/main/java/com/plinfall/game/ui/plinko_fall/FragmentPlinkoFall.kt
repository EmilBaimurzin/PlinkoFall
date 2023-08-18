package com.plinfall.game.ui.plinko_fall

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.plinfall.game.R
import com.plinfall.game.core.library.shortToast
import com.plinfall.game.databinding.FragmentPlinkoFallBinding
import com.plinfall.game.domain.DotsPosition
import com.plinfall.game.domain.Prefs
import com.plinfall.game.ui.other.ViewBindingFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class FragmentPlinkoFall :
    ViewBindingFragment<FragmentPlinkoFallBinding>(FragmentPlinkoFallBinding::inflate) {
    private val sp by lazy {
        Prefs(requireContext())
    }
    private val viewModel: PlinkoFallViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextBalance()

        binding.play.setOnClickListener {
            val bidValue = when (viewModel.bidValue.value!!) {
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
            if (sp.getBalance() >= bidValue) {
                sp.setBalance(-bidValue)
                setTextBalance()
                viewModel.spawnBall(binding.hole.x, binding.hole.y + binding.hole.height)
            } else {
                shortToast(requireContext(), "Not enough balance")
            }
        }

        binding.menu.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.win.observe(viewLifecycleOwner) {
            binding.winText.isVisible = it.second
            binding.winText.text = "+" + it.first.toString()
        }

        viewModel.balanceCallback = {
            val bidValue = when (viewModel.bidValue.value!!) {
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
            val value = (it * bidValue).toInt()
            sp.setBalance(value)
            setTextBalance()
        }

        binding.plus.setOnClickListener {
            if (System.currentTimeMillis() - sp.getLastBalance() > 3600000) {
                sp.setLastBalance(System.currentTimeMillis())
                sp.setBalance(50)
                setTextBalance()
            } else {
                val timeMin = 60 - (System.currentTimeMillis() - sp.getLastBalance()) / 1000 / 60
                shortToast(requireContext(), "Please wait $timeMin minutes more")
            }
        }

        binding.arrowRight.setOnClickListener {
            viewModel.clickRight()
        }

        binding.arrowLeft.setOnClickListener {
            viewModel.clickLeft()
        }

        viewModel.bidValue.observe(viewLifecycleOwner) {
            val bidValue = when (it) {
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
            binding.bid.text = bidValue.toString()
        }

        viewModel.balls.observe(viewLifecycleOwner) {
            binding.ballsLayout.removeAllViews()
            it.forEach { ball ->
                val ballView = ImageView(requireContext())
                ballView.layoutParams = ViewGroup.LayoutParams(dpToPx(30), dpToPx(30))
                ballView.x = ball.position.first
                ballView.y = ball.position.second
                ballView.setImageResource(R.drawable.ball)
                binding.ballsLayout.addView(ballView)
            }
        }

        lifecycleScope.launch {
            delay(20)
            val dotsPosition = DotsPosition(
                binding.d1.x.toInt() - dpToPx(3),
                binding.d2.x.toInt() - dpToPx(3),
                binding.d3.x.toInt() - dpToPx(3),
                binding.d4.x.toInt() - dpToPx(3),
                binding.d5.x.toInt() - dpToPx(3),
                binding.d6.x.toInt() - dpToPx(3),
                binding.d7.x.toInt() - dpToPx(3),
                binding.d8.x.toInt() - dpToPx(3),
                binding.d9.x.toInt() - dpToPx(3),
            )
            viewModel.letBallsFall(
                binding.field.y.toInt(),
                dpToPx(50),
                dpToPx(41),
                dotsPosition,
                dpToPx(30)
            )
        }
    }

    private fun setTextBalance() {
        binding.balance.text = sp.getBalance().toString()
    }

    private fun dpToPx(dp: Int): Int {
        val displayMetrics = resources.displayMetrics
        return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stop()
    }
}
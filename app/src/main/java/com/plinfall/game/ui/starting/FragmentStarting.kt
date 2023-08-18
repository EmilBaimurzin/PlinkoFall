package com.plinfall.game.ui.starting

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.plinfall.game.R
import com.plinfall.game.databinding.FragmentStartingBinding
import com.plinfall.game.ui.other.ViewBindingFragment

class FragmentStarting : ViewBindingFragment<FragmentStartingBinding>(FragmentStartingBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exit.setOnClickListener {
            requireActivity().finish()
        }

        binding.play.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentPlinkoFall)
        }

        binding.privacyText.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse("https://www.google.com")
                )
            )
        }
    }
}
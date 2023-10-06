package com.sidharth.geemu.presentation.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sidharth.geemu.R
import com.sidharth.geemu.databinding.ModalBottomSheetBinding

class ModalBottomSheet : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ModalBottomSheetBinding.inflate(inflater)

        binding.rgCollection.setOnCheckedChangeListener { _, checkedId ->
            val collection = when (checkedId) {
                R.id.playing -> 0
                R.id.not_playing -> 1
                R.id.completed -> 2
                R.id.played -> 3
                else -> 4
            }
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                KEY,
                collection
            )
            dismiss()
        }

        return binding.root
    }

    companion object {
        const val KEY = "collectionKey"
    }
}
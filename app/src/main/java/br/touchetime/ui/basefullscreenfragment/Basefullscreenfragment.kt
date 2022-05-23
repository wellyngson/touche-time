package br.touchetime.ui.basefullscreenfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.touchetime.databinding.FragmentBaseFullscreenBinding

class BaseFullscreenFragment() : DialogFragment() {

    private lateinit var viewBinding: FragmentBaseFullscreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentBaseFullscreenBinding.inflate(inflater, container, false)
        return viewBinding.root
    }
}

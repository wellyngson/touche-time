package br.touchetime.ui.stylefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import br.touchetime.data.model.Fight
import br.touchetime.databinding.FragmentStyleBinding
import br.touchetime.ui.bottomcontrol.BottomSheetDialogTransparentBackgroundFragment

class StyleFragment : BottomSheetDialogTransparentBackgroundFragment() {

    private lateinit var viewBinding: FragmentStyleBinding
    private val viewModel: StyleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentStyleBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readArgs()
        setupAdapter()
        setupFinishBottomSheet()
    }

    private fun readArgs() {
        (arguments?.getParcelable(ARG_FIGHT) as? Fight)?.let { fight ->
            viewModel.setFight(fight)
        }
    }

    private fun setupAdapter() {
        viewBinding.style.adapter = StyleAdapter(
            viewModel.getListStyle()
        ) { style ->
            onStyleSelected(getString(style))
        }
    }

    private fun setupFinishBottomSheet() {
        viewBinding.finish.setOnClickListener {
            onStyleSelectedResult()
        }
    }

    private fun onStyleSelected(style: String) {
        viewModel.setStyleSelected(style)
    }

    private fun onStyleSelectedResult() {
        parentFragmentManager.setFragmentResult(
            STYLE_SELECTED,
            bundleOf(
                FIGHT to viewModel.getFight()
            )
        )
    }

    companion object {
        const val TAG = "br.touchetime.ui.stylefragment"
        const val FIGHT = "FIGHT"
        const val STYLE_SELECTED = "STYLE_SELECTED"
        private const val ARG_FIGHT = "ARG_FIGHT"

        private fun newInstance(fight: Fight?) = StyleFragment().apply {
            arguments = bundleOf(
                ARG_FIGHT to fight
            )
        }

        fun show(fragmentManager: FragmentManager, fight: Fight?) =
            newInstance(fight).show(fragmentManager, TAG)
    }
}

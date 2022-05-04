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
    }

    private fun readArgs() {
        (arguments?.getParcelable(ARG_FIGHT) as? Fight)?.let { fight ->
            viewModel.setFight(fight)
        }
    }

    private fun setupAdapter() {
        val listStyle = viewModel.getListStyle()

        viewBinding.style.adapter = StyleAdapter(
            listStyle
        ) { position ->
            viewModel.setStyleSelected(getString(listStyle[position]))

            viewModel.getFight().let {
                if (it != null) {
                    onStyleSelectedResultFight(it)
                } else {
                    onStyleSelectedResult(listStyle[position])
                }
            }

            parentFragmentManager.setFragmentResult(VIEW_STYLE_SELECTED, bundleOf())
            dismiss()
        }
    }

    private fun onStyleSelectedResultFight(fight: Fight) {
        parentFragmentManager.setFragmentResult(
            STYLE_SELECTED_FIGHT,
            bundleOf(
                FIGHT to fight
            )
        )
    }

    private fun onStyleSelectedResult(style: Int) {
        parentFragmentManager.setFragmentResult(
            STYLE_SELECTED,
            bundleOf(
                STYLE to style
            )
        )
    }

    companion object {
        const val TAG = "br.touchetime.ui.stylefragment"
        const val FIGHT = "FIGHT"
        const val STYLE = "STYLE"
        const val STYLE_SELECTED = "STYLE_SELECTED"
        const val STYLE_SELECTED_FIGHT = "STYLE_SELECTED_FIGHT"
        const val VIEW_STYLE_SELECTED = "VIEW_STYLE_SELECTED"
        private const val ARG_FIGHT = "ARG_FIGHT"

        private fun newInstance(fight: Fight?) = StyleFragment().apply {
            arguments = bundleOf(
                ARG_FIGHT to fight
            )
        }

        fun show(fragmentManager: FragmentManager, fight: Fight? = null) =
            newInstance(fight).show(fragmentManager, TAG)
    }
}

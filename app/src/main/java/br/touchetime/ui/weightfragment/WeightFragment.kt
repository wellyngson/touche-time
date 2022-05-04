package br.touchetime.ui.weightfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import br.touchetime.data.model.Fight
import br.touchetime.databinding.FragmentWeightBinding
import br.touchetime.ui.bottomcontrol.BottomSheetDialogTransparentBackgroundFragment

class WeightFragment : BottomSheetDialogTransparentBackgroundFragment() {

    private lateinit var viewBinding: FragmentWeightBinding
    private val viewModel: WeightViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentWeightBinding.inflate(
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

        (arguments?.getSerializable(STYLE) as? Int)?.let {
            viewModel.setStyle(it)
        }

        (arguments?.getSerializable(CATEGORY) as? Int)?.let {
            viewModel.setCategory(it)
        }
    }

    private fun setupAdapter() {
        viewBinding.weight.adapter = viewModel.getListWeightSelected()?.let { listWeightSelected ->
            WeightAdapter(
                listWeightSelected
            ) { position ->
                viewModel.setWeightSelected(listWeightSelected[position])

                viewModel.getFight().let {
                    if (it != null) {
                        onWeightSelectedResultFight(it)
                    } else {
                        onWeightSelectedResult(listWeightSelected[position])
                    }
                }

                parentFragmentManager.setFragmentResult(VIEW_WEIGHT_SELECTED, bundleOf())
                dismiss()
            }
        }
    }

    private fun onWeightSelectedResultFight(fight: Fight) {
        parentFragmentManager.setFragmentResult(
            WEIGHT_SELECTED_FIGHT,
            bundleOf(
                FIGHT to fight
            )
        )
    }

    private fun onWeightSelectedResult(weight: Int) {
        parentFragmentManager.setFragmentResult(
            WEIGHT_SELECTED,
            bundleOf(
                WEIGHT to weight
            )
        )
    }

    companion object {
        const val TAG = "br.touchetime.ui.stylefragment"
        const val WEIGHT = "WEIGHT"
        const val FIGHT = "FIGHT"
        const val WEIGHT_SELECTED_FIGHT = "WEIGHT_SELECTED_FIGHT"
        const val WEIGHT_SELECTED = "WEIGHT_SELECTED"
        const val VIEW_WEIGHT_SELECTED = "VIEW_WEIGHT_SELECTED"
        private const val ARG_FIGHT = "ARG_FIGHT"
        private const val STYLE = "STYLE"
        private const val CATEGORY = "CATEGORY"

        private fun newInstance(
            fight: Fight?,
            style: Int?,
            category: Int?,
        ) = WeightFragment().apply {
            arguments = bundleOf(
                ARG_FIGHT to fight,
                STYLE to style,
                CATEGORY to category
            )
        }

        fun show(
            fragmentManager: FragmentManager,
            fight: Fight? = null,
            style: Int? = null,
            category: Int? = null,
        ) = newInstance(fight, style, category).show(fragmentManager, TAG)
    }
}

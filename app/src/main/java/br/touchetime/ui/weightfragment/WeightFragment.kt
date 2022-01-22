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
        savedInstanceState: Bundle?
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
        setupFinishBottomSheet()
    }

    private fun readArgs() {
        (arguments?.getParcelable(ARG_FIGHT) as? Fight)?.let { fight ->
            viewModel.setFight(fight)
        }
    }

    private fun setupAdapter() {
        val listWeight = viewModel.getListWeight(requireContext())

        viewBinding.weight.adapter = WeightAdapter(
            listWeight
        ) { position ->
            viewModel.setWeightSelected(listWeight[position])

            viewModel.getFight()?.let { fight ->
                onWeightSelected(
                    fight
                )
            }
        }
    }

    private fun setupFinishBottomSheet() {
        viewBinding.finish.setOnClickListener {
            dismiss()
        }
    }

    private fun onWeightSelected(fight: Fight) {
        onWeightSelectedResult(fight)
    }

    private fun onWeightSelectedResult(fight: Fight) {
        parentFragmentManager.setFragmentResult(
            WEIGHT_SELECTED,
            bundleOf(
                FIGHT to fight
            )
        )
    }

    companion object {
        const val TAG = "br.touchetime.ui.stylefragment"
        const val FIGHT = "FIGHT"
        const val WEIGHT_SELECTED = "WEIGHT_SELECTED"
        private const val ARG_FIGHT = "ARG_FIGHT"

        private fun newInstance(fight: Fight?) = WeightFragment().apply {
            arguments = bundleOf(
                ARG_FIGHT to fight
            )
        }

        fun show(fragmentManager: FragmentManager, fight: Fight?) =
            newInstance(fight).show(fragmentManager, TAG)
    }
}

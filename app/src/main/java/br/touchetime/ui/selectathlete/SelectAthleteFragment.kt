package br.touchetime.ui.selectathlete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import br.touchetime.data.model.Athlete
import br.touchetime.data.model.NetworkState
import br.touchetime.databinding.FragmentSelectAthleteBinding
import br.touchetime.ui.bottomcontrol.BottomSheetDialogTransparentBackgroundFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectAthleteFragment : BottomSheetDialogTransparentBackgroundFragment() {

    private lateinit var viewBinding: FragmentSelectAthleteBinding
    private val viewModel: SelectAthleteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentSelectAthleteBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        readArgs()
        setupRecyclerView()
        setupObserverAthletesAndState()
    }

    private fun readArgs() {
        (arguments?.getSerializable(ARG) as? String)?.let {
            viewModel.setupAthleteSelected(it)
        }
    }

    private fun setupRecyclerView() {
        viewBinding.athletes.adapter = AthleteAdapter(object : AthleteAdapter.AthleteHandler {
            override fun onClick(athlete: Athlete) {
                viewModel.athleteSelected?.let {
                    athleteChosen(it, athlete)
                }

                dismiss()
            }

            override fun onLongClick(athlete: Athlete) {
                // TODO: Delete athlete
                dismiss()
            }
        })
    }

    private fun athleteChosen(key: String, athlete: Athlete) {
        parentFragmentManager.setFragmentResult(
            key,
            bundleOf(
                ATHLETE to athlete
            )
        )
    }

    private fun setupObserverAthletesAndState() {
        viewModel.athletes.observe(viewLifecycleOwner) {
            submitAthletes(it)
        }

        viewModel.athletesLoadState.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkState.Loading -> {
                    viewBinding.apply {
                        loading.isVisible = true
                        athletes.isVisible = false
                    }
                }
                is NetworkState.Loaded -> {
                    viewBinding.apply {
                        loading.isVisible = false
                        athletes.isVisible = true
                    }
                }
                is NetworkState.Failed -> {
                    viewBinding.apply {
                        loading.isVisible = false
                        athletes.isVisible = false
                    }
                }
                is NetworkState.Idle -> {
                    viewBinding.apply {
                        loading.isVisible = true
                        athletes.isVisible = false
                    }
                }
            }
        }
    }

    private fun submitAthletes(athletes: List<Athlete>) {
        (viewBinding.athletes.adapter as? AthleteAdapter)?.submitList(athletes)
    }

    companion object {
        const val TAG = "br.touchetime.ui.selectathlete"
        const val ARG = "arg"
        const val RED = "red"
        const val BLUE = "blue"
        const val ATHLETE = "athlete"

        private fun newInstance(athleteSelected: String) = SelectAthleteFragment().apply {
            arguments = bundleOf(
                ARG to athleteSelected
            )
        }

        fun show(fragmentManager: FragmentManager, athleteSelected: String) =
            newInstance(athleteSelected).show(fragmentManager, TAG)
    }
}

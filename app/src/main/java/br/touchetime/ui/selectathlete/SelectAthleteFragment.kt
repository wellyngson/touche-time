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
import br.touchetime.ui.basedialogfragment.BaseBottomSheetDialogFragment
import br.touchetime.ui.common.ConfirmDeletionDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectAthleteFragment : BaseBottomSheetDialogFragment() {

    private lateinit var viewBinding: FragmentSelectAthleteBinding
    private val viewModel: SelectAthleteViewModel by viewModels()
    private val resultKeys = arrayOf(
        ConfirmDeletionDialog.DELETE_ATHLETE
    )

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
        setupResultKeysListeners()
    }

    private fun readArgs() {
        (arguments?.getSerializable(ARG) as? String)?.let {
            viewModel.setupColorAthleteSelected(it)
        }
    }

    private fun setupRecyclerView() {
        viewBinding.athletes.adapter = AthleteAdapter(object : AthleteAdapter.AthleteHandler {
            override fun onClick(athlete: Athlete) {
                viewModel.colorAthleteSelected?.let {
                    athleteChosen(it, athlete)
                }

                // TODO: This dismiss should be removed of here and go for the state response
                dismiss()
            }

            override fun onLongClick(athlete: Athlete) {
                viewModel.setupAthlete(athlete)

                ConfirmDeletionDialog.show(childFragmentManager)
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

    private fun setupResultKeysListeners() {
        resultKeys.forEach {
            when (it) {
                ConfirmDeletionDialog.DELETE_ATHLETE -> childFragmentManager
                else -> activity?.supportFragmentManager
            }?.setFragmentResultListener(
                it,
                viewLifecycleOwner,
                ::handleResultKey
            )
        }
    }

    private fun handleResultKey(key: String, bundle: Bundle) {
        when (key) {
            ConfirmDeletionDialog.DELETE_ATHLETE -> deleteAthlete()
        }
    }

    private fun deleteAthlete() {
        viewModel.deleteAthlete()
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

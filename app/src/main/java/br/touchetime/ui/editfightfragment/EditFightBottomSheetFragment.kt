package br.touchetime.ui.editfightfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.touchetime.R
import br.touchetime.data.model.ParamsFight
import br.touchetime.data.model.UiState
import br.touchetime.databinding.FragmentEditFightBinding
import br.touchetime.ui.bottomcontrol.BottomSheetDialogTransparentBackgroundFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EditFightBottomSheetFragment : BottomSheetDialogTransparentBackgroundFragment() {

    private lateinit var viewBinding: FragmentEditFightBinding
    private val viewModel: EditFightViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentEditFightBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupObservers()
        setupTechnicalSuperiority()
        setupNumberRounds()
        setupTimeRound()
        setupTimeInterval()
        finishConfiguration()
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.technicalSuperiorityEditFight.collect { technicalSuperiority ->
                when (technicalSuperiority.state) {
                    UiState.Initial, UiState.Success -> {
                        viewBinding.technicalSuperiority.setText(technicalSuperiority.score.toString())
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.numberRoundEditFight.collect { numberRounds ->
                when (numberRounds.state) {
                    UiState.Initial, UiState.Success -> {
                        viewBinding.numberRounds.setText(numberRounds.score.toString())
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.timeRoundEditFight.collect { timeRound ->
                when (timeRound.state) {
                    UiState.Initial, UiState.Success -> {
                        viewBinding.timeRound.setText(timeRound.score)
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.timeIntervalEditFight.collect { timeInterval ->
                when (timeInterval.state) {
                    UiState.Initial, UiState.Success -> {
                        viewBinding.timeInterval.setText(timeInterval.score)
                    }
                }
            }
        }
    }

    private fun setupTechnicalSuperiority() {
        viewBinding.technicalSuperiority.apply {
            setTitle(context.getString(R.string.technical_superiority))

            viewBindingComponent.apply {
                remove.setOnClickListener {
                    viewModel.removeTechnicalSuperiority()
                }
                add.setOnClickListener {
                    viewModel.addTechnicalSuperiority()
                }
            }
        }
    }

    private fun setupNumberRounds() {
        viewBinding.numberRounds.apply {
            setTitle(context.getString(R.string.number_rounds))

            viewBindingComponent.apply {
                remove.setOnClickListener {
                    viewModel.removeNumberRound()
                }
                add.setOnClickListener {
                    viewModel.addNumberRounds()
                }
            }
        }
    }

    private fun setupTimeRound() {
        viewBinding.timeRound.apply {
            setTitle(context.getString(R.string.time_rounds))

            viewBindingComponent.apply {
                remove.setOnClickListener {
                    viewModel.removeTimeRound()
                }
                add.setOnClickListener {
                    viewModel.addTimeRound()
                }
            }
        }
    }

    private fun setupTimeInterval() {
        viewBinding.timeInterval.apply {
            setTitle(context.getString(R.string.time_interval_of_round))

            viewBindingComponent.apply {
                remove.setOnClickListener {
                    viewModel.removeTimeInterval()
                }
                add.setOnClickListener {
                    viewModel.addTimeInterval()
                }
            }
        }
    }

    private fun finishConfiguration() {
        viewBinding.finish.setOnClickListener {
            viewModel.apply {
                changeParameters(
                    ParamsFight(
                        technicalSuperiorityEditFight.value.score,
                        numberRoundEditFight.value.score,
                        timeRoundEditFight.value.score,
                        timeIntervalEditFight.value.score
                    )
                )
            }

            dismiss()
        }
    }

    companion object {
        const val TAG = "br.touchetime.ui.editfightfragment"

        private fun newInstance() = EditFightBottomSheetFragment()

        fun show(fragmentManager: FragmentManager) = newInstance().show(fragmentManager, TAG)
    }
}

package br.touchetime.ui.editfightfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import br.touchetime.R
import br.touchetime.databinding.FragmentEditFightBinding
import br.touchetime.ui.bottomcontrol.BottomSheetDialogTransparentBackgroundFragment
import br.touchetime.ui.scorefragment.ScoreViewModel

class EditFightFragment : BottomSheetDialogTransparentBackgroundFragment() {

    private lateinit var viewBinding: FragmentEditFightBinding
    private val viewModel: ScoreViewModel by activityViewModels()

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
        viewModel.technicalSuperiorityEditFight.observe(viewLifecycleOwner) {
            viewBinding.technicalSuperiority.setText(it.toString())
        }

        viewModel.numberRoundEditFight.observe(viewLifecycleOwner) {
            viewBinding.numberRounds.setText(it.toString())
        }

        viewModel.timeRoundEditFight.observe(viewLifecycleOwner) {
            viewBinding.numberRounds.setText(it.toString())
        }

        viewModel.timeIntervalEditFight.observe(viewLifecycleOwner) {
            viewBinding.numberRounds.setText(it.toString())
        }
    }

    private fun setupTechnicalSuperiority() {
        viewBinding.technicalSuperiority.apply {
            setTitle(context.getString(R.string.technical_superiority))
            setText(viewModel.technicalSuperiorityEditFight.value.toString())

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
            setText(viewModel.numberRoundEditFight.value.toString())

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
            setText(context.getString(R.string.time_of_round))

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
            setText(context.getString(R.string.time_of_round))

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
            viewModel.changeParameters()
            dismiss()
        }
    }

    companion object {
        const val TAG = "br.touchetime.ui.editfightfragment"

        private fun newInstance() = EditFightFragment()

        fun show(fragmentManager: FragmentManager) = newInstance().show(fragmentManager, TAG)
    }
}

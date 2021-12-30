package br.touchetime.ui.editfightfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import br.touchetime.R
import br.touchetime.databinding.FragmentEditFightBinding
import br.touchetime.extension.setFragmentResultsListeners
import br.touchetime.ui.bottomcontrol.BottomSheetDialogTransparentBackgroundFragment
import br.touchetime.ui.scorefragment.ScoreFragment

class EditFightFragment : BottomSheetDialogTransparentBackgroundFragment() {

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
        viewModel.technicalSuperiority.observe(viewLifecycleOwner) {
            viewBinding.technicalSuperiority
                .viewBindingComponent.text.text = it.toString()
        }

        viewModel.numberRound.observe(viewLifecycleOwner) {
            viewBinding.numberRounds
                .viewBindingComponent.text.text = it.toString()
        }

        viewModel.timeRound.observe(viewLifecycleOwner) {
            viewBinding.timeRound
                .viewBindingComponent.text.text = it
        }

        viewModel.timeInterval.observe(viewLifecycleOwner) {
            viewBinding.timeInterval
                .viewBindingComponent.text.text = it
        }
    }

    private fun setupTechnicalSuperiority() {
        viewBinding.technicalSuperiority.apply {
            setTitle(context.getString(R.string.technical_superiority))
            setText(viewModel.technicalSuperiority.value.toString())

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
            setText(viewModel.numberRound.value.toString())

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
            setFragmentResultsListeners(
                childFragmentManager,
                arrayOf(ScoreFragment.DISMISS_RESULT)
            ) { key, bundle ->
                if (key == ScoreFragment.DISMISS_RESULT) {
                    dismiss()
                }
            }
        }
    }

    companion object {
        const val TAG = "br.touchetime.ui.editfightfragment"
        const val EDIT_FIGHT_DISMISS_RESULT = "EDIT_FIGHT_DISMISS_RESULT"

        private fun newInstance() = EditFightFragment()

        fun show(fragmentManager: FragmentManager) = newInstance().show(fragmentManager, TAG)
    }
}

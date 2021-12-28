package br.touchetime.ui.editfightfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import br.touchetime.R
import br.touchetime.databinding.FragmentEditFightBinding
import br.touchetime.ui.bottomcontrol.BottomSheetDialogTransparentBackgroundFragment

class EditFightFragment : BottomSheetDialogTransparentBackgroundFragment() {

    private lateinit var viewBinding: FragmentEditFightBinding
    private val viewModel: EditFightViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentEditFightBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupTechnicalSuperiority()
        setupNumberRounds()
        setupTimeRound()
        setupTimeInterval()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.technicalSuperiority.observe(viewLifecycleOwner) {
            viewBinding.technicalSuperiority.viewBindingComponent.text.text = it.toString()
        }

        viewModel.numberRound.observe(viewLifecycleOwner) {
            viewBinding.numberRounds.viewBindingComponent.text.text = it.toString()
        }

        viewModel.timeRound.observe(viewLifecycleOwner) {
            viewBinding.timeRound.viewBindingComponent.text.text = it
        }

        viewModel.timeInterval.observe(viewLifecycleOwner) {
            viewBinding.timeInterval.viewBindingComponent.text.text = it
        }
    }

    private fun setupTimeInterval() {
        viewBinding.timeInterval.apply {
            setTitle(context.getString(R.string.time_interval_of_round))
            setText(context.getString(R.string.time_of_round))

            viewBindingComponent.apply {
                remove.setOnClickListener {
                    viewModel.changeTimeInterval(-10)
                }
                add.setOnClickListener {
                    viewModel.changeTimeInterval(10)
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
                    viewModel.changeTimeRound(-10)
                }
                add.setOnClickListener {
                    viewModel.changeTimeRound(10)
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
                    viewModel.changeNumberRounds(-1)
                }
                add.setOnClickListener {
                    viewModel.changeNumberRounds(1)
                }
            }
        }
    }

    private fun setupTechnicalSuperiority() {
        viewBinding.technicalSuperiority.apply {
            setTitle(context.getString(R.string.technical_superiority))
            setText(viewModel.technicalSuperiority.value.toString())

            viewBindingComponent.apply {
                remove.setOnClickListener {
                    viewModel.changeTechnicalSuperiority(-1)
                }
                add.setOnClickListener {
                    viewModel.changeTechnicalSuperiority(1)
                }
            }
        }
    }

    companion object {
        const val TAG = "br.touchetime.ui.editfightfragment"

        private fun newInstance() = EditFightFragment()

        fun show(fragmentManager: FragmentManager) = newInstance().show(fragmentManager, TAG)
    }
}

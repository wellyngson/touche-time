package br.touchetime.ui.scorefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import br.touchetime.MainActivity
import br.touchetime.databinding.FragmentScoreBinding
import br.touchetime.ui.chronometerfragment.ChronometerFragment
import br.touchetime.ui.homefragment.HomeFragment

class ScoreFragment : Fragment() {

    private lateinit var viewBinding: FragmentScoreBinding
    private val viewModel: ScoreViewModel by viewModels()
    private val mainActivity: MainActivity?
        get() = activity as? MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentScoreBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupObservers()
        setupRedListeners()
        setupBlueListeners()
        setupChronometer()
        setupScoreFragment()
    }

    private fun setupScoreFragment() {
        viewBinding.back.setOnClickListener {
            mainActivity?.navigateToFragment(
                HomeFragment.newInstance(),
                HomeFragment.TAG
            )
        }
    }

    private fun setupChronometer() {
        childFragmentManager.commit(true) {
            replace(
                viewBinding.chronometer.id,
                ChronometerFragment.newInstance(),
                ChronometerFragment.TAG
            )
        }
    }

    private fun setupBlueListeners() {
        viewBinding.blue.viewBindingComponent.apply {
            addScore.setOnClickListener {
                viewModel.addScore(BLUE)
            }

            removeScore.setOnClickListener {
                viewModel.removeScore(BLUE)
            }

            foulContainer.setOnClickListener {
                viewModel.addFoul(BLUE)
            }
        }
    }

    private fun setupRedListeners() {
        viewBinding.red.viewBindingComponent.apply {
            addScore.setOnClickListener {
                viewModel.addScore(RED)
            }

            removeScore.setOnClickListener {
                viewModel.removeScore(RED)
            }

            foulContainer.setOnClickListener {
                viewModel.addFoul(RED)
            }
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            scoreRed.observe(viewLifecycleOwner) {
                viewBinding.red.viewBindingComponent.score.text = it.toString()
            }

            scoreBlue.observe(viewLifecycleOwner) {
                viewBinding.blue.viewBindingComponent.score.text = it.toString()
            }

            foulRed.observe(viewLifecycleOwner) {
                viewBinding.red.viewBindingComponent.foul.text = it.toString()
            }

            foulBlue.observe(viewLifecycleOwner) {
                viewBinding.blue.viewBindingComponent.foul.text = it.toString()
            }
        }
    }

    companion object {
        const val TAG = "br.wrestling.ui.scorefragment"
        const val RED = "red"
        const val BLUE = "blue"

        fun newInstance() = ScoreFragment()
    }
}
